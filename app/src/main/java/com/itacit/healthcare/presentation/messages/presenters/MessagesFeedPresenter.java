package com.itacit.healthcare.presentation.messages.presenters;

import com.itacit.healthcare.data.entries.Index;
import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.entries.MessagesSummary;
import com.itacit.healthcare.domain.interactor.messages.ArchiveMessageUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesSummaryUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.mappers.MessagesSummaryMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.views.MessagesFeedView;

import java.util.List;
import java.util.Map;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedPresenter extends BasePresenter<MessagesFeedView> {

    private final GetMessagesSummaryUseCase getSummaryUseCase;
    private final MessagesSummaryMapper summaryMapper;
    public List<MessageModel> messageModels;

    private GetMessagesUseCase getMessagesUseCase;
    private ArchiveMessageUseCase archiveUseCase;
    private MessagesMapper dataMapper;

    public Index index = new Index();
    public static final int START_POSITION = 1;
    public static final int ROW_COUNT = 10;

    public MessagesFeedPresenter(GetMessagesUseCase messagesUseCase,
                                 MessagesMapper messagesMapper,
                                 ArchiveMessageUseCase archiveUseCase,
                                 GetMessagesSummaryUseCase getSummaryUseCase,
                                 MessagesSummaryMapper summaryMapper) {
        getMessagesUseCase = messagesUseCase;
        dataMapper = messagesMapper;
        this.archiveUseCase = archiveUseCase;
        this.getSummaryUseCase = getSummaryUseCase;
        this.summaryMapper = summaryMapper;
    }

    @Override
    protected void onAttachedView(@NonNull MessagesFeedView view) {
        getMessagesCounts();
    }

    private void getMessagesCounts() {
        getSummaryUseCase.execute(this::showSummaryOnView,
                errorHandler);
    }

    private void showSummaryOnView(MessagesSummary summary) {
        Map<MessagesFilter, String> summaryMap = summaryMapper.transform(summary);
        actOnView(view -> view.showMessagesSummary(summaryMap));
    }

    private void showMessagesOnView(List<Message> messages) {
        messageModels = dataMapper.transform(messages);
        actOnView(v -> v.showMessages(messageModels));
    }

    public void getMessagesWithFilter(MessagesFilter filter) {
        index.setStartIndex(START_POSITION);
        index.setRowCount(ROW_COUNT);
        index.setFilter(filter.toString());
        actOnView(MessagesFeedView::showProgress);
        getMessagesUseCase.execute(new MessagesListSubscriber(), index);
    }

    public void getMore(int newStartPosition){
        index.setStartIndex(newStartPosition);
        getMessagesUseCase.execute(new MessagesListSubscriber(), index);
        getMessagesUseCase.execute(messages -> {
                    showMessagesOnView(messages);
                    actOnView(MessagesFeedView::hideProgress);
        }, errorHandler, filter.toString());
    }

    public void onMessageSelected(String messageId) {
        actOnView(view -> view.showMessageDetails(messageId));
    }
    
    public void onMessageArchiveSelected(String messageId) {
        archiveUseCase.execute(o -> {
            actOnView(view -> view.removeMessage(messageId));
            getMessagesCounts();
        }, errorHandler, messageId);

    public void onMessageArchiveSelected(String messageId) {
        archiveMessageUseCase.execute(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                actOnView(view -> view.removeMessage(messageId));
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Void o) {}
        }, messageId);
    }

    private final class MessagesListSubscriber extends Subscriber<List<Message>> {
        @Override
        public void onCompleted() {
            actOnView(MessagesFeedView::hideProgress);
        }

        @Override
        public void onError(Throwable e) {
            actOnView(MessagesFeedView::hideProgress);
        }

        @Override
        public void onNext(List<Message> messages) {
            showMessagesOnView(messages);
        }
    }

    public enum MessagesFilter {
        ALL("ALL"),
        ACT("ACT"),
        WAITING("WAITING"),
        INBOX("INBOX"),
        SENT("SENT"),
        ARCHIVE("ARCHIVE"),
        DONE("DONE"),;

        private final String filter;
        MessagesFilter(String filter) {
            this.filter = filter;
        }

        @Override
        public String toString() {
            return filter;
        }
    }
}
