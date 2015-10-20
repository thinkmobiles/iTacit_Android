package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

/**
 * Created by root on 14.10.15.
 */
public abstract class NewsFeedPresenter extends BasePresenter<INewsFeedView> {
    public abstract void loadNews();
    public abstract void searchNews(String searchWord);

}
