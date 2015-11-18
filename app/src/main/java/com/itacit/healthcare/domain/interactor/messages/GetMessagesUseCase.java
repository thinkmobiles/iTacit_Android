package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by root on 12.11.15.
 */
public class GetMessagesUseCase extends GetListUseCase<Message> {

    public static final String FILTER_GROUP = "filterGroup:";
    public static final String SORT_FIELD_MESSAGES = "sendDateTime";
    public static final String SENDER_MESSAGE = "sender";
    public static final String RECIPIENTS_MESSAGE = "recipients";

    public GetMessagesUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
//        setRequestFields(DEFAULT_FIELDS, SENDER_MESSAGE, RECIPIENTS_MESSAGE);
        setSortField(SORT_FIELD_MESSAGES);
    }

    @Override
    protected Observable<ListResponse<Message>> request() {
        return MessagesService.getApi().getMessages(requestBody);
    }

    public void execute(Subscriber<List<Message>> useCaseSubscriber, String filter) {
        if (filter == null ){
            super.execute(useCaseSubscriber);
        } else {
            filter = FILTER_GROUP + filter;
            super.execute(useCaseSubscriber, filter);
        }
    }

    public enum  Filter {
        ALL("ALL"),
        ACT("ACT"),
        WAITING("WAITING"),
        DONE("DONE"),
        INBOX("INBOX"),
        SENT("SENT"),
        ARCHIVE("ARCHIVE");

        private final String filter;
        Filter(String filter) {
            this.filter = filter;
        }

        @Override
        public String toString() {
            return filter;
        }
    }
}
