package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;

import java.util.List;

/**
 * Created by Den on 17.11.15.
 */
public interface MessageRepliesView extends View {
    void showHeaderReplies(MessageModel messageModel);
    void showListReplies(List<RepliesModel> replies);
    void replyToAll();
    void privateReply();
}
