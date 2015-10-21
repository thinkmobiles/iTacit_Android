package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.News;
import com.itacit.healthcare.domain.api.NewsApi;

import java.util.List;

import rx.Subscriber;

/**
 * Created by root on 21.10.15.
 */
public class GetNewsInteractor extends BaseRestInteractor<List<News>> {
    NewsApi mApi;

    public GetNewsInteractor() {
        super();
        mApi = mRetrofit.create(NewsApi.class);
    }

    @Override
    public void execute(Subscriber<List<News>> subscriber) {
        mApi.getNews().subscribe(subscriber);
    }
}
