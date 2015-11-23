package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.RecipientsView;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsPresenter extends BasePresenter<RecipientsView> {
    private MessageStorage messageStorage;
    private RecipientsGroupedModel recipients;

    @Override
    protected void onAttachedView(@NonNull RecipientsView view) {
        messageStorage = view.getMessageStorage();
        recipients = messageStorage.getMessage().getRecipients();
    }

    public void onRecipientClick(String id, RecipientsGroupedModel.RecipientType type) {
        recipients.removeRecipient(id, type);
        actOnView(view -> view.removeRecipient(id, type));
    }
}
