package com.itacit.healthcare.domain.api;

import com.itacit.healthcare.data.News;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public interface NewsApi {
    public static final String BASE_URL = "https://app.itacit.com/mobile/";

    @POST("/1.0/news/article")
    public Observable<List<News>> getNews(@Body RequestNews request);
}
