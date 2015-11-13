package com.itacit.healthcare.presentation.news.mappers;

import android.net.Uri;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;

/**
 * Created by root on 29.10.15.
 */
public class NewsDetailsMapper extends ModelMapper<NewsDetailsModel, News> {
    @Override
    public NewsDetailsModel transform(News dataEntry) {
        NewsDetailsModel model = new NewsDetailsModel();
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        model.setId(dataEntry.getId());
        model.setHeadline(dataEntry.getHeadline() != null ? dataEntry.getHeadline() : "");
        model.setBody(dataEntry.getBody() != null ? dataEntry.getBody() : "");
        model.setCategoryName(dataEntry.getCategoryName() != null ? dataEntry.getCategoryName() : "");
        model.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl() != null ? dataEntry.getHeadlineImageUrl() : ""));
        model.setStartDate(dataEntry.getStartDate() != null ? dataEntry.getStartDate() : "");

        if(dataEntry.getAuthor() == null) {
            return model;
        }

        UserMapper authorMapper = new UserMapper();
        UserModel authorModel = authorMapper.transform(dataEntry.getAuthor());
        model.setAuthor(authorModel);
        return model;
    }
}
