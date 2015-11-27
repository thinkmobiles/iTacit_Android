package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Chip;
import com.itacit.healthcare.presentation.news.models.NewsModel;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by root on 13.10.15.
 */
public interface NewsFeedView extends View {
    void showNews(List<NewsModel> news);
    void showFilters(List<Chip> chips);
    void hideFilters();
    void showProgress();
    void hideProgress();
    void showNewsItemDetails(String newsId);
    Observable<String> getNewsSearchTextObs();
    NewsStorage getNewsSearch();
}
