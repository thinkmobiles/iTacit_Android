package com.itacit.healthcare.presentation.news.views.activity;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.news.views.NewsStorage;
import com.itacit.healthcare.presentation.news.views.fragments.NewsFeedFragment;

import java.util.Stack;

import rx.subjects.BehaviorSubject;

/**
 * Created by root on 21.10.15.
 */
public class NewsActivity extends BaseActivity implements NewsStorage {
    private Stack<NewsSearch> newsStorage;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newsStorage = new Stack<>();
        newsStorage.setSize(1);
        newsStorage.push(new NewsSearch());

        super.onCreate(savedInstanceState);
        switchContent(NewsFeedFragment.class);
    }

    public NewsSearch getNews() {
	    return newsStorage.peek();
    }

	public NewsSearch popNews() {
		NewsSearch newsSearch = newsStorage.peek();
		newsStorage.push(new NewsSearch());
		return newsSearch;
	}

	public void pushNews(NewsSearch model) {
		newsStorage.push(model);
	}
}
