package com.itacit.healthcare.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.itacit.healthcare.data.entries.RecipientInfo;

import java.util.List;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsSummaryResponse {
    @SerializedName("totalRecipients")
    @Expose
    private Integer totalRecipients;
    @SerializedName("recipientsList")
    @Expose
    private RecipientsLists recipientsLists;

    public Integer getTotalRecipients() {
        return totalRecipients;
    }

    public void setTotalRecipients(Integer totalRecipients) {
        this.totalRecipients = totalRecipients;
    }

    public RecipientsLists getRecipientsLists() {
        return recipientsLists;
    }

    public void setRecipientsLists(RecipientsLists recipientsLists) {
        this.recipientsLists = recipientsLists;
    }

    public class RecipientsLists {
        @SerializedName("roles")
        @Expose
        private List<RecipientInfo> roles;
        @SerializedName("employees")
        @Expose
        private List<RecipientInfo> employees;
        @SerializedName("businessUnits")
        @Expose
        private List<RecipientInfo> businessUnits;
        @SerializedName("groups")
        @Expose
        private List<RecipientInfo> groups;
        @SerializedName("jobClassifications")
        @Expose
        private List<RecipientInfo> jobClassifications;

        public List<RecipientInfo> getRoles() {
            return roles;
        }

        public void setRoles(List<RecipientInfo> roles) {
            this.roles = roles;
        }

        public List<RecipientInfo> getEmployees() {
            return employees;
        }

        public void setEmployees(List<RecipientInfo> employees) {
            this.employees = employees;
        }

        public List<RecipientInfo> getBusinessUnits() {
            return businessUnits;
        }

        public void setBusinessUnits(List<RecipientInfo> businessUnits) {
            this.businessUnits = businessUnits;
        }

        public List<RecipientInfo> getGroups() {
            return groups;
        }

        public void setGroups(List<RecipientInfo> groups) {
            this.groups = groups;
        }

        public List<RecipientInfo> getJobClassifications() {
            return jobClassifications;
        }

        public void setJobClassifications(List<RecipientInfo> jobClassifications) {
            this.jobClassifications = jobClassifications;
        }
    }



}
