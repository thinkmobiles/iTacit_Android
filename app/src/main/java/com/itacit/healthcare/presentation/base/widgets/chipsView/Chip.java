package com.itacit.healthcare.presentation.base.widgets.chipsView;

import android.graphics.Bitmap;

/**
 * Created by root on 03.11.15.
 */
public class Chip {
    public enum FilterType {Author, Category}
    private final String id;
    private final String visibleText;
    private final FilterType filterType;
    private Bitmap image;

    public Chip(String id, String visibleText, FilterType filterType) {
        this.id = id;
        this.visibleText = visibleText;
        this.filterType = filterType;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getId() {
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
        if (o instanceof Chip) {
            Chip other = (Chip) o;
            if (other.getFilterType() != filterType) {
                return false;
            }

            if (!other.getId().equals(id)) {
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
