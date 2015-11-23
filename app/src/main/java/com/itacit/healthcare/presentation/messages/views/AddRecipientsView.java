package com.itacit.healthcare.presentation.messages.views;


import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.presentation.base.views.View;

import java.util.List;

import rx.Observable;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 16.11.15.
 */
public interface AddRecipientsView extends View {
    Observable<String> getSearchRecipientsInput();

    void showRecipients(List<RecipientModel> recipients, RecipientType type);

    void showRecipientsCount(int count);

    MessageStorage getMessageStorage();

    void navigateToNewMessage();

    void navigateToRecipients();
}
