package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.presenters.NewsDetailsPresenter;
import com.itacit.healthcare.presentation.news.views.INewsDetailsView;

/**
 * Created by root on 21.10.15.
 */
public class NewsDetailsFragment extends BaseFragmentView<NewsDetailsPresenter> implements INewsDetailsView {
    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {

    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected NewsDetailsPresenter createPresenter() {
        return null;
    }
}
