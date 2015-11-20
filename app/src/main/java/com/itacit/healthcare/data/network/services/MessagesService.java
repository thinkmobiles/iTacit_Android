package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.data.network.AccessTokenHandler;
import com.itacit.healthcare.data.network.request.CreateMessageRequest;
import com.itacit.healthcare.data.network.request.CreateReplyRequest;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by root on 12.11.15.
 */
public class MessagesService {
    private static MessagesApi api;

    public static MessagesApi getApi() {
        if (api == null) {
            api = ServiceGenerator.createService(MessagesApi.class, true);
        }

        return api;
    }

    public interface MessagesApi {
        @POST("/mobile/1.0/messaging/message")
        Observable<ListResponse<Message>> getMessages(@Body ListRequest request);

        @POST("/mobile/1.0/messaging/message/new")
        Observable<Integer> createMessage(@Body CreateMessageRequest request);

        @POST("/mobile/1.0/messaging/message/sendReply ")
        Observable<Reply> createReply(@Body CreateReplyRequest request);

        @POST("/mobile/1.0/messaging/reply")
        Observable<ListResponse<Reply>> getListReplies(@Body ListRequest request);

        @PUT("/mobile/1.0/messaging/archive/{MessageId}")
        Observable<Void> archiveMessage(@Path("MessageId") String messageId);

        @PUT("/mobile/1.0/message/readConfirm/{MessageId}")
        Observable<Void> confirmMessageRead(@Path("MessageId") String messageId);

//        @POST("/mobile/1.0/messaging/message")
//        Observable<Object> getItemReplies(@Body ItemRequest request);
    }
}
