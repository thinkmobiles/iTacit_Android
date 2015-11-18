package com.itacit.healthcare.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 23.10.15.
 */
public class ListRequest {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("rowCount")
    @Expose
    private Integer rowCount;
    @SerializedName("fields")
    @Expose
    private String fields;

    /**
     *
     * @return
     * The query
     */
    public String getQuery() {
        return query;
    }

    /**
     *
     * @param query
     * The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     *
     * @return
     * The sort
     */
    public String getSort() {
        return sort;
    }

    /**
     *
     * @param sort
     * The sort
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     *
     * @return
     * The startIndex
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @param startIndex
     * The startIndex
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return
     * The rowCount
     */
    public Integer getRowCount() {
        return rowCount;
    }

    /**
     *
     * @param rowCount
     * The rowCount
     */
    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String _fields) {
        fields = _fields;
    }
}