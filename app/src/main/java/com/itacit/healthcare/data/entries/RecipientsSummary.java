package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsSummary {
    @SerializedName("totalRecipients")
    @Expose
    private Integer totalRecipients;
//    @SerializedName("recipientsList")
//    @Expose
//    private RecipientsList recipientsList;

    public Integer getTotalRecipients() {
        return totalRecipients;
    }

    public void setTotalRecipients(Integer totalRecipients) {
        this.totalRecipients = totalRecipients;
    }

//    public RecipientsList getRecipientsList() {
//        return recipientsList;
//    }
//
//    public void setRecipientsList(RecipientsList recipientsList) {
//        this.recipientsList = recipientsList;
//    }

    public class RecipientsList {
        @SerializedName("roles")
        @Expose
        private List<RecipientSummaryItem> roles;
        @SerializedName("employees")
        @Expose
        private List<RecipientSummaryItem> employees;
        @SerializedName("businessUnits")
        @Expose
        private List<RecipientSummaryItem> businessUnits;
        @SerializedName("groups")
        @Expose
        private List<RecipientSummaryItem> groups;
        @SerializedName("jobClassifications")
        @Expose
        private List<RecipientSummaryItem> jobClassifications;

        public List<RecipientSummaryItem> getRoles() {
            return roles;
        }

        public void setRoles(List<RecipientSummaryItem> roles) {
            this.roles = roles;
        }

        public List<RecipientSummaryItem> getEmployees() {
            return employees;
        }

        public void setEmployees(List<RecipientSummaryItem> employees) {
            this.employees = employees;
        }

        public List<RecipientSummaryItem> getBusinessUnits() {
            return businessUnits;
        }

        public void setBusinessUnits(List<RecipientSummaryItem> businessUnits) {
            this.businessUnits = businessUnits;
        }

        public List<RecipientSummaryItem> getGroups() {
            return groups;
        }

        public void setGroups(List<RecipientSummaryItem> groups) {
            this.groups = groups;
        }

        public List<RecipientSummaryItem> getJobClassifications() {
            return jobClassifications;
        }

        public void setJobClassifications(List<RecipientSummaryItem> jobClassifications) {
            this.jobClassifications = jobClassifications;
        }
    }

    public class RecipientSummaryItem {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("parentName")
        @Expose
        private String parentName;
        @SerializedName("roleName")
        @Expose
        private String roleName;
        @SerializedName("nameFull")
        @Expose
        private String nameFull;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getNameFull() {
            return nameFull;
        }

        public void setNameFull(String nameFull) {
            this.nameFull = nameFull;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

}
