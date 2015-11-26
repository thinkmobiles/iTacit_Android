package com.itacit.healthcare.presentation.news.views.adapters;

import static com.itacit.healthcare.presentation.base.widgets.chipsView.Chip.FilterType;

/**
 * Created by root on 16.11.15.
 */

public interface FilterSelectionListener {
    void onFilterSelected(String filterId, FilterType type);
    void onFilterDeselected(String filterId, FilterType type);
}