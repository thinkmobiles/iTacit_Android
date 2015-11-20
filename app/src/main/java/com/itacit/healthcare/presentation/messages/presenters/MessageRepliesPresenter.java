package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.domain.interactor.messages.GetHeaderUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetListRepliesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.ListRepliesMapper;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.views.MessageRepliesView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Den on 17.11.15.
 */
public class MessageRepliesPresenter extends BasePresenter<MessageRepliesView> {
    public List<RepliesModel> repliesModels;
    public MessagesModel messagesModel;

    private GetListRepliesUseCase getListRepliesUseCase;
    private GetHeaderUseCase getHeaderUseCase;

    private ListRepliesMapper repliesMapper;
    private MessagesMapper messagesMapper;

    private String messageId;

    public MessageRepliesPresenter(ListRepliesMapper listRepliesMapper,
                                   GetListRepliesUseCase getListRepliesUseCase,
                                   MessagesMapper messagesMapper,
                                   GetHeaderUseCase getHeaderUseCase,
                                   String messageId) {
        this.repliesMapper = listRepliesMapper;
        this.getListRepliesUseCase = getListRepliesUseCase;
        this.messagesMapper = messagesMapper;
        this.getHeaderUseCase = getHeaderUseCase;
        this.messageId = messageId;
    }

    @Override
    protected void onAttachedView(@NonNull MessageRepliesView view) {
        getHeaderUseCase.execute(new HeaderRepliesSubscriber());
        getListRepliesUseCase.execute(new RepliesListSubscriber(), messageId);
    }

    private void showRepliesOnView() {
        actOnView(v -> v.showListReplies(repliesModels));
    }

    private void showHeaderRepliesOnView(){actOnView(v -> v.showHeaderReplies(messagesModel));}

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
            messagesModel = messagesMapper.transform(message);
        }
    }

}
