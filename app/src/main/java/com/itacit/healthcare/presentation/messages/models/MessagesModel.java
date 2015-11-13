package com.itacit.healthcare.presentation.messages.models;

import android.net.Uri;

/**
 * Created by Den on 13.11.15.
 */
public class MessagesModel {

//    private Uri headlineUri;
    private long id;
    private String senderName;
    private String lastTimeResponse;
    private String numberOfResponse;
    private String senderPosition;
    private String subject;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long _id) {
        id = _id;
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

    public String getNumberOfResponse() {
        return numberOfResponse;
    }

    public void setNumberOfResponse(String _numberOfResponse) {
        numberOfResponse = _numberOfResponse;
    }

    public String getSenderPosition() {
        return senderPosition;
    }

    public void setSenderPosition(String _senderPosition) {
        senderPosition = _senderPosition;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String _subject) {
        subject = _subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String _content) {
        content = _content;
    }
}
