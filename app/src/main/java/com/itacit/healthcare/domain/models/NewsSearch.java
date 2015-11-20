package com.itacit.healthcare.domain.models;

import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 06.11.15.
 */
public class NewsSearch extends GetListParams {
    private List<Filter> filters;
    private Calendar dateFrom;
    private Calendar dateTo;
    private String search = "";


    public NewsSearch(List<Filter> filters, Calendar dateFrom, Calendar dateTo) {
        this.filters = filters;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public NewsSearch() {
        filters = new ArrayList<>();
    }


    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void setDateFrom(Calendar dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Calendar dateTo) {
        this.dateTo = dateTo;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public Calendar getDateFrom() {
        return dateFrom;
    }

    public Calendar getDateTo() {
        return dateTo;
    }

    public String getSearch() {
        return search;
    }

}
