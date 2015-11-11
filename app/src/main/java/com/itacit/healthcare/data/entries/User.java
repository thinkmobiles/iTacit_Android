package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11.11.15.
 */
public class User {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nameFull")
    @Expose
    private String nameFull;

    @SerializedName("nameLast")
    @Expose
    private String nameLast;

    @SerializedName("nameFirst")
    @Expose
    private String nameFirst;

    @SerializedName("roleName")
    @Expose
    private String roleName;

    @SerializedName("jobClassificationId")
    @Expose
    private String jobId;

    @SerializedName("jobClassificationName")
    @Expose
    private String jobName;

    @SerializedName("businessUnitId")
    @Expose
    private String businessId;

    @SerializedName("businessUnitName")
    @Expose
    private String businessName;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("imageAssetId")
    @Expose
    private String imageId;

    @SerializedName("emailAddress")
    @Expose
    private String email;

    @SerializedName("mobilePhoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("notificationsEmailYn")
    @Expose
    private String notifyEmail;

    @SerializedName("notificationsSMSYn")
    @Expose
    private String notifySMS;

    @SerializedName("preferredLanguageId")
    @Expose
    private String languageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public String getNotifySMS() {
        return notifySMS;
    }

    public void setNotifySMS(String notifySMS) {
        this.notifySMS = notifySMS;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
