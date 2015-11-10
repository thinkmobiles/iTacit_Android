package com.itacit.healthcare.data.network.interceptors;

import com.itacit.healthcare.data.network.services.AuthService;
import com.itacit.healthcare.global.bus.RxBus;
import com.itacit.healthcare.global.errors.AuthError;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by root on 28.10.15.
 */
public class AuthInterceptor implements Interceptor {
    private static final String AUTH_HEADER = "Authorization";
    public static final int AUTH_ERROR_CODE = 401;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if (AuthService.getAccessToken() == null) {
            Response resp = chain.proceed(original);
            RxBus.getInstance().send(new AuthError(resp.message()));
            return resp;
        }

        final Request request = addTokenHeader(original);
        Response response = chain.proceed(request);

        if(response.code() == AUTH_ERROR_CODE) {
            AuthService.refreshToken();
            Request newRequest = addTokenHeader(original);
            response = chain.proceed(newRequest);
        }

        return response;
    }

    private Request addTokenHeader(Request original) {
        Request.Builder builder = original.newBuilder();

        return builder
                .header(AUTH_HEADER, AuthService.getAccessToken().getTokenType() + " " + AuthService.getAccessToken().getAccessToken())
                .method(original.method(), original.body())
                .build();
    }
}
