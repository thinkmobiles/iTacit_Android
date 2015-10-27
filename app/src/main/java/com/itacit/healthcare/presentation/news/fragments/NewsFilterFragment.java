package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;
import android.view.ViewTreeObserver;

import com.itacit.healthcare.R;
import com.itacit.healthcare.chipsView.ChipsEditText;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.presenters.NewsFilterPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFilterView;

import butterknife.Bind;

/**
 * Created by root on 21.10.15.
 */
public class NewsFilterFragment extends BaseFragmentView<NewsFilterPresenter> implements INewsFilterView {
    @Bind(R.id.et_serch_FNSF)
    ChipsEditText mSearchFiltersEt;

    @Override
    protected void setUpView() {

        ViewTreeObserver observer = mSearchFiltersEt.getViewTreeObserver();
        observer.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSearchFiltersEt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mSearchFiltersEt.addChip("JohnCarson");
            }
        });
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_search_filter;
    }

    @Override
    protected NewsFilterPresenter createPresenter() {
        return new NewsFilterPresenter();
    }
}
