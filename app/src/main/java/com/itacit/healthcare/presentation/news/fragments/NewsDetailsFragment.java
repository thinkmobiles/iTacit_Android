package com.itacit.healthcare.presentation.news.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.views.BaseFragmentView;
import com.itacit.healthcare.presentation.news.mapper.NewsDetailsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.presenters.NewsDetailsPresenter;
import com.itacit.healthcare.presentation.news.views.INewsDetailsView;

/**
 * Created by root on 21.10.15.
 */
public class NewsDetailsFragment extends BaseFragmentView<NewsDetailsPresenter> implements INewsDetailsView {
    public static final String NEWS_ID = "newsId";



    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_details;
    }

    @Override
    protected NewsDetailsPresenter createPresenter() {
        long newsId = getArguments().getLong("newsId");
        return new NewsDetailsPresenter(new GetNewsDetailsUseCase(newsId), new NewsDetailsModelMapper());
    }

    @Override
    public void showNewsDetails(NewsDetailsModel newsDetails) {

    }
}
