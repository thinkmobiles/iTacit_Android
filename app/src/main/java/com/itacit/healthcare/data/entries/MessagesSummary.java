package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 26.11.15.
 */
public class MessagesSummary {
    @SerializedName("ALL")
    @Expose
    private MessagesCount all;
    @SerializedName("ACT")
    @Expose
    private MessagesCount act;
    @SerializedName("DONE")
    @Expose
    private MessagesCount done;
    @SerializedName("ARCHIVE")
    @Expose
    private MessagesCount archive;
    @SerializedName("WAITING")
    @Expose
    private MessagesCount waiting;
    @SerializedName("SENT")
    @Expose
    private MessagesCount sent;
    @SerializedName("INBOX")
    @Expose
    private MessagesCount inbox;

    public MessagesCount getAll() {
        return all;
    }

    public void setAll(MessagesCount all) {
        this.all = all;
    }

    public MessagesCount getAct() {
        return act;
    }

    public void setAct(MessagesCount act) {
        this.act = act;
    }

    public MessagesCount getDone() {
        return done;
    }

    public void setDone(MessagesCount done) {
        this.done = done;
    }

    public MessagesCount getArchive() {
        return archive;
    }

    public void setArchive(MessagesCount archive) {
        this.archive = archive;
    }

    public MessagesCount getWaiting() {
        return waiting;
    }

    public void setWaiting(MessagesCount waiting) {
        this.waiting = waiting;
    }

    public MessagesCount getSent() {
        return sent;
    }

    public void setSent(MessagesCount sent) {
        this.sent = sent;
    }

    public MessagesCount getInbox() {
        return inbox;
    }

    public void setInbox(MessagesCount inbox) {
        this.inbox = inbox;
    }

    public class MessagesCount {
        @SerializedName("messageCount")
        @Expose
        private String messageCount;
        @SerializedName("readRequired")
        @Expose
        private String readRequired;


        public String getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(String messageCount) {
            this.messageCount = messageCount;
        }

        public String getReadRequired() {
            return readRequired;
        }

        public void setReadRequired(String readRequired) {
            this.readRequired = readRequired;
        }
    }
}
