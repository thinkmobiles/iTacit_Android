package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;

/**
 * Created by root on 21.10.15.
 */
public interface NewsDetailsView extends View {
    void showNewsDetails(NewsDetailsModel newsDetails);
    void showAuthorDetails(AuthorModel authorModel);
}