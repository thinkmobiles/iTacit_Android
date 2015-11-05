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
        NewsDetailsModel newsDetailsModel = new NewsDetailsModel();
        try {
            long id = dataEntry.getArticleId() != null ? Long.parseLong(dataEntry.getArticleId()) : 0;
            if (id == 0) return null;
            newsDetailsModel.setArticleId(id);

            newsDetailsModel.setHeadline(dataEntry.getHeadline() != null ? dataEntry.getHeadline() : "");
            newsDetailsModel.setBody(dataEntry.getBody() != null ? dataEntry.getBody() : "");
            newsDetailsModel.setCategoryName(dataEntry.getCategoryName() != null ? dataEntry.getCategoryName() : "");
            newsDetailsModel.setHeadlineUri(Uri.parse(dataEntry.getHeadlineImageUrl() != null ? dataEntry.getHeadlineImageUrl() : ""));
            newsDetailsModel.setStartDate(dataEntry.getStartDate() != null ? dataEntry.getStartDate() : "");
            newsDetailsModel.setAuthorName(dataEntry.getAuthorName() != null ? dataEntry.getAuthorName() : "");
            newsDetailsModel.setAuthorId(dataEntry.getAuthorId() != null ? dataEntry.getAuthorId() : "");
            return newsDetailsModel;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
