package com.itacit.healthcare.presentation.news.mapper;

import com.itacit.healthcare.data.News;
import com.itacit.healthcare.presentation.base.ModelDataMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;

/**
 * Created by root on 23.10.15.
 */
public class NewsModelDataMapper extends ModelDataMapper<NewsModel, News>{
    @Override
    public NewsModel transform(News dataEntry) {
        return null;
    }
}
