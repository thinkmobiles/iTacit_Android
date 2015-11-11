package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.data.network.services.NewsService;

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
                .getNewsDetails(request)
                .filter(r -> r != null);
    }
}
