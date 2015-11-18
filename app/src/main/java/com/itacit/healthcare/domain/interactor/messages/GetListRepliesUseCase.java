package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Den on 16.11.15.
 */
public class GetListRepliesUseCase extends GetListUseCase<Reply> {

    private static final String RECIPIENTS_FIELD = "recipients";
    private static final String SENDER = "sender";
    private static final String SUBJECT = "subject";

    private static final String PARENT_MESSAGE_ID = "parentMessageId:";
    public static final String SORT_FIELD_REPLIES = "sendDateTime";

    public GetListRepliesUseCase(Integer startIndex, Integer rowCounts) {
        super(startIndex, rowCounts);
//        setRequestFields(DEFAULT_FIELDS, SENDER, RECIPIENTS_FIELD,SUBJECT);
        setSortField(SORT_FIELD_REPLIES);
    }

    @Override
    protected Observable<ListResponse<Reply>> request() {
        return MessagesService.getApi().getListReplies(requestBody);
    }

    public void execute(Subscriber<List<Reply>> useCaseSubscriber, String idSender) {
        if (idSender == null ){
            super.execute(useCaseSubscriber);
        } else {
            idSender = PARENT_MESSAGE_ID + idSender;
            super.execute(useCaseSubscriber, idSender);
        }
    }
}
