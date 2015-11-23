package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.presentation.base.views.View;

/**
 * Created by root on 23.11.15.
 */
public interface RecipientsView extends View {
    void showRecipients(RecipientsGroupedModel recipientsGroupedModel);
    void removeRecipient(String userId, RecipientsGroupedModel.RecipientType recipientType);
    void removeRecipientGroup(RecipientsGroupedModel.PredefinedRecipients recipientType);
    MessageStorage getMessageStorage();
}
