package com.itacit.healthcare.presentation.messages.models;

import java.util.Date;

/**
 * Created by root on 17.11.15.
 */
public class CreateMessageModel {
    private RecipientsModel recipients = new RecipientsModel();
    private String subject;
    private String body;
    private Boolean readRequired;
    private Date readRequiredDate;

    public RecipientsModel getRecipients() {
        return recipients;
    }

    public void setRecipients(RecipientsModel recipients) {
        this.recipients = recipients;
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

    public Boolean isReadRequired() {
        return readRequired;
    }

    public void setReadRequired(Boolean readRequired) {
        this.readRequired = readRequired;
    }

    public Date getReadRequiredDate() {
        return readRequiredDate;
    }

    public void setReadRequiredDate(Date readRequiredDate) {
        this.readRequiredDate = readRequiredDate;
    }
}
