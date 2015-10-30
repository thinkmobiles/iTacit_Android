package com.itacit.healthcare.data.network.api;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.NewsListResponse;


import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public interface NewsApi {
    @POST("mobile/1.0/news/article")
    Observable<NewsListResponse> getNews(@Body ListRequest request);

    @GET("/mobile/1.0/news/article/{articleId}")
    Observable<NewsDetails> getNewsDetails(@Path("articleId") String articleId);

    @POST("/mobile/1.0/news/articleAuthor")
    Observable<List<Author>> getAuthors(@Body ListRequest request);

    @POST("/mobile/1.0/news/article/Category")
    Observable<List<Category>> getCategories(@Body ListRequest request);
}
