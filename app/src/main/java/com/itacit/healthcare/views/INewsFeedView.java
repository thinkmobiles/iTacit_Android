package com.itacit.healthcare.views;

import com.itacit.healthcare.models.News;

import java.util.List;

/**
 * Created by root on 13.10.15.
 */
public interface INewsFeedView extends IView {
    public void showNews(List<News> news);
    public void newsItemSelected(News newsItem);
}
