package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;

import java.util.List;

/**
 * Created by Den on 12.11.15.
 */
public interface MessagesFeedView extends View {
    void showMessages(List<MessagesModel> messages);
    void showProgress();
    void hideProgress();
}
