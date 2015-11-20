package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by Den on 16.11.15.
 */
public class GetListRepliesUseCase extends GetListUseCase<Reply, String> {

    private static final String RECIPIENTS_FIELD = "recipients";
    private static final String SENDER = "sender";
    private static final String SUBJECT = "subject";

    private static final String PARENT_MESSAGE_ID = "parentMessageId:";
    public static final String SORT_FIELD_REPLIES = "sendDateTime";

    @Override
    protected ListRequest initArgs(String messageId) {
        ListRequest requestBody = new ListRequest();
        requestBody.setQuery(PARENT_MESSAGE_ID + messageId);
        return requestBody;
    }

    @Override
    protected Observable<ListResponse<Reply>> request(ListRequest requestBody) {
        return MessagesService.getApi().getListReplies(requestBody);
    }
}
