package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Index;
import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 12.11.15.
 */
public class GetMessagesUseCase extends GetListUseCase<Message, Index> {

    public static final String FILTER_GROUP = "filterGroup:";
    public static final String SORT_FIELD_MESSAGES = "sendDateTime";

    @Override
    protected Observable<ListResponse<Message>> request(ListRequest requestBody) {
        return MessagesService.getApi().getMessages(requestBody);
    }

    @Override
    protected ListRequest initArgs(Index index) {
        ListRequest requestBody = new ListRequest();
        requestBody.setStartIndex(index.getStartIndex());
        requestBody.setRowCount(index.getRowCount());
        requestBody.setQuery(FILTER_GROUP + index.getFilter());
        requestBody.setSort(SORT_FIELD_MESSAGES);
        return requestBody;
    }
}
