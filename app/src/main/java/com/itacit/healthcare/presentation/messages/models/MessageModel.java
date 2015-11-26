package com.itacit.healthcare.presentation.messages.models;

import android.net.Uri;

import com.itacit.healthcare.data.entries.Recipient;
import com.itacit.healthcare.presentation.base.model.BaseModel;

import java.util.List;

/**
 * Created by Den on 13.11.15.
 */
public class MessageModel extends BaseModel {

    private Uri headlineUri;
    private String senderName;
    private String timeSendMessage;
    private Integer numberOfResponse;
    private String senderRoleName;
    private String subject;
    private String body;
    private boolean readRequiredYn;
    private String readRequiredDate;
    private Integer responseCount;
    private List<Recipient> recipientsList;
    private String firstName;
    private String lastName;
    private boolean userMarksRead;

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

    public String getTimeSendMessage() {
        return timeSendMessage;
    }

    public void setTimeSendMessage(String _timeSendMessage) {
        timeSendMessage = _timeSendMessage;
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

    public void setReadRequiredYn(boolean _readRequiredYn) {
        readRequiredYn = _readRequiredYn;
    }

    public String getReadRequiredDate() {
        return readRequiredDate;
    }

    public void setReadRequiredDate(String _readRequiredDate) {
        readRequiredDate = _readRequiredDate;
    }

    public boolean isReadRequiredYn() {
        return readRequiredYn;
    }

    public Integer getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(Integer _responseCount) {
        responseCount = _responseCount;
    }

    public List<Recipient> getRecipientsList() {
        return recipientsList;
    }

    public void setRecipientsList(List<Recipient> _recipientsList) {
        recipientsList = _recipientsList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _firstName) {
        firstName = _firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String _lastName) {
        lastName = _lastName;
    }

    public boolean isUserMarksRead() {
        return userMarksRead;
    }

    public void setUserMarksRead(boolean userMarksRead) {
        this.userMarksRead = userMarksRead;
    }
}
