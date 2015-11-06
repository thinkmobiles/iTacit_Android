package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.IView;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;

/**
 * Created by root on 21.10.15.
 */
public interface INewsDetailsView extends IView {
    void showNewsDetails(NewsDetailsModel newsDetails);
    void showAuthorDetails(AuthorModel authorModel);
}