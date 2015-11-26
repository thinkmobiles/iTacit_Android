package com.itacit.healthcare.presentation.messages.models;

import android.net.Uri;

import com.itacit.healthcare.presentation.base.model.BaseModel;

/**
 * Created by Den on 16.11.15.
 */
public class RepliesModel extends BaseModel {
    private String sendDateTime;
    private boolean readConfirmedYn;
    private Uri senderImageUri;
    private String body;
    private boolean replyPrivateYn;
    private String senderId;
    private String senderRoleName;
    private String senderNameFull;
    private String parentMessageId;
    private boolean replyMethodEmailYn;
    private boolean replyMethodSMSYn;
    private String senderImageAssetId;
    private Integer responseCount;

    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String _sendDateTime) {
        sendDateTime = _sendDateTime;
    }

    public Uri getSenderImageUri() {
        return senderImageUri;
    }

    public void setSenderImageUri(Uri _senderImageUrl) {
        senderImageUri = _senderImageUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String _body) {
        body = _body;
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

    public String getSenderImageAssetId() {
        return senderImageAssetId;
    }

    public void setSenderImageAssetId(String _senderImageAssetId) {
        senderImageAssetId = _senderImageAssetId;
    }

    public boolean isReadConfirmedYn() {
        return readConfirmedYn;
    }

    public void setReadConfirmedYn(boolean _readConfirmedYn) {
        readConfirmedYn = _readConfirmedYn;
    }

    public boolean isReplyPrivateYn() {
        return replyPrivateYn;
    }

    public void setReplyPrivateYn(boolean _replyPrivateYn) {
        replyPrivateYn = _replyPrivateYn;
    }

    public boolean isReplyMethodEmailYn() {
        return replyMethodEmailYn;
    }

    public void setReplyMethodEmailYn(boolean _replyMethodEmailYn) {
        replyMethodEmailYn = _replyMethodEmailYn;
    }

    public boolean isReplyMethodSMSYn() {
        return replyMethodSMSYn;
    }

    public void setReplyMethodSMSYn(boolean _replyMethodSMSYn) {
        replyMethodSMSYn = _replyMethodSMSYn;
    }

    public Integer getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(Integer _responseCount) {
        responseCount = _responseCount;
    }
}
