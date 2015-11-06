package com.itacit.healthcare.presentation.news.models;

import android.net.Uri;

/**
 * Created by root on 20.10.15.
 */
public class NewsModel {
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
