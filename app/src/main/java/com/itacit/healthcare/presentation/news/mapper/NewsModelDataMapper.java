package com.itacit.healthcare.presentation.news.mapper;

import android.net.Uri;

import com.itacit.healthcare.data.network.response.News;
import com.itacit.healthcare.presentation.base.ModelDataMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;

/**
 * Created by root on 23.10.15.
 */
public class NewsModelDataMapper extends ModelDataMapper<NewsModel, News>{
    @Override
    public NewsModel transform(News dataEntry) {
        NewsModel newsModel = new NewsModel();
        newsModel.setHeadline(dataEntry.getHeadline());
        newsModel.setCategoryName(dataEntry.getCategoryName());
        newsModel.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl()));
        newsModel.setStartDate(dataEntry.getStartDate());
        return newsModel;
    }
}
