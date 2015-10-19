package com.itacit.healthcare.presenters;

import com.itacit.healthcare.presenters.base.BasePresenter;
import com.itacit.healthcare.views.INewsFeedView;

/**
 * Created by root on 14.10.15.
 */
public abstract class NewsFeedPresenter extends BasePresenter<INewsFeedView> {
    public abstract void loadNews();
}
