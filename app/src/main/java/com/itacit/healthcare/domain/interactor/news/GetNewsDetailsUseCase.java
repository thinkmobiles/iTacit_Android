package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by root on 29.10.15.
 */
public class GetNewsDetailsUseCase extends GetItemUseCase<NewsDetails> {
    private static final String BODY_FIELD = "body";

    public GetNewsDetailsUseCase(int id) {
        super(id);
        setRequestFields(DEFAULT_FIELDS, BODY_FIELD);
    }

    @Override
    protected Observable<NewsDetails> buildUseCaseObservable() {
        return NewsService.getApi()
                .getNewsDetails(requestBody)
                .filter(r -> r != null);
    }
}
