package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.UserRecipientModel;

import java.util.List;

/**
 * Created by root on 23.11.15.
 */
public interface RecipientsView extends View {
    void showRecipients(List<UserRecipientModel> models, boolean showDeleteIcon);
    void removeRecipient(int position);
    MessageStorage getMessageStorage();

    void navigateToCreateMessage();
}
