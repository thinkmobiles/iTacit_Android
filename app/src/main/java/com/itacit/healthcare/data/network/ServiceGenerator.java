package com.itacit.healthcare.data.network;

import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.data.network.interceptors.Logger;
import com.itacit.healthcare.data.entries.AccessToken;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by root on 28.10.15.
 */
public class ServiceGenerator {
    public static String BASE_URL = "https://mobilesandbox.itacit.com";
    private static OkHttpClient client = new OkHttpClient();

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    private static Retrofit retrofit = builder.client(client).build();
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, AccessToken accessToken) {
       client.interceptors().clear();
        if (accessToken != null) {
            client.interceptors().add(new AuthInterceptor(accessToken));
        }

        client.interceptors().add(new Logger());

        return retrofit.create(serviceClass);
    }
}
