package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.domain.interactor.messages.ConfirmMessageReadUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetHeaderUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetListRepliesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.ListRepliesMapper;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.views.MessageRepliesView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Den on 17.11.15.
 */
public class MessageRepliesPresenter extends BasePresenter<MessageRepliesView> {
    public List<RepliesModel> repliesModels;
    public MessageModel messageModel;

    private GetListRepliesUseCase getListRepliesUseCase;
    private GetHeaderUseCase getHeaderUseCase;

    private ListRepliesMapper repliesMapper;
    private MessagesMapper messagesMapper;

    private ConfirmMessageReadUseCase confirmMessageReadUseCase;

    private String messageId;

    public MessageRepliesPresenter(ListRepliesMapper listRepliesMapper,
                                   GetListRepliesUseCase getListRepliesUseCase,
                                   MessagesMapper messagesMapper,
                                   GetHeaderUseCase getHeaderUseCase,
                                   ConfirmMessageReadUseCase confirmMessageReadUseCase,
                                   String messageId) {
        this.repliesMapper = listRepliesMapper;
        this.getListRepliesUseCase = getListRepliesUseCase;
        this.messagesMapper = messagesMapper;
        this.getHeaderUseCase = getHeaderUseCase;
        this.confirmMessageReadUseCase = confirmMessageReadUseCase;
        this.messageId = messageId;
    }

    @Override
    protected void onAttachedView(@NonNull MessageRepliesView view) {
        getHeaderUseCase.execute(new HeaderRepliesSubscriber(),messageId);
        getListRepliesUseCase.execute(new RepliesListSubscriber(), messageId);
    }

    public  void sendResponseConfirm(){
        confirmMessageReadUseCase.execute(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                actOnView(view -> view.showConfirmed());
            }

            @Override
            public void onError(Throwable e) {
                actOnView(view -> view.showErrorToast(e.toString()));
            }

            @Override
            public void onNext(Void t) {
            }
        }, messageId);
    }

    private void showRepliesOnView() {
        actOnView(v -> v.showListReplies(repliesModels));
    }

    private void showHeaderRepliesOnView(){actOnView(v -> v.showHeaderReplies(messageModel));}

    private final class RepliesListSubscriber extends Subscriber<List<Reply>> {
        @Override
        public void onCompleted() {
            showRepliesOnView();
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Reply> replies) {
            repliesModels = repliesMapper.transform(replies);
        }
    }

    private final class HeaderRepliesSubscriber extends Subscriber<Message> {
        @Override
        public void onCompleted() {
            showHeaderRepliesOnView();
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Message message) {
            messageModel = messagesMapper.transform(message);
        }
    }

}
