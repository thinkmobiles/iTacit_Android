package com.itacit.healthcare.presentation.news.mapper;

import android.net.Uri;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.presentation.base.ModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;

/**
 * Created by root on 23.10.15.
 */
public class NewsModelMapper extends ModelMapper<NewsModel, News> {
    @Override
    public NewsModel transform(News dataEntry) {
        NewsModel newsModel = new NewsModel();
        long id = dataEntry.getArticleId() != null ? Long.parseLong(dataEntry.getArticleId()) : 0;
        newsModel.setArticleId(id);
        newsModel.setHeadline(dataEntry.getHeadline());
        newsModel.setCategoryName(dataEntry.getCategoryName());
        newsModel.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl()));
        newsModel.setStartDate(dataEntry.getStartDate());
        return newsModel;
    }
}
