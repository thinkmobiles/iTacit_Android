package com.itacit.healthcare.presentation.news.models;

import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 06.11.15.
 */
public class NewsSearch {
    private List<Filter> filters;
    private Date dateFrom;
    private Date dateTo;
    private String search;


    public NewsSearch(List<Filter> filters, Date dateFrom, Date dateTo) {
        this.filters = filters;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
