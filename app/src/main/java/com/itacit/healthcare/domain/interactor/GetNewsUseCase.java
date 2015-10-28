package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.network.response.News;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.request.RequestNews;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 21.10.15.
 */
public class GetNewsUseCase extends UseCase<List<News>> {

    @Override
    protected Observable buildUseCaseObservable() {
        RequestNews request = new RequestNews();
        request.setStartIndex(1);
        request.setRowCount(20);
        return ServiceGenerator.createService(NewsApi.class).getNews(request);
    }
}
