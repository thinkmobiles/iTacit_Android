package com.itacit.healthcare.presentation.news.presenters;

/**
 * Created by root on 14.10.15.
 */
public interface INewsFeedPresenter {
    void loadNews();
    void searchNews(String searchWord);

}
