package com.itacit.healthcare.presentation.base.widgets.chipsView;

/**
 * Created by root on 03.11.15.
 */
public class Filter {
    public enum FilterType {Author, Category}
    private final long id;
    private final String visibleText;
    private final FilterType filterType;

    public Filter(long id, String visibleText, FilterType filterType) {
        this.id = id;
        this.visibleText = visibleText;
        this.filterType = filterType;
    }

    public long getId() {
        return id;
    }

    public String getVisibleText() {
        return visibleText;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Filter) {
            Filter other = (Filter) o;
            if (other.getFilterType() != filterType) {
                return false;
            }

            if (other.getId() != id) {
                return false;
            }

            if (!other.getVisibleText().equals(visibleText)) {
                return false;
            }

        } else {
            return false;
        }

        return true;
    }
}
