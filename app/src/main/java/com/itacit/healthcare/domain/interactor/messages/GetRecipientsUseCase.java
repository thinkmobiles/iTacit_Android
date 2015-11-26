package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Recipient;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 26.11.15.
 */
public class GetRecipientsUseCase extends GetListUseCase<Recipient, String> {

    private final String MESSAGE_ID = "messageId:";

    @Override
    protected Observable<ListResponse<Recipient>> request(ListRequest requestBody) {
        return MessagesService.getApi().getMessageRecipients(requestBody);
    }

    @Override
    protected ListRequest initArgs(String messageId) {
        ListRequest requestBody = new ListRequest();
        requestBody.setQuery(MESSAGE_ID+messageId);
        return requestBody;
    }
}
