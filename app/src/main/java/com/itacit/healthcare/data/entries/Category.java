package com.itacit.healthcare.data.entries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class Category {
    @SerializedName("categoryId")
    @Expose
    private String id;
    @SerializedName("categoryName")
    @Expose
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
