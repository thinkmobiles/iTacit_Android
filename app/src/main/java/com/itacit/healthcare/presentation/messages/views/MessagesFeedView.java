package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.MessageModel;

import java.util.List;

/**
 * Created by Den on 12.11.15.
 */
public interface MessagesFeedView extends View {
    void showMessages(List<MessageModel> messages);
    void showProgress();
    void showMessageDetails(String id);
    void removeMessage(String id);
    void hideProgress();
}
