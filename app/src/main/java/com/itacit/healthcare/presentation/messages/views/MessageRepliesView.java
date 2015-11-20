package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;

import java.util.List;

/**
 * Created by Den on 17.11.15.
 */
public interface MessageRepliesView extends View {
    void showHeaderReplies(MessagesModel messagesModel);
    void showListReplies(List<RepliesModel> replies);
}
