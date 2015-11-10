package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class Author {
    @SerializedName("authorId")
    @Expose
    private String id;
    @SerializedName("authorNameFull")
    @Expose
    private String fullName;
    @SerializedName("authorRoleName")
    @Expose
    private String role;
    @SerializedName("authorImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("authorImageAssetId")
    @Expose
    private String imageAssetId;
    @SerializedName("mostRecentArticlePublishDate")
    @Expose
    private String mostRecentPublishDate;
    @SerializedName("resultSetRowId")
    @Expose
    private String resultSetRowId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageAssetId() {
        return imageAssetId;
    }

    public void setImageAssetId(String imageAssetId) {
        this.imageAssetId = imageAssetId;
    }

    public String getMostRecentPublishDate() {
        return mostRecentPublishDate;
    }

    public void setMostRecentPublishDate(String mostRecentPublishDate) {
        this.mostRecentPublishDate = mostRecentPublishDate;
    }

    public String getResultSetRowId() {
        return resultSetRowId;
    }

    public void setResultSetRowId(String resultSetRowId) {
        this.resultSetRowId = resultSetRowId;
    }
}
