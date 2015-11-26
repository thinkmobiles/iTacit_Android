package com.itacit.healthcare.domain.models;

import com.itacit.healthcare.presentation.base.widgets.chipsView.Chip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 06.11.15.
 */
public class NewsSearch extends GetListParams {
    private List<Chip> chips;
    private Calendar dateFrom;
    private Calendar dateTo;
    private String search = "";


    public NewsSearch(List<Chip> chips, Calendar dateFrom, Calendar dateTo) {
        this.chips = chips;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public NewsSearch() {
        chips = new ArrayList<>();
    }


    public void setChips(List<Chip> chips) {
        this.chips = chips;
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

    public List<Chip> getChips() {
        return chips;
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
