package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 29.10.15.
 */
public class NewsDetails {

    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("articleId")
    @Expose
    private String articleId;
    @SerializedName("authorName")
    @Expose
    private String authorName;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("authorId")
    @Expose
    private String authorId;
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

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The articleId
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     *
     * @param articleId
     * The articleId
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     *
     * @return
     * The authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     *
     * @param authorName
     * The authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @param categoryName
     * The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     *
     * @return
     * The headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     *
     * @param headline
     * The headline
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     *
     * @return
     * The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The authorId
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     *
     * @param authorId
     * The authorId
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    /**
     *
     * @return
     * The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     * The endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     * The headlineImageUrl
     */
    public String getHeadlineImageUrl() {
        return headlineImageUrl;
    }

    /**
     *
     * @param headlineImageUrl
     * The headlineImageUrl
     */
    public void setHeadlineImageUrl(String headlineImageUrl) {
        this.headlineImageUrl = headlineImageUrl;
    }

    /**
     *
     * @return
     * The headlineImageAssetId
     */
    public String getHeadlineImageAssetId() {
        return headlineImageAssetId;
    }

    /**
     *
     * @param headlineImageAssetId
     * The headlineImageAssetId
     */
    public void setHeadlineImageAssetId(String headlineImageAssetId) {
        this.headlineImageAssetId = headlineImageAssetId;
    }

}