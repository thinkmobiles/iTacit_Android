package com.itacit.healthcare.presentation.news.views.activity;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.news.views.fragments.NewsFeedFragment;

import rx.subjects.BehaviorSubject;

/**
 * Created by root on 21.10.15.
 */
public class NewsActivity extends BaseActivity {
    private BehaviorSubject<NewsSearch> searchNews;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchNews = BehaviorSubject.create(new NewsSearch());
        switchContent(NewsFeedFragment.class);
    }

    public BehaviorSubject<NewsSearch> getNewsSearchSubj() {
        return searchNews;
    }
}
