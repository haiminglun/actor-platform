package im.actor.core.modules.messaging.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import im.actor.core.api.ApiDialogGroup;
import im.actor.core.api.ApiDialogShort;
import im.actor.core.api.rpc.RequestLoadGroupedDialogs;
import im.actor.core.api.rpc.ResponseLoadGroupedDialogs;
import im.actor.core.entity.Avatar;
import im.actor.core.entity.DialogSpec;
import im.actor.core.entity.Group;
import im.actor.core.entity.Peer;
import im.actor.core.entity.PeerType;
import im.actor.core.entity.User;
import im.actor.core.modules.ModuleContext;
import im.actor.core.modules.messaging.dialogs.entity.GroupedItem;
import im.actor.core.modules.messaging.dialogs.entity.GroupedStorage;
import im.actor.core.util.ModuleActor;
import im.actor.core.viewmodel.DialogGroup;
import im.actor.core.viewmodel.DialogSmall;
import im.actor.core.viewmodel.DialogSpecVM;
import im.actor.core.viewmodel.generics.ArrayListDialogSmall;
import im.actor.runtime.function.Consumer;
import im.actor.runtime.mvvm.MVVMCollection;

import static im.actor.core.entity.EntityConverter.convert;

public class ActiveDialogsActor extends ModuleActor {

    private static final String PREFERENCE_GROUPED = "dialogs.grouped";
    private static final String PREFERENCE_GROUPED_LOADED = "dialogs.grouped.loaded";

    private boolean isStarted = false;
    private GroupedStorage storage;
    private MVVMCollection<DialogSpec, DialogSpecVM> specs;


    //
    // Init
    //

    public ActiveDialogsActor(ModuleContext context) {
        super(context);
    }

    @Override
    public void preStart() {
        super.preStart();

        specs = context().getMessagesModule().getDialogDescKeyValue();
        storage = new GroupedStorage();

        boolean isLoaded = preferences().getBool(PREFERENCE_GROUPED_LOADED, false);
        byte[] data = preferences().getBytes(PREFERENCE_GROUPED);
        if (data != null) {
            try {
                storage = new GroupedStorage(data);
            } catch (IOException e) {
                e.printStackTrace();
                isLoaded = false;
            }
        }

        if (!isLoaded) {
            loadGroupedDialogs();
        } else {
            isStarted = true;
            notifyVM();
        }
    }

    private void loadGroupedDialogs() {
        api(new RequestLoadGroupedDialogs()).then(new Consumer<ResponseLoadGroupedDialogs>() {
            @Override
            public void apply(final ResponseLoadGroupedDialogs response) {
                updates().executeRelatedResponse(response.getUsers(), response.getGroups(), self(), new Runnable() {
                    @Override
                    public void run() {
                        applyGroups(response.getDialogs());
                        preferences().putBool(PREFERENCE_GROUPED_LOADED, true);
                    }
                });
            }
        }).done(self());
    }


    //
    // Updates
    //

    private void onActiveDialogsChanged(List<ApiDialogGroup> groupedItems) {
        if (!isStarted) {
            isStarted = true;
            preferences().putBool(PREFERENCE_GROUPED_LOADED, true);
            unstashAll();
        }
        applyGroups(groupedItems);
    }

    private void onCounterChanged(Peer peer, int counter) {
        if (isStarted) {
            DialogSpec spec = new DialogSpec(peer, false, counter);
            specs.getEngine().addOrUpdateItem(spec);
            notifyVM(peer);
        } else {
            stash();
        }
    }

    private void onPeerInfoChanged(Peer peer) {
        if (isStarted) {
            notifyVM(peer);
        }
    }


    //
    // Tools
    //

    private void notifyVM(Peer peer) {
        boolean found = false;
        for (GroupedItem i : storage.getGroups()) {
            for (Peer p : i.getPeers()) {
                if (p.equals(peer)) {
                    found = true;
                    break;
                }
            }
        }

        if (found) {
            notifyVM();
        }
    }

    private void notifyVM() {

        ArrayList<DialogGroup> groups = new ArrayList<>();
        for (GroupedItem i : storage.getGroups()) {
            ArrayListDialogSmall dialogSmalls = new ArrayListDialogSmall();
            for (Peer p : i.getPeers()) {
                DialogSpec spec = specs.getEngine().getValue(p.getUnuqueId());
                String title;
                Avatar avatar;
                if (p.getPeerType() == PeerType.GROUP) {
                    Group group = getGroup(p.getPeerId());
                    title = group.getTitle();
                    avatar = group.getAvatar();
                } else if (p.getPeerType() == PeerType.PRIVATE) {
                    User user = getUser(p.getPeerId());
                    title = user.getName();
                    avatar = user.getAvatar();
                } else {
                    continue;
                }

                dialogSmalls.add(new DialogSmall(p, title, avatar, spec.getCounter()));
            }
            groups.add(new DialogGroup(i.getTitle(), i.getKey(), dialogSmalls));
        }
        context().getMessagesModule().getDialogGroupsVM().getGroupsValueModel().change(groups);
    }


    private void applyGroups(List<ApiDialogGroup> dialogGroups) {

        // Writing missing specs

        ArrayList<DialogSpec> updatedSpecs = new ArrayList<>();
        for (ApiDialogGroup g : dialogGroups) {
            for (ApiDialogShort s : g.getDialogs()) {
                Peer peer = convert(s.getPeer());
                if (specs.getEngine().getValue(peer.getUnuqueId()) == null) {
                    updatedSpecs.add(new DialogSpec(peer, false, s.getCounter()));
                }
            }
        }
        specs.getEngine().addOrUpdateItems(updatedSpecs);

        // Updating storage

        storage.getGroups().clear();
        for (ApiDialogGroup g : dialogGroups) {
            ArrayList<Peer> peers = new ArrayList<Peer>();
            for (ApiDialogShort s : g.getDialogs()) {
                Peer peer = convert(s.getPeer());
                peers.add(peer);
            }
            storage.getGroups().add(new GroupedItem(g.getKey(), g.getTitle(), peers));
        }
        preferences().putBytes(PREFERENCE_GROUPED, storage.toByteArray());

        // Updating VM

        notifyVM();
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof PeerInformationChanged) {
            PeerInformationChanged informationChanged = (PeerInformationChanged) message;
            onPeerInfoChanged(informationChanged.getPeer());
        } else if (message instanceof CounterChanged) {
            CounterChanged counterChanged = (CounterChanged) message;
            onCounterChanged(counterChanged.getPeer(), counterChanged.getCounter());
        } else if (message instanceof ActiveDialogsChanged) {
            ActiveDialogsChanged g = (ActiveDialogsChanged) message;
            onActiveDialogsChanged(g.getItems());
        } else {
            super.onReceive(message);
        }
    }

    public static class ActiveDialogsChanged {

        private List<ApiDialogGroup> items;

        public ActiveDialogsChanged(List<ApiDialogGroup> items) {
            this.items = items;
        }

        public List<ApiDialogGroup> getItems() {
            return items;
        }
    }

    public static class PeerInformationChanged {

        private Peer peer;

        public PeerInformationChanged(Peer peer) {
            this.peer = peer;
        }

        public Peer getPeer() {
            return peer;
        }

    }

    public static class CounterChanged {
        private Peer peer;
        private int counter;

        public CounterChanged(Peer peer, int counter) {
            this.peer = peer;
            this.counter = counter;
        }

        public Peer getPeer() {
            return peer;
        }

        public int getCounter() {
            return counter;
        }
    }
}