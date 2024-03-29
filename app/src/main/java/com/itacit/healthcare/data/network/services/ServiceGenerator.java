package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.data.network.interceptors.Logger;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by root on 28.10.15.
 */
public class ServiceGenerator {
    public static String BASE_URL = "https://mobilesandbox.itacit.com";

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, false);
    }

    public static <S> S createService(Class<S> serviceClass, boolean requireAuth) {
        OkHttpClient client = new OkHttpClient();
        if (requireAuth){
            client.interceptors().add(new AuthInterceptor());
        }
        client.interceptors().add(new Logger());
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
