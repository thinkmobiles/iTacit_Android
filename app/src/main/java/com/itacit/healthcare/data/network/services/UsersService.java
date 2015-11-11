package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.data.network.AccessTokenHandler;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public class UsersService {
    private static UsersApi api;

    public static UsersApi getApi() {
        if (api == null) {
            api = ServiceGenerator.createService(UsersApi.class, AccessTokenHandler.getAccessToken());
        }

        return api;
    }

    public interface UsersApi {
        @POST("/mobile/1.0/employee/profile")
        Observable<ListResponse<User>> getUsersList(@Body ListRequest listRequest);

        @POST("/mobile/1.0/employee/profile")
        Observable<User> getUser(@Body ItemRequest itemRequest);
    }
}
