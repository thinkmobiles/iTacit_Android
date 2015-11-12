package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 12.11.15.
 */
public class Message {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("parentMessageId")
    @Expose
    private String parentMessageId;
    @SerializedName("originalMessageId")
    @Expose
    private String originalMessageId;
    @SerializedName("sendDateTime")
    @Expose
    private String sendDateTime;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("senderNameFull")
    @Expose
    private String senderNameFull;
    @SerializedName("senderRoleName")
    @Expose
    private String senderRoleName;
    @SerializedName("senderImageUrl")
    @Expose
    private String senderImageUrl;
    @SerializedName("senderImageAssetId")
    @Expose
    private String senderImageAssetId;
    @SerializedName("readRequiredYn")
    @Expose
    private String readRequiredYn;
    @SerializedName("readRequiredDate")
    @Expose
    private String readRequiredDate;
    @SerializedName("replyCountNew")
    @Expose
    private Integer replyCountNew;
    @SerializedName("resultSetRowId")
    @Expose
    private Integer resultSetRowId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(String parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public String getOriginalMessageId() {
        return originalMessageId;
    }

    public void setOriginalMessageId(String originalMessageId) {
        this.originalMessageId = originalMessageId;
    }

    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderNameFull() {
        return senderNameFull;
    }

    public void setSenderNameFull(String senderNameFull) {
        this.senderNameFull = senderNameFull;
    }

    public String getSenderRoleName() {
        return senderRoleName;
    }

    public void setSenderRoleName(String senderRoleName) {
        this.senderRoleName = senderRoleName;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }

    public String getSenderImageAssetId() {
        return senderImageAssetId;
    }

    public void setSenderImageAssetId(String senderImageAssetId) {
        this.senderImageAssetId = senderImageAssetId;
    }

    public String getReadRequiredYn() {
        return readRequiredYn;
    }

    public void setReadRequiredYn(String readRequiredYn) {
        this.readRequiredYn = readRequiredYn;
    }

    public String getReadRequiredDate() {
        return readRequiredDate;
    }

    public void setReadRequiredDate(String readRequiredDate) {
        this.readRequiredDate = readRequiredDate;
    }

    public Integer getReplyCountNew() {
        return replyCountNew;
    }

    public void setReplyCountNew(Integer replyCountNew) {
        this.replyCountNew = replyCountNew;
    }

    public Integer getResultSetRowId() {
        return resultSetRowId;
    }

    public void setResultSetRowId(Integer resultSetRowId) {
        this.resultSetRowId = resultSetRowId;
    }
}
