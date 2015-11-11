package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.data.network.services.NewsService;

import rx.Observable;

/**
 * Created by root on 29.10.15.
 */
public class GetNewsDetailsUseCase extends UseCase<NewsDetails> {
    private String arcticleId;

    public GetNewsDetailsUseCase(long arcticleId) {
        this.arcticleId = String.valueOf(arcticleId);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return NewsService.getApi()
                .getNewsDetails(arcticleId)
                .filter(r -> r != null);
    }
}
