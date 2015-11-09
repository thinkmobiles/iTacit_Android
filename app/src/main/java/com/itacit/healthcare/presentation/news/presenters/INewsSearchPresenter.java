package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.models.NewsSearch;

/**
 * Created by root on 21.10.15.
 */
public interface INewsSearchPresenter {
    NewsSearch getNewsSearch();
    void getAuthors(String query);
    void getCategories(String query);
    void selectAuthorFilterById(long id);
    void selectCategoryFilterById(long id);
    void onDateSelected(DateType dateType, int year, int monthOfYear, int dayOfMonth);
    void onDateClear(DateType dateType);
    boolean isDateValid();
    enum DateType {From, To}
}
