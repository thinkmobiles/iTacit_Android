package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.domain.interactor.messages.GetListRepliesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.mappers.ListRepliesMapper;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.views.MessageRepliesView;
import com.itacit.healthcare.presentation.messages.views.fragments.MessageRepliesFragment;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Den on 17.11.15.
 */
public class MessageRepliesPresenter extends BasePresenter<MessageRepliesView> {
    public List<RepliesModel> repliesModels;

    private GetListRepliesUseCase getListRepliesUseCase;
    private ListRepliesMapper dataMapper;
    private MessageRepliesFragment messageRepliesFragment;
    private String messageId;

    public MessageRepliesPresenter(ListRepliesMapper listRepliesMapper,
                                   GetListRepliesUseCase getListRepliesUseCase,
                                   String messageId) {
        this.dataMapper = listRepliesMapper;
        this.getListRepliesUseCase = getListRepliesUseCase;
        this.messageId = messageId;
    }

    @Override
    protected void onAttachedView(@NonNull MessageRepliesView view) {
            getListRepliesUseCase.execute(new RepliesListSubscriber(), messageId);
    }

    private void showRepliesOnView() {
        actOnView(v -> v.showListReplies(repliesModels));
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
            repliesModels = dataMapper.transform(replies);
        }
    }
}
