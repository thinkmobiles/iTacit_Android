package com.itacit.healthcare.presentation.news.mapper;

import android.net.Uri;

import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.presentation.base.ModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;

/**
 * Created by root on 29.10.15.
 */
public class NewsDetailsModelMapper extends ModelMapper<NewsDetailsModel, NewsDetails> {
    @Override
    public NewsDetailsModel transform(NewsDetails dataEntry) {
        NewsDetailsModel newsModel = new NewsDetailsModel();
        long id = dataEntry.getArticleId() != null ? Long.parseLong(dataEntry.getArticleId()) : 0;
        newsModel.setArticleId(id);
        newsModel.setHeadline(dataEntry.getHeadline());
        newsModel.setCategoryName(dataEntry.getCategoryName());
        newsModel.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl()));
        newsModel.setStartDate(dataEntry.getStartDate());
        return newsModel;
    }
}
