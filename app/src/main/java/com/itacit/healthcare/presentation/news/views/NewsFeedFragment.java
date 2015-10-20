package com.itacit.healthcare.presentation.news.views;

import android.widget.EditText;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenterImpl;

import java.util.List;

import rx.Observable;
import rx.android.widget.WidgetObservable;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter> implements INewsFeedView {

    EditText mSearchNewsEt;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_feed;
    }

    @Override
    protected NewsFeedPresenter createPresenter() {
        return new NewsFeedPresenterImpl();
    }

    @Override
    public void showNews(List<NewsModel> news) {

    }

    @Override
    public void newsItemSelected(NewsModel newsItem) {

    }

    @Override
    public Observable<String> getNewsSearchTextObs() {
        return WidgetObservable.text(mSearchNewsEt).map(e -> e.text().toString());
    }
}
