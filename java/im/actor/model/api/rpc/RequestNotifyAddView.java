package im.actor.model.api.rpc;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.model.droidkit.bser.Bser;
import im.actor.model.droidkit.bser.BserParser;
import im.actor.model.droidkit.bser.BserObject;
import im.actor.model.droidkit.bser.BserValues;
import im.actor.model.droidkit.bser.BserWriter;
import im.actor.model.droidkit.bser.DataInput;
import im.actor.model.droidkit.bser.DataOutput;
import im.actor.model.droidkit.bser.util.SparseArray;
import static im.actor.model.droidkit.bser.Utils.*;
import java.io.IOException;
import im.actor.model.network.parser.*;
import java.util.List;
import java.util.ArrayList;
import im.actor.model.api.*;

public class RequestNotifyAddView extends Request<ResponseVoid> {

    public static final int HEADER = 0xa2;
    public static RequestNotifyAddView fromBytes(byte[] data) throws IOException {
        return Bser.parse(new RequestNotifyAddView(), data);
    }

    private int bannerId;
    private int viewDuration;

    public RequestNotifyAddView(int bannerId, int viewDuration) {
        this.bannerId = bannerId;
        this.viewDuration = viewDuration;
    }

    public RequestNotifyAddView() {

    }

    public int getBannerId() {
        return this.bannerId;
    }

    public int getViewDuration() {
        return this.viewDuration;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.bannerId = values.getInt(1);
        this.viewDuration = values.getInt(2);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        writer.writeInt(1, this.bannerId);
        writer.writeInt(2, this.viewDuration);
    }

    @Override
    public String toString() {
        String res = "rpc NotifyAddView{";
        res += "bannerId=" + this.bannerId;
        res += ", viewDuration=" + this.viewDuration;
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
