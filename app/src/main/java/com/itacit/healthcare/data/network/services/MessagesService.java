package com.itacit.healthcare.data.network.services;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.entries.RecipientInfo;
import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.data.network.request.CreateMessageRequest;
import com.itacit.healthcare.data.network.request.CreateReplyRequest;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.request.RecipientsInfoRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.response.RecipientsSummaryResponse;

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
        Observable<ListResponse<Message>> getMessages(@Body ListRequest requestBody);

        @POST("/mobile/1.0/messaging/message/new")
        Observable<Integer> createMessage(@Body CreateMessageRequest requestBody);

        @POST("/mobile/1.0/messaging/message/sendReply ")
        Observable<Reply> createReply(@Body CreateReplyRequest requestBody);

        @POST("/mobile/1.0/messaging/reply")
        Observable<ListResponse<Reply>> getListReplies(@Body ListRequest requestBody);

        @PUT("/mobile/1.0/messaging/archive/{MessageId}")
        Observable<Void> archiveMessage(@Path("MessageId") String messageId);

        @PUT("/mobile/1.0/messaging/readConfirm/{MessageId}")
        Observable<Void> confirmMessageRead(@Path("MessageId") String messageId);

        @POST("/mobile/1.0/messaging/message")
        Observable<Message> getMessageDetailsReplies(@Body ItemRequest requestBody);

        @POST("/mobile/1.0/employee/advanced/summary")
        Observable<RecipientsSummaryResponse> getRecipientsSummary(@Body RecipientsInfoRequest requestBody);

        @POST("/mobile/1.0/employee/advanced/fullList")
        Observable<ListResponse<RecipientInfo>> getRecipientsFullList(@Body RecipientsInfoRequest requestBody);

    }
}
