package com.itacit.healthcare.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsInfoRequest {
    @SerializedName("mixedList")
    @Expose
    private List<Map<String, String>> recipients;

    public List<Map<String, String>> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Map<String, String>> recipients) {
        this.recipients = recipients;
    }
}
