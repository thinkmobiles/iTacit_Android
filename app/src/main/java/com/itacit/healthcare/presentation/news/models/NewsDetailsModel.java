package com.itacit.healthcare.presentation.news.models;

import android.net.Uri;

/**
 * Created by root on 29.10.15.
 */
public class NewsDetailsModel {
    private long articleId;
    private Uri headlineUri;
    private String startDate;
    private String categoryName;
    private String headline;

    public Uri getHeadlineUri() {
        return headlineUri;
    }

    public void setHeadlineUri(Uri headlineUri) {
        this.headlineUri = headlineUri;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
