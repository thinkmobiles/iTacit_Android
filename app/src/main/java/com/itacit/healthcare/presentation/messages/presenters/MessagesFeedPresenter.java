package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.itacit.healthcare.presentation.messages.views.MessagesFeedView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedPresenter extends BasePresenter<MessagesFeedView> {

    public List<MessagesModel> messagesModels;

    private GetMessagesUseCase getMessagesUseCase;
    private MessagesMapper dataMapper;

    public MessagesFeedPresenter(GetMessagesUseCase messagesUseCase, MessagesMapper messagesMapper) {
        getMessagesUseCase = messagesUseCase;
        dataMapper = messagesMapper;
    }

    @Override
    protected void onAttachedView(@NonNull MessagesFeedView view) {
        getMessagesUseCase.execute(new MessagesListSubscriber());
    }

    private void showMessagesOnView(List<Message> messages) {
        messagesModels = dataMapper.transform(messages);
        actOnView(v -> v.showMessages(messagesModels));
    }

    public void getMessages(MessagesFilter filter) {
        actOnView(MessagesFeedView::showProgress);
        getMessagesUseCase.execute(new MessagesListSubscriber(), filter.toString());
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
