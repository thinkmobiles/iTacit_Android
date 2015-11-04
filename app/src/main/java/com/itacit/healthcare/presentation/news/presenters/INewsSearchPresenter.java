package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;

/**
 * Created by root on 21.10.15.
 */
interface INewsSearchPresenter {
    void getAuthors(String query);
    void getCategories(String query);
    void selectAuthorFilterById(long id);
    void selectCategoryFilterById(long id);
}
