package com.itacit.healthcare.domain.models;

import java.util.Date;

/**
 * Created by root on 17.11.15.
 */
public class CreateMessageModel {
    private RecipientsGroupedModel recipients = new RecipientsGroupedModel();
    private String subject;
    private String body;
    private Boolean readRequired = false;
    private Date readRequiredDate;

    public RecipientsGroupedModel getRecipients() {
        return recipients;
    }

    public void setRecipients(RecipientsGroupedModel recipients) {
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
