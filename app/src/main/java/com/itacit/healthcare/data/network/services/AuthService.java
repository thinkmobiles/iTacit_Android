package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.AccessToken;
import com.itacit.healthcare.data.network.AccessTokenHandler;

import java.io.IOException;

import retrofit.Call;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by root on 28.10.15.
 */
public abstract class AuthService {
    private static final String CLIENT_ID = "MOBILESANDBOX";
    private static final String GRANT_TYPE_PASS = "password";
    private static final String GRANT_TYPE_TOKEN = "refresh_token";

    private static AuthApi authApi;

    private static AuthApi getApi() {
        if (authApi == null) authApi = ServiceGenerator.createService(AuthApi.class);
        return authApi;
    }

    public static Observable<Boolean> login(String userName, String password) {
         return getApi().login(CLIENT_ID, userName, password, GRANT_TYPE_PASS)
                .flatMap(t -> {
                    AccessTokenHandler.setAccessToken(t);
                    return Observable.just(true);
                });
    }

    public static AccessToken refreshToken() {
        Call<AccessToken> tokenCall = getApi().refreshToken(CLIENT_ID,
                GRANT_TYPE_TOKEN, AccessTokenHandler.getAccessToken().getRefreshToken());
        try {
            AccessTokenHandler.setAccessToken(tokenCall.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AccessTokenHandler.getAccessToken();
    }

    public interface AuthApi {
        @Headers("Content-Type: application/json")
        @POST("/mobile/oauth/token")
        Observable<AccessToken> login(@Query("client_id") String clientId, @Query("username") String userName,
                                      @Query("password") String password, @Query("grant_type") String grantType);

        @Headers("Content-Type: application/json")
        @POST("/mobile/oauth/token")
        Call<AccessToken> refreshToken(@Query("client_id") String clientId,  @Query("grant_type") String grantType,
                                       @Query("refresh_token") String refreshToken);
    }
}
