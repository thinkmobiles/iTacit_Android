package com.itacit.healthcare.presentation.messages.presenters;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.domain.interactor.messages.ArchiveMessageUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.views.MessagesFeedView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedPresenter extends BasePresenter<MessagesFeedView> {

    public List<MessageModel> messageModels;

    private GetMessagesUseCase getMessagesUseCase;
    private ArchiveMessageUseCase archiveMessageUseCase;
    private MessagesMapper dataMapper;

    public MessagesFeedPresenter(GetMessagesUseCase messagesUseCase, MessagesMapper messagesMapper, ArchiveMessageUseCase archiveMessageUseCase) {
        getMessagesUseCase = messagesUseCase;
        dataMapper = messagesMapper;
        this.archiveMessageUseCase = archiveMessageUseCase;
    }

    private void showMessagesOnView(List<Message> messages) {
        messageModels = dataMapper.transform(messages);
        actOnView(v -> v.showMessages(messageModels));
    }

    public void getMessages(MessagesFilter filter) {
        actOnView(MessagesFeedView::showProgress);
        getMessagesUseCase.execute(new MessagesListSubscriber(), filter.toString());
    }

    public void onMessageSelected(String messageId) {
        actOnView(view -> view.showMessageDetails(messageId));
    }
    
    public void onMessageArchiveSeleced(String messageId) {
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
        DONE("DONE"),
        INBOX("INBOX"),
        SENT("SENT"),
        ARCHIVE("ARCHIVE");

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
