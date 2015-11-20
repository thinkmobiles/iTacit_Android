package com.itacit.healthcare.presentation.messages.models;

import android.net.Uri;

/**
 * Created by Den on 13.11.15.
 */
public class MessageModel {

    private String id;
    private Uri headlineUri;
    private String senderName;
    private String lastTimeResponse;
    private Integer numberOfResponse;
    private String senderRoleName;
    private String subject;
    private String body;
    private boolean readRequiredYn;
    private String readRequiredDate;

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }

    public Uri getHeadlineUri() {
        return headlineUri;
    }

    public void setHeadlineUri(Uri _headlineUri) {
        headlineUri = _headlineUri;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String _senderName) {
        senderName = _senderName;
    }

    public String getLastTimeResponse() {
        return lastTimeResponse;
    }

    public void setLastTimeResponse(String _lastTimeResponse) {
        lastTimeResponse = _lastTimeResponse;
    }

    public Integer getNumberOfResponse() {
        return numberOfResponse;
    }

    public void setNumberOfResponse(Integer _numberOfResponse) {
        numberOfResponse = _numberOfResponse;
    }

    public String getSenderRoleName() {
        return senderRoleName;
    }

    public void setSenderRoleName(String _senderRoleName) {
        senderRoleName = _senderRoleName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String _subject) {
        subject = _subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String _body) {
        body = _body;
    }

    public boolean getReadRequiredYn() {
        return readRequiredYn;
    }

    public void setReadRequiredYn(boolean _readRequiredYn) {
        readRequiredYn = _readRequiredYn;
    }

    public String getReadRequiredDate() {
        return readRequiredDate;
    }

    public void setReadRequiredDate(String _readRequiredDate) {
        readRequiredDate = _readRequiredDate;
    }
}
