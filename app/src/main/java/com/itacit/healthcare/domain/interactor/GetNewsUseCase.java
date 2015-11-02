package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.AuthManager;
import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.response.ListResponse;

import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public class GetNewsUseCase extends GetListUseCase<News> {

    public GetNewsUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    protected Observable<ListResponse<News>> request() {
        return ServiceGenerator.createService(NewsApi.class, AuthManager.accessToken)
                .getNews(listRequest);
    }

}