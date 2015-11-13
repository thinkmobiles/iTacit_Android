package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 21.10.15.
 */
public class News {
    //Default fields
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("authorId")
    @Expose
    private String authorId;
    @SerializedName("authorName")
    @Expose
    private String authorName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("headlineImageUrl")
    @Expose
    private String headlineImageUrl;
    @SerializedName("headlineImageAssetId")
    @Expose
    private String headlineImageAssetId;
    @SerializedName("resultSetRowId")
    @Expose
    private String resultSetRowId;
    //Additional Fields
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("author")
    @Expose
    private User author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHeadlineImageUrl() {
        return headlineImageUrl;
    }

    public void setHeadlineImageUrl(String headlineImageUrl) {
        this.headlineImageUrl = headlineImageUrl;
    }

    public String getHeadlineImageAssetId() {
        return headlineImageAssetId;
    }

    public void setHeadlineImageAssetId(String headlineImageAssetId) {
        this.headlineImageAssetId = headlineImageAssetId;
    }

    public String getResultSetRowId() {
        return resultSetRowId;
    }

    public void setResultSetRowId(String resultSetRowId) {
        this.resultSetRowId = resultSetRowId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}