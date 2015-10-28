package com.itacit.healthcare.data.network.interceptors;

import com.itacit.healthcare.data.network.response.AccessToken;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by root on 28.10.15.
 */
public class AuthInterceptor implements Interceptor {
    private final AccessToken accessToken;

    public AuthInterceptor(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("Accept", "applicaton/json")
                .header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
