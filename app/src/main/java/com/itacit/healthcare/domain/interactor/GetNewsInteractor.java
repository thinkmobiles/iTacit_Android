package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.News;
import com.itacit.healthcare.domain.api.NewsApi;
import com.itacit.healthcare.domain.api.RequestNews;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        RequestNews request = new RequestNews();
        request.setStartIndex(1);
        request.setRowCount(20);
        mApi.getNews(request).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(subscriber);
    }
}
