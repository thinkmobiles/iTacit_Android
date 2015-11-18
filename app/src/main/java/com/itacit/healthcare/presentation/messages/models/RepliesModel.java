package com.itacit.healthcare.presentation.messages.models;

import android.net.Uri;

/**
 * Created by Den on 16.11.15.
 */
public class RepliesModel {
    private String sendDateTime;
    private boolean readConfirmedYn;
    private Uri senderImageUrl;
    private String body;
    private boolean replyPrivateYn;
    private String senderId;
    private String senderRoleName;
    private String senderNameFull;
    private String parentMessageId;
    private boolean replyMethodEmailYn;
    private boolean replyMethodSMSYn;
    private String id;
    private String senderImageAssetId;

    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String _sendDateTime) {
        sendDateTime = _sendDateTime;
    }

    public boolean getReadConfirmedYn() {
        return readConfirmedYn;
    }

    public void setReadConfirmedYn(boolean _readConfirmedYn) {
        readConfirmedYn = _readConfirmedYn;
    }

    public Uri getSenderImageUri() {
        return senderImageUrl;
    }

    public void setSenderImageUri(Uri _senderImageUrl) {
        senderImageUrl = _senderImageUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String _body) {
        body = _body;
    }

    public boolean getReplyPrivateYn() {
        return replyPrivateYn;
    }

    public void setReplyPrivateYn(boolean _replyPrivateYn) {
        replyPrivateYn = _replyPrivateYn;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String _senderId) {
        senderId = _senderId;
    }

    public String getSenderRoleName() {
        return senderRoleName;
    }

    public void setSenderRoleName(String _senderRoleName) {
        senderRoleName = _senderRoleName;
    }

    public String getSenderNameFull() {
        return senderNameFull;
    }

    public void setSenderNameFull(String _senderNameFull) {
        senderNameFull = _senderNameFull;
    }

    public String getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(String _parentMessageId) {
        parentMessageId = _parentMessageId;
    }

    public boolean getReplyMethodEmailYn() {
        return replyMethodEmailYn;
    }

    public void setReplyMethodEmailYn(boolean _replyMethodEmailYn) {
        replyMethodEmailYn = _replyMethodEmailYn;
    }

    public boolean getReplyMethodSMSYn() {
        return replyMethodSMSYn;
    }

    public void setReplyMethodSMSYn(boolean _replyMethodSMSYn) {
        replyMethodSMSYn = _replyMethodSMSYn;
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getSenderImageAssetId() {
        return senderImageAssetId;
    }

    public void setSenderImageAssetId(String _senderImageAssetId) {
        senderImageAssetId = _senderImageAssetId;
    }
}
