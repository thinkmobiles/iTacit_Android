package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.domain.interactor.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mapper.NewsDetailsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.views.INewsDetailsView;

import rx.Subscriber;

/**
 * Created by root on 26.10.15.
 */
public class NewsDetailsPresenter extends BasePresenter<INewsDetailsView> implements INewsDetailsPresenter {
    private GetNewsDetailsUseCase newsDetailsUseCase;
    private NewsDetailsModelMapper dataMapper;

    public NewsDetailsPresenter(GetNewsDetailsUseCase newsDetailsUseCase, NewsDetailsModelMapper dataMapper) {
        this.newsDetailsUseCase = newsDetailsUseCase;
        this.dataMapper = dataMapper;
    }

    private void showDetailsOnView(NewsDetails newsDetails) {
        NewsDetailsModel model = dataMapper.transform(newsDetails);
        if(getView() != null) getView().showNewsDetails(model);
    }

    @Override
    public void loadNewsDetails() {
        newsDetailsUseCase.execute(new NewDetailsSubscriber());
    }

    private final class NewDetailsSubscriber extends Subscriber<NewsDetails> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(NewsDetails newsDetails) {
            showDetailsOnView(newsDetails);
        }
    }
}
