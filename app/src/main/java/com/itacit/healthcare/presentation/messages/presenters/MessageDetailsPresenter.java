package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.domain.interactor.messages.ConfirmMessageReadUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessageDetailsUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetRepliesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.mappers.ReplyMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.views.MessageDetailsView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Den on 17.11.15.
 */
public class MessageDetailsPresenter extends BasePresenter<MessageDetailsView> {
    public List<RepliesModel> repliesModels;
    public MessageModel messageModel;

    private GetRepliesUseCase getRepliesUseCase;
    private GetMessageDetailsUseCase getMessageDetailsUseCase;

    private ReplyMapper repliesMapper;
    private MessagesMapper messagesMapper;

    private ConfirmMessageReadUseCase confirmMessageReadUseCase;

    private String messageId;

    public MessageDetailsPresenter(ReplyMapper replyMapper,
                                   GetRepliesUseCase getRepliesUseCase,
                                   MessagesMapper messagesMapper,
                                   GetMessageDetailsUseCase getMessageDetailsUseCase,
                                   ConfirmMessageReadUseCase confirmMessageReadUseCase,
                                   String messageId) {
        this.repliesMapper = replyMapper;
        this.getRepliesUseCase = getRepliesUseCase;
        this.messagesMapper = messagesMapper;
        this.getMessageDetailsUseCase = getMessageDetailsUseCase;
        this.confirmMessageReadUseCase = confirmMessageReadUseCase;
        this.messageId = messageId;
    }

    @Override
    protected void onAttachedView(@NonNull MessageDetailsView view) {
        view.showProgress();
        getMessageDetailsUseCase.execute(new HeaderRepliesSubscriber(),messageId);
        getRepliesUseCase.execute(new RepliesListSubscriber(), messageId);
    }

    public  void sendResponseConfirm(){
        confirmMessageReadUseCase.execute(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                actOnView(view -> view.showConfirmed());
            }

            @Override
            public void onError(Throwable e) {
                actOnView(view -> view.showError(e.toString()));
            }

            @Override
            public void onNext(Void t) {
            }
        }, messageId);
    }

    private void showRepliesOnView() {
        actOnView(v -> v.showListReplies(repliesModels));
    }

    private void showHeaderRepliesOnView() {
        actOnView(v -> {
            v.showHeaderReplies(messageModel);
            v.hideProgress();
        });
    }

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
