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
        try {
            long id = dataEntry.getId() != null ? Long.parseLong(dataEntry.getId()) : 0;
            if (id == 0) return null;
            newsModel.setId(id);

            newsModel.setHeadline(dataEntry.getHeadline() != null ? dataEntry.getHeadline() : "");
            newsModel.setCategoryName(dataEntry.getCategoryName() != null ? dataEntry.getCategoryName() : "");
            newsModel.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl() != null ? dataEntry.getHeadlineImageUrl() : ""));
            newsModel.setStartDate(dataEntry.getStartDate() != null ? dataEntry.getStartDate() : "");
            return newsModel;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
