package com.itacit.healthcare.presentation.news.presenters;

/**
 * Created by root on 21.10.15.
 */
public interface INewsSearchPresenter {

    void getAuthors(String query);
    void getCategories(String query);
    void selectAuthorFilterById(long id);
    void selectCategoryFilterById(long id);
    void onDateSelected(DateType dateType, int year, int monthOfYear, int dayOfMonth);
    void onDateClear(DateType dateType);
    enum DateType {From, To}
}
