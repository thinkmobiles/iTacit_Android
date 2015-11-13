package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by root on 29.10.15.
 */
public class GetNewsDetailsUseCase extends GetItemUseCase<News> {
    private static final String BODY_FIELD = "body";
    private static final String AUTHOR_FIELD = "author";

    public GetNewsDetailsUseCase(String id) {
        super(id);
        setRequestFields(DEFAULT_FIELDS, BODY_FIELD, AUTHOR_FIELD);
    }

    @Override
    protected Observable<News> buildUseCaseObservable() {
        return NewsService.getApi()
                .getNewsDetails(requestBody)
                .filter(r -> r != null);
    }
}
