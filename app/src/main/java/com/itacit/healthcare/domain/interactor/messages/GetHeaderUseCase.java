package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by Den on 19.11.15.
 */
public class GetHeaderUseCase extends GetItemUseCase<Message,Void> {

    private static final String SENDER_FIELD = "sender";
    public static final String RECIPIENTS_MESSAGE = "recipients";

    private final String id;


    public GetHeaderUseCase(String id) {
        this.id = id;
    }

    @Override
    protected Observable<Message> buildUseCaseObservable(ItemRequest requestBody) {
        return MessagesService.getApi().getHeaderReplies(requestBody);
    }

    @Override
    protected ItemRequest initArgs(Void args) {
        ItemRequest requestBody = new ItemRequest();
        requestBody.setId(id);
        setRequestFields(requestBody, DEFAULT_FIELDS, SENDER_FIELD, RECIPIENTS_MESSAGE);
        return requestBody;    }
}
