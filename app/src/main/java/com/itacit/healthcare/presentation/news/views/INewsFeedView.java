package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.presentation.base.views.IView;
import com.itacit.healthcare.presentation.news.models.NewsModel;

import java.util.List;

import rx.Observable;

/**
 * Created by root on 13.10.15.
 */
public interface INewsFeedView extends IView {
    void showNews(List<NewsModel> news);
    void showProgress();
    void hideProgress();
    void showNewsItemDetails(long newsId);
    void showSearchResults(List<NewsModel> newsModels);
    Observable<String> getNewsSearchTextObs();
}
