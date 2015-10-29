package com.itacit.healthcare.data.network.api;

import com.itacit.healthcare.data.entries.AccessToken;


import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by root on 28.10.15.
 */
public interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("/mobile/oauth/token")
    Observable<AccessToken> login(@Query("client_id") String clientId, @Query("username") String userName,
               @Query("password") String password, @Query("grant_type") String grantType);
}
