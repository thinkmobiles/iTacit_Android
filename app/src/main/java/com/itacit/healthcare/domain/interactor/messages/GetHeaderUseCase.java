package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by Den on 19.11.15.
 */
public class GetHeaderUseCase extends GetItemUseCase<Message,String> {

    private static final String SENDER_FIELD = "sender";
    public static final String RECIPIENTS_MESSAGE = "recipients";

    @Override
    protected Observable<Message> buildUseCaseObservable(ItemRequest requestBody) {
        return MessagesService.getApi().getHeaderReplies(requestBody);
    }

    @Override
    protected ItemRequest initArgs(String args) {
        ItemRequest requestBody = new ItemRequest();
        requestBody.setId(args);
        setRequestFields(requestBody, DEFAULT_FIELDS, SENDER_FIELD, RECIPIENTS_MESSAGE);
        return requestBody;    }
}
