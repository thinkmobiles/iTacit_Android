package com.itacit.healthcare.presentation.news.views.chipsView;

/**
 * Created by root on 22.10.15.
 */
public interface FilterChip extends Chip {
    public enum FilterType {Author, Category}

    public FilterType getFilterType();
    public void setFilterType(FilterType filterType);
}
