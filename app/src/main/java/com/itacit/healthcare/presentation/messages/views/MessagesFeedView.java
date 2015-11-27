package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by Den on 12.11.15.
 */
public interface MessagesFeedView extends View {
    void showMessages(List<MessageModel> messages, boolean canArchive);

    void showMessageDetails(String id);
    void disableLoadMore();
    void addMessages(List<MessageModel> messages);
    void removeMessage(String id);
    void showMessagesSummary(Map<MessagesFeedPresenter.MessagesFilter, String> summary);
}
