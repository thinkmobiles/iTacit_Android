package com.itacit.healthcare.presentation.news.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.news.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mappers.NewsDetailsMapper;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.views.NewsDetailsView;

import rx.Subscriber;

/**
 * Created by root on 26.10.15.
 */
public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {
    private GetNewsDetailsUseCase newsDetailsUseCase;
    private NewsDetailsMapper dataMapper;

    public NewsDetailsPresenter(GetNewsDetailsUseCase newsDetailsUseCase, NewsDetailsMapper dataMapper) {
        this.newsDetailsUseCase = newsDetailsUseCase;
        this.dataMapper = dataMapper;
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
        newsDetailsUseCase.execute(new NewDetailsSubscriber());
    }

    private final class NewDetailsSubscriber extends Subscriber<News> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(News newsDetails) {
            showDetailsOnView(newsDetails);
        }
    }
}
