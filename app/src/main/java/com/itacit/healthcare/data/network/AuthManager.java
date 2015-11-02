package com.itacit.healthcare.data.network;

import com.itacit.healthcare.data.network.api.AuthApi;
import com.itacit.healthcare.data.entries.AccessToken;

import java.io.IOException;

import retrofit.Call;
import rx.Observable;


/**
 * Created by root on 28.10.15.
 */
public abstract class AuthManager {
    private static final String CLIENT_ID = "MOBILESANDBOX";
    private static final String GRANT_TYPE_PASS = "password";
    private static final String GRANT_TYPE_TOKEN = "refresh_token";

    public static AccessToken accessToken;
    private static AuthApi authApi;
    private static AuthApi getService() {
        if (authApi == null) authApi = ServiceGenerator.createService(AuthApi.class);
        return authApi;
    }

    public static Observable<Boolean> login(String userName, String password) {
         return getService().login(CLIENT_ID, userName, password, GRANT_TYPE_PASS)
                .flatMap(t -> {
                    accessToken = t;
                    return Observable.just(true);
                });
    }

    public static AccessToken refreshToken() {
        Call<AccessToken> tokenCall = getService().refreshToken(CLIENT_ID,
                GRANT_TYPE_TOKEN, accessToken.getRefreshToken());
        try {
            accessToken = tokenCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

}
