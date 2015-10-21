package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.domain.api.NewsApi;

import retrofit.Retrofit;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by root on 21.10.15.
 */
public abstract class BaseRestInteractor<T> implements Interactor<T> {
    protected Retrofit mRetrofit;
    protected Subscription mSubscription = Subscriptions.empty();

    public BaseRestInteractor() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NewsApi.BASE_URL)
                        .build();
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
