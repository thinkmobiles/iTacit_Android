package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.network.AccessTokenHandler;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by root on 12.11.15.
 */
public class MessagesService {
    private static MessagesApi api;

    public static MessagesApi getApi() {
        if (api == null) {
            api = ServiceGenerator.createService(MessagesApi.class, AccessTokenHandler.getAccessToken());
        }

        return api;
    }

    public interface MessagesApi {
        @POST("/mobile/1.0/messaging/message")
        Observable<ListResponse<Message>> getMessages(@Body ListRequest request);
    }
}
