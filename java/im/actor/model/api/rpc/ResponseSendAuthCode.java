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

public class ResponseSendAuthCode extends Response {

    public static final int HEADER = 0x2;
    public static ResponseSendAuthCode fromBytes(byte[] data) throws IOException {
        return Bser.parse(new ResponseSendAuthCode(), data);
    }

    private String smsHash;
    private boolean isRegistered;

    public ResponseSendAuthCode(String smsHash, boolean isRegistered) {
        this.smsHash = smsHash;
        this.isRegistered = isRegistered;
    }

    public ResponseSendAuthCode() {

    }

    public String getSmsHash() {
        return this.smsHash;
    }

    public boolean isRegistered() {
        return this.isRegistered;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.smsHash = values.getString(1);
        this.isRegistered = values.getBool(2);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        if (this.smsHash == null) {
            throw new IOException();
        }
        writer.writeString(1, this.smsHash);
        writer.writeBool(2, this.isRegistered);
    }

    @Override
    public String toString() {
        String res = "tuple SendAuthCode{";
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
