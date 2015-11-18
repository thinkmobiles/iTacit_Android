package com.itacit.healthcare.presentation.messages.models;

import java.util.Date;

/**
 * Created by root on 17.11.15.
 */
public class CreateMessageModel {
    private RecipientsModel recipients;
    private String subject;
    private String body;
    private Boolean readRequire;
    private Date date;

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

    public Boolean getReadRequire() {
        return readRequire;
    }

    public void setReadRequire(Boolean readRequire) {
        this.readRequire = readRequire;
    }
}
