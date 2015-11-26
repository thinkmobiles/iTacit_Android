package com.itacit.healthcare.presentation.news.models;

import android.net.Uri;

import com.itacit.healthcare.presentation.base.model.BaseModel;
import com.itacit.healthcare.presentation.messages.models.UserModel;

/**
 * Created by root on 29.10.15.
 */
public class NewsDetailsModel extends BaseModel {
    private Uri headlineUri;
    private String startDate;
    private String categoryName;
    private String headline;
    private String body;
    private UserModel author;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }
}
