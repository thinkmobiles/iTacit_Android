package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

/**
 * Created by root on 14.10.15.
 */
interface INewsFeedPresenter {
    public abstract void loadNews();
    public abstract void searchNews(String searchWord);

}
