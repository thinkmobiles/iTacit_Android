package com.itacit.healthcare.data.entries;

/**
 * Created by Den on 19.11.15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupRecipients {
    @SerializedName("responseRows")
    @Expose
    private List<Recipient> recipients;
    @SerializedName("responseCount")
    @Expose
    private Integer responseCount;

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> _recipients) {
        recipients = _recipients;
    }

    public Integer getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(Integer _responseCount) {
        responseCount = _responseCount;
    }
}
