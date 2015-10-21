package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.presentation.base.presenters.IPresenter;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.presenters.NewsSearchFilterPresenter;

/**
 * Created by root on 21.10.15.
 */
public class NewsSearchFilterFragment extends BaseFragmentView<NewsSearchFilterPresenter>{
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
    protected NewsSearchFilterPresenter createPresenter() {
        return null;
    }
}
