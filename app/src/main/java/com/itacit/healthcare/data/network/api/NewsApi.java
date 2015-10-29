package com.itacit.healthcare.data.network.api;

import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.data.network.request.RequestNews;
import com.itacit.healthcare.data.network.response.NewsListResponse;


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
    Observable<NewsListResponse> getNews(@Body RequestNews request);

    @GET("/mobile/1.0/news/article/{articleId}")
    Observable<NewsDetails> getNewsDetails(@Path("articleId") String articleId);
}
