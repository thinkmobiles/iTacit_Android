package com.itacit.healthcare.presentation.news;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.news.fragments.NewsFeedFragment;
import com.itacit.healthcare.presentation.news.models.NewsSearch;

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
        searchNews = BehaviorSubject.create();
        getFragmentManager().beginTransaction().replace(R.id.content, new NewsFeedFragment()).commit();
    }

    public BehaviorSubject<NewsSearch> getSearchNews() {
        return searchNews;
    }
}
