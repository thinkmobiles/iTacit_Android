package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.GetNewsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenter extends BasePresenter<INewsFeedView> implements INewsFeedPresenter {
    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    private GetNewsUseCase getNewsUseCase;
    private NewsModelMapper dataMapper;

    public NewsFeedPresenter(GetNewsUseCase newsUseCase, NewsModelMapper newsModelDataMapper) {
        getNewsUseCase = newsUseCase;
        dataMapper = newsModelDataMapper;
    }

    @Override
    protected void onViewAttach() {
        if (getView()!= null) {
            compositeSubscription.add(getView().getNewsSearchTextObs()
                    .filter(text -> text.length() > SEARCH_TEXT_MIN_LENGTH)
                    .debounce(1, TimeUnit.SECONDS)
                    .subscribe(this::searchNews));
        }
    }

    private void showNewsOnView(List<News> news) {
        List<NewsModel> newsModels = dataMapper.transform(news);
        if(getView() != null) getView().showNews(newsModels);
    }

    @Override
    public void loadNews() {
        if(getView() != null) getView().showProgress();
        getNewsUseCase.execute(new NewsListSubscriber());
    }

    @Override
    public void searchNews(String searchWord) {

    }

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {
            if(getView() != null) getView().hideProgress();
        }

        @Override
        public void onError(Throwable e) {
            if(getView() != null) getView().hideProgress();
        }

        @Override
        public void onNext(List<News> newses) {
            showNewsOnView(newses);
        }
    }
}
