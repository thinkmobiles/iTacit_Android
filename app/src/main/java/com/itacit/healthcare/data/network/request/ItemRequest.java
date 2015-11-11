package com.itacit.healthcare.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11.11.15.
 */
public class ItemRequest {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fields")
    @Expose
    private String fields;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("rowCount")
    @Expose
    private Integer rowCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
