package com.itacit.healthcare.data.network;

import com.itacit.healthcare.data.network.api.AuthApi;
import com.itacit.healthcare.data.entries.AccessToken;

import rx.Observable;


/**
 * Created by root on 28.10.15.
 */
public class AuthManager {
    private final String CLIENT_ID = "MOBILESANDBOX";
    private final String GRANT_TYPE = "password";

    public static AccessToken accessToken;

    public Observable login(String userName, String password) {
         return ServiceGenerator.createService(AuthApi.class).login(CLIENT_ID, userName, password, GRANT_TYPE)
                .flatMap(t -> {
                    accessToken = t;
                    return Observable.just(t);
                });
    }

}
