package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.news.GetNewsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mappers.NewsMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.news.views.NewsFeedView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenter extends BasePresenter<NewsFeedView> {
    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    private GetNewsUseCase getNewsUseCase;
    private NewsMapper newsMapper;
    public List<NewsModel> newsModels;

    public NewsFeedPresenter(GetNewsUseCase newsUseCase, NewsMapper newsMapper) {
        getNewsUseCase = newsUseCase;
        this.newsMapper = newsMapper;
    }

    @Override
    protected void onViewAttach() {
        if (getView()!= null) {
            compositeSubscription.add(getView().getNewsSearchTextObs()
                    .filter(text -> text.length() >= SEARCH_TEXT_MIN_LENGTH)
                    .debounce(1, TimeUnit.SECONDS)
                    .observeOn(getView().getUiThreadScheduler())
                    .subscribe(this::searchNews));

            compositeSubscription.add(getView().getNewsSearch().subscribe(this::searchNews));
        }
    }

    private void searchNews(NewsSearch search) {
        if (search.getFilters() != null) {
            if (getView() != null) getView().showFilters(search.getFilters());
        }
        getNewsUseCase.execute(new NewsListSubscriber(), search);
    }

    private void showNewsOnView() {
        if(getView() != null) getView().showNews(newsModels);
    }

    public void loadNews() {
        if (getView() != null) getView().showProgress();
        getNewsUseCase.execute(new NewsListSubscriber());
    }

    public void searchNews(String query) {
        getNewsUseCase.execute(new NewsListSubscriber(), query);
    }

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {
            if(getView() != null) getView().hideProgress();
	        showNewsOnView();
        }

        @Override
        public void onError(Throwable e) {
            if(getView() != null) getView().hideProgress();
        }

        @Override
        public void onNext(List<News> newses) {
	        newsModels = newsMapper.transform(newses);
        }
    }
}
