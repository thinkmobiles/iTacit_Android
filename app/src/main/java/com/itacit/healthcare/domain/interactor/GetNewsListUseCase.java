package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.AuthManager;
import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.request.RequestNews;

import java.util.List;

import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public class GetNewsListUseCase extends UseCase<List<News>> {

    @Override
    protected Observable buildUseCaseObservable() {
        RequestNews request = new RequestNews();
        request.setStartIndex(1);
        request.setRowCount(100);
        return ServiceGenerator.createService(NewsApi.class, AuthManager.accessToken)
                .getNews(request)
                .filter(r -> r.getResponseRows() != null)
                .map(r -> r.getResponseRows());
    }
}
