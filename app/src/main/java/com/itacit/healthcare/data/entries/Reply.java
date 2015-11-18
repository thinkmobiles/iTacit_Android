package com.itacit.healthcare.data.entries;

/**
 * Created by Den on 16.11.15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reply {
    //Default fields
    @SerializedName("sendDateTime")
    @Expose
    private String sendDateTime;
    @SerializedName("readConfirmedYn")
    @Expose
    private String readConfirmedYn;
    @SerializedName("senderImageUrl")
    @Expose
    private String senderImageUrl;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("replyPrivateYn")
    @Expose
    private String replyPrivateYn;
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("senderRoleName")
    @Expose
    private String senderRoleName;
    @SerializedName("senderNameFull")
    @Expose
    private String senderNameFull;
    @SerializedName("parentMessageId")
    @Expose
    private String parentMessageId;
    @SerializedName("replyMethodEmailYn")
    @Expose
    private String replyMethodEmailYn;
    @SerializedName("replyMethodSMSYn")
    @Expose
    private String replyMethodSMSYn;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("senderImageAssetId")
    @Expose
    private String senderImageAssetId;
    //Additional fields
    @SerializedName("responseRows")
    @Expose
    private List<RecipientOfRepliers> mRecipients;
    @SerializedName("responseCount")
    @Expose
    private Integer responseCount;

    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("subject")
    @Expose
    private String subject;

    public String getSendDateTime() {
        return sendDateTime;
    }

    /**
     *
     * @param sendDateTime
     * The sendDateTime
     */
    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    /**
     *
     * @return
     * The readConfirmedYn
     */
    public String getReadConfirmedYn() {
        return readConfirmedYn;
    }

    /**
     *
     * @param readConfirmedYn
     * The readConfirmedYn
     */
    public void setReadConfirmedYn(String readConfirmedYn) {
        this.readConfirmedYn = readConfirmedYn;
    }

    /**
     *
     * @return
     * The senderImageUrl
     */
    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    /**
     *
     * @param senderImageUrl
     * The senderImageUrl
     */
    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The replyPrivateYn
     */
    public String getReplyPrivateYn() {
        return replyPrivateYn;
    }

    /**
     *
     * @param replyPrivateYn
     * The replyPrivateYn
     */
    public void setReplyPrivateYn(String replyPrivateYn) {
        this.replyPrivateYn = replyPrivateYn;
    }

    /**
     *
     * @return
     * The senderId
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     *
     * @param senderId
     * The senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     *
     * @return
     * The senderRoleName
     */
    public String getSenderRoleName() {
        return senderRoleName;
    }

    /**
     *
     * @param senderRoleName
     * The senderRoleName
     */
    public void setSenderRoleName(String senderRoleName) {
        this.senderRoleName = senderRoleName;
    }

    /**
     *
     * @return
     * The senderNameFull
     */
    public String getSenderNameFull() {
        return senderNameFull;
    }

    /**
     *
     * @param senderNameFull
     * The senderNameFull
     */
    public void setSenderNameFull(String senderNameFull) {
        this.senderNameFull = senderNameFull;
    }

    /**
     *
     * @return
     * The parentMessageId
     */
    public String getParentMessageId() {
        return parentMessageId;
    }

    /**
     *
     * @param parentMessageId
     * The parentMessageId
     */
    public void setParentMessageId(String parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    /**
     *
     * @return
     * The replyMethodEmailYn
     */
    public String getReplyMethodEmailYn() {
        return replyMethodEmailYn;
    }

    /**
     *
     * @param replyMethodEmailYn
     * The replyMethodEmailYn
     */
    public void setReplyMethodEmailYn(String replyMethodEmailYn) {
        this.replyMethodEmailYn = replyMethodEmailYn;
    }

    /**
     *
     * @return
     * The replyMethodSMSYn
     */
    public String getReplyMethodSMSYn() {
        return replyMethodSMSYn;
    }

    /**
     *
     * @param replyMethodSMSYn
     * The replyMethodSMSYn
     */
    public void setReplyMethodSMSYn(String replyMethodSMSYn) {
        this.replyMethodSMSYn = replyMethodSMSYn;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The senderImageAssetId
     */
    public String getSenderImageAssetId() {
        return senderImageAssetId;
    }

    /**
     *
     * @param senderImageAssetId
     * The senderImageAssetId
     */
    public void setSenderImageAssetId(String senderImageAssetId) {
        this.senderImageAssetId = senderImageAssetId;
    }

    public List<RecipientOfRepliers> getRecipients() {
        return mRecipients;
    }

    public void setRecipients(List<RecipientOfRepliers> _recipients) {
        mRecipients = _recipients;
    }

    public Integer getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(Integer _responseCount) {
        responseCount = _responseCount;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender _sender) {
        sender = _sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String _subject) {
        subject = _subject;
    }
}
