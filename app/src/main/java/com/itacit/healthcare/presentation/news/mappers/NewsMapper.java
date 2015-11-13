package com.itacit.healthcare.presentation.news.mappers;

import android.net.Uri;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;

/**
 * Created by root on 23.10.15.
 */
public class NewsMapper extends ModelMapper<NewsModel, News> {
    @Override
    public NewsModel transform(News dataEntry) {
        NewsModel newsModel = new NewsModel();
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }
        newsModel.setId(dataEntry.getId());

        newsModel.setHeadline(dataEntry.getHeadline() != null ? dataEntry.getHeadline() : "");
        newsModel.setCategoryName(dataEntry.getCategoryName() != null ? dataEntry.getCategoryName() : "");
        newsModel.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl() != null ? dataEntry.getHeadlineImageUrl() : ""));
        newsModel.setStartDate(dataEntry.getStartDate() != null ? dataEntry.getStartDate() : "");
        return newsModel;
    }
}
