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
        getMessageDetailsUseCase.execute(this::showHeaderRepliesOnView, errorHandler, messageId);
        getRepliesUseCase.execute(this::showRepliesOnView, errorHandler, messageId);
    }

    public void sendResponseConfirm() {
        confirmMessageReadUseCase.execute(o -> actOnView(MessageDetailsView::showConfirmed), errorHandler, messageId);
    }

    private void showRepliesOnView(List<Reply> replies) {
        repliesModels = repliesMapper.transform(replies);
        actOnView(v -> v.showListReplies(repliesModels));
    }

    private void showHeaderRepliesOnView(Message message) {
        messageModel = messagesMapper.transform(message);
        actOnView(v -> {
            v.showHeaderReplies(messageModel);
            v.hideProgress();
        });
    }
}
