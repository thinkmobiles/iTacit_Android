package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by Den on 19.11.15.
 */
public class GetHeaderUseCase extends GetItemUseCase<Message> {

    private static final String SENDER_FIELD = "sender";
    public static final String RECIPIENTS_MESSAGE = "recipients";


    public GetHeaderUseCase(String id) {
        super(id);
        setRequestFields(DEFAULT_FIELDS, SENDER_FIELD,RECIPIENTS_MESSAGE);
    }

    @Override
    protected Observable<Message> buildUseCaseObservable() {
        return MessagesService.getApi().getHeaderReplies(requestBody);
    }
}
