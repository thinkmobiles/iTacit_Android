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

    public GetMessagesUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    protected Observable<ListResponse<Message>> request() {
        return MessagesService.getApi().getMessages(requestBody);
    }

    public void execute(Subscriber<List<Message>> useCaseSubscriber, Filter filter) {
        super.execute(useCaseSubscriber, filter.toString());
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