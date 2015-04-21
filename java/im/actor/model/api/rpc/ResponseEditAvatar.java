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
import static im.actor.model.droidkit.bser.Utils.*;
import java.io.IOException;
import im.actor.model.network.parser.*;
import java.util.List;
import java.util.ArrayList;
import im.actor.model.api.*;

public class ResponseEditAvatar extends Response {

    public static final int HEADER = 0x67;
    public static ResponseEditAvatar fromBytes(byte[] data) throws IOException {
        return Bser.parse(new ResponseEditAvatar(), data);
    }

    private Avatar avatar;
    private int seq;
    private byte[] state;

    public ResponseEditAvatar(Avatar avatar, int seq, byte[] state) {
        this.avatar = avatar;
        this.seq = seq;
        this.state = state;
    }

    public ResponseEditAvatar() {

    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public int getSeq() {
        return this.seq;
    }

    public byte[] getState() {
        return this.state;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.avatar = values.getObj(1, new Avatar());
        this.seq = values.getInt(2);
        this.state = values.getBytes(3);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        if (this.avatar == null) {
            throw new IOException();
        }
        writer.writeObject(1, this.avatar);
        writer.writeInt(2, this.seq);
        if (this.state == null) {
            throw new IOException();
        }
        writer.writeBytes(3, this.state);
    }

    @Override
    public String toString() {
        String res = "tuple EditAvatar{";
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
