package com.itacit.healthcare.domain.api;

import com.itacit.healthcare.data.News;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public interface NewsApi {
    public static final String BASE_URL = "baseuri";

    @GET("getNewsUrl")
    Observable<List<News>> getNews();
}
