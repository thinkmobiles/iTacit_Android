package com.itacit.healthcare.data.network.api;

import com.itacit.healthcare.data.network.response.News;
import com.itacit.healthcare.data.network.request.RequestNews;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public interface NewsApi {
    @POST("/1.0/news/article")
    Observable<List<News>> getNews(@Body RequestNews request);
}
