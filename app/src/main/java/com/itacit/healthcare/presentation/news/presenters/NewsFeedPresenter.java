package com.itacit.healthcare.presentation.news.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.news.GetNewsUseCase;
import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mappers.NewsMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.views.NewsFeedView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenter extends BasePresenter<NewsFeedView> {
    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    public static final int REQUEST_DELAY = 1;
    private GetNewsUseCase getNewsUseCase;
    private NewsMapper newsMapper;
    private NewsSearch lastSearch = new NewsSearch();
    public List<NewsModel> newsModels;

    public NewsFeedPresenter(GetNewsUseCase newsUseCase, NewsMapper newsMapper) {
        getNewsUseCase = newsUseCase;
        this.newsMapper = newsMapper;
    }

    public void clearNewsSearch() {
        lastSearch = new NewsSearch();
            actOnView(view -> {
            view.hideFilters();
            view.getNewsSearch().onNext(lastSearch);
        });
    }

    @Override
    protected void onAttachedView(@NonNull NewsFeedView view) {
        compositeSubscription.add(view.getNewsSearchTextObs()
                .filter(text -> text.length() >= SEARCH_TEXT_MIN_LENGTH)
                .debounce(REQUEST_DELAY, TimeUnit.SECONDS)
                .observeOn(view.getUiThreadScheduler())
                .subscribe(this::searchNews));

        compositeSubscription.add(view.getNewsSearch().subscribe(this::searchNews));
    }

    private void searchNews(NewsSearch search) {
        if (search.getChips() != null && !search.getChips().isEmpty()) {
            actOnView(view -> view.showFilters(search.getChips()));
        }

        actOnView(NewsFeedView::showProgress);
        lastSearch = search;
        getNewsUseCase.execute(new NewsListSubscriber(), search);
    }

    private void showNewsOnView(List<News> newses) {
        newsModels = newsMapper.transform(newses);
        actOnView(view -> view.showNews(newsModels));
    }

    public void searchNews(String query) {
        actOnView(NewsFeedView::showProgress);
        lastSearch = new NewsSearch();
        lastSearch.setSearch(query);
        getNewsUseCase.execute(new NewsListSubscriber(), lastSearch);
    }

    public void refreshNews() {
            getNewsUseCase.execute(new NewsListSubscriber(), lastSearch);
    }

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {
            actOnView(NewsFeedView::hideProgress);
        }
        @Override
        public void onError(Throwable e) {
            actOnView(NewsFeedView::hideProgress);
        }

        @Override
        public void onNext(List<News> newses) {
            showNewsOnView(newses);
        }
    }
}
