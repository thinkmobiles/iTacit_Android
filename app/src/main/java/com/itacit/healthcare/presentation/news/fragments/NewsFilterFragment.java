package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.presenters.NewsFilterPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFilterView;

/**
 * Created by root on 21.10.15.
 */
public class NewsFilterFragment extends BaseFragmentView<NewsFilterPresenter> implements INewsFilterView {
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
    protected NewsFilterPresenter createPresenter() {
        return null;
    }
}
