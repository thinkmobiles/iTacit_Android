package com.itacit.healthcare.presentation.messages.presenters;

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
    protected void onViewAttach() {
        if (getView()!= null) {
            getMessagesUseCase.execute(new MessagesListSubscriber(), GetMessagesUseCase.Filter.ALL);
        }
    }

    private void showMessagesOnView() {
        if(getView() != null) getView().showMessages(messagesModels);
    }

    private final class MessagesListSubscriber extends Subscriber<List<Message>> {

        @Override
        public void onCompleted() {
            showMessagesOnView();
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Message> messages) {
            messagesModels = dataMapper.transform(messages);
        }
    }
}
