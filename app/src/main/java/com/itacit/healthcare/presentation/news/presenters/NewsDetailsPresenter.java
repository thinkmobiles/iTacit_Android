package com.itacit.healthcare.presentation.news.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.news.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mappers.NewsDetailsMapper;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.views.NewsDetailsView;

/**
 * Created by root on 26.10.15.
 */
public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {
    private GetNewsDetailsUseCase newsDetailsUseCase;
    private NewsDetailsMapper dataMapper;
    private String newsId;

    public NewsDetailsPresenter(GetNewsDetailsUseCase newsDetailsUseCase, NewsDetailsMapper dataMapper, String newsId) {
        this.newsDetailsUseCase = newsDetailsUseCase;
        this.dataMapper = dataMapper;
        this.newsId = newsId;
    }

    @Override
    protected void onAttachedView(@NonNull NewsDetailsView view) {
        loadNewsDetails();
    }

    private void showDetailsOnView(News newsDetails) {
        NewsDetailsModel model = dataMapper.transform(newsDetails);
        if(getView() != null) {
            getView().showNewsDetails(model);
            getView().showAuthorDetails(model.getAuthor());
        }
    }

    public void loadNewsDetails() {
        newsDetailsUseCase.execute(this::showDetailsOnView, errorHandler, newsId);
    }
}
