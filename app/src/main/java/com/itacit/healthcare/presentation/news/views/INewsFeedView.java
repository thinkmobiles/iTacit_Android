package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.IView;
import com.itacit.healthcare.presentation.news.models.NewsModel;

import java.util.List;

import rx.Observable;

/**
 * Created by root on 13.10.15.
 */
public interface INewsFeedView extends IView {
    public void showNews(List<NewsModel> news);
    public void newsItemSelected(NewsModel newsItem);
    public Observable<String> getNewsSearchTextObs();
}
