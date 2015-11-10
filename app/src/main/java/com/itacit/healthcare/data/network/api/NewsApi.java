package com.itacit.healthcare.data.network.api;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;


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
    Observable<ListResponse<News>> getNews(@Body ListRequest request);

    @GET("/mobile/1.0/news/article/{id}")
    Observable<NewsDetails> getNewsDetails(@Path("id") String id);

    @POST("/mobile/1.0/news/author")
    Observable<ListResponse<Author>> getAuthors(@Body ListRequest request);

    @POST("/mobile/1.0/news/category")
    Observable<ListResponse<Category>> getCategories(@Body ListRequest request);
}
