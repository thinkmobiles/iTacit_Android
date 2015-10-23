package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.domain.api.Loger;
import com.itacit.healthcare.domain.api.NewsApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by root on 21.10.15.
 */
public abstract class BaseRestInteractor<T> implements Interactor<T> {
    protected Retrofit mRetrofit;
    protected Subscription mSubscription = Subscriptions.empty();

    public BaseRestInteractor() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Loger());

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                        .build();
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
