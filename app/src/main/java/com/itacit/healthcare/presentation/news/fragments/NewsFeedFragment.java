package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetNewsInteractor;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenterImpl;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.widget.WidgetObservable;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter> implements INewsFeedView {
    @Bind(R.id.et_search_FN)
    EditText searchNewsEt;

    @Bind(R.id.recycler_view_FN)
    RecyclerView mRecyclerView;

    @Override
    protected void setUpView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.title_news_feed);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_feed;
    }

    @Override
    protected NewsFeedPresenter createPresenter() {
        return new NewsFeedPresenterImpl(new GetNewsInteractor());
    }

    @Override
    public void showNews(List<NewsModel> news) {

    }

    @Override
    public void newsItemSelected(NewsModel newsItem) {

    }

    @Override
    public Observable<String> getNewsSearchTextObs() {
        return WidgetObservable.text(searchNewsEt).map(e -> e.text().toString());
    }
}
