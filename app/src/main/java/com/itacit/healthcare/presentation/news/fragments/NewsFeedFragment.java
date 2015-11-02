package com.itacit.healthcare.presentation.news.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetNewsUseCase;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.adapters.NewsAdapter;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import butterknife.Bind;
import rx.Observable;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter> implements INewsFeedView {
    @Bind(R.id.et_search_FN)
    EditText searchNewsView;

    @Bind(R.id.recycler_view_FN)
    RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadNews();
    }

    @Override
    protected void setUpView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(layoutManager);
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
        return new NewsFeedPresenter(new GetNewsUseCase(0, 100), new NewsModelMapper());
    }

    @Override
    public void showNews(List<NewsModel> news) {
        newsAdapter = new NewsAdapter(getActivity(), news);
        newsRecyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnNewsItemSelectedListener(this::showNewsItemDetails);
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    @Override
    public void showNewsItemDetails(long newsId) {
        Bundle args = new Bundle(1);
        args.putLong(NewsDetailsFragment.NEWS_ID, newsId);
        ((BaseActivity) getActivity()).switchContent(NewsDetailsFragment.class, true, args);
    }

    @Override
    public Observable<String> getNewsSearchTextObs() {
        return RxTextView.textChangeEvents(searchNewsView).map(e -> e.text().toString());
    }
}
