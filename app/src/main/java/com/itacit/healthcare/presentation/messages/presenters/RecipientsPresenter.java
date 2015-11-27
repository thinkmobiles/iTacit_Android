package com.itacit.healthcare.presentation.messages.presenters;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.Recipient;
import com.itacit.healthcare.data.entries.RecipientInfo;
import com.itacit.healthcare.domain.interactor.messages.GetRecipientsFullListUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetRecipientsUseCase;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.models.UserRecipientModel;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.RecipientsView;

import java.util.ArrayList;
import java.util.List;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsPresenter extends BasePresenter<RecipientsView> {
    private MessageStorage messageStorage;
    private RecipientsGroupedModel recipients;
    private GetRecipientsFullListUseCase getRecipientsFullList;
    private List<UserRecipientModel> usersRecipients = new ArrayList<>();
    private GetRecipientsUseCase getRecipientsUseCase;
    private String messageId;
    private boolean showReadConfirmed;

    public RecipientsPresenter(GetRecipientsFullListUseCase getRecipientsFullList,
                               GetRecipientsUseCase getRecipientsUseCase, String messageId,
                               boolean showReadConfirmed) {
        this.getRecipientsFullList = getRecipientsFullList;
        this.getRecipientsUseCase = getRecipientsUseCase;
        this.messageId = messageId;
        this.showReadConfirmed = showReadConfirmed;
    }

    @Override
    protected void onAttachedView(@NonNull RecipientsView view) {
        messageStorage = view.getMessageStorage();
        recipients = messageStorage.getMessage().getRecipients();
        if (messageId != null) {
            getRecipientsUseCase.execute(this::showRecipientsOnView, errorHandler, messageId);
        } else {
            getRecipientsFullList.execute(this::showRecipientsInfoOnView, errorHandler, recipients);
        }
    }

    private void showRecipientsInfoOnView(List<RecipientInfo> recipientInfos) {
        usersRecipients = new ArrayList<>();
        for (RecipientInfo recipientInfo : recipientInfos) {
            if (recipientInfo.getNameFull() != null) {
                UserRecipientModel user = new UserRecipientModel();
                user.setId(recipientInfo.getId());
                user.setFullName(recipientInfo.getNameFull());
                user.setParent(recipientInfo.getParentName());
                user.setRole(recipientInfo.getRoleName());
                user.setBusiness(recipientInfo.getBusinessUnitName());
                user.setImageUri(Uri.parse(recipientInfo.getImageUrl()));
                usersRecipients.add(user);
            }
        }

        actOnView(view -> view.showRecipients(usersRecipients, true));
    }

    private void showRecipientsOnView(List<Recipient> recipients) {
        usersRecipients = new ArrayList<>();
        for (Recipient recipient : recipients) {
            if (showReadConfirmed && !recipient.getReadConfirmedYn().equals("Y")) {
                continue;
            }

            if (recipient.getNameFull() != null) {
                UserRecipientModel user = new UserRecipientModel();
                user.setId(recipient.getId());
                user.setFullName(recipient.getNameFull());
                user.setRole(recipient.getRoleName());
                user.setImageUri(Uri.parse(recipient.getImageUrl()));
                usersRecipients.add(user);
            }
        }

        actOnView(view -> view.showRecipients(usersRecipients, false));
    }

    public void onUserRecipientClick(String id) {
        for (UserRecipientModel userRecipient : usersRecipients) {
            if (userRecipient.getId().equals(id)) {
                actOnView(view -> view.removeRecipient(usersRecipients.indexOf(userRecipient)));
                usersRecipients.remove(userRecipient);
                break;
            }
        }

    }

    public void selectRecipients() {
        List<RecipientModel> recipientModels = new ArrayList<>();
        for (UserRecipientModel userRecipient : usersRecipients) {
            RecipientModel recipient = new RecipientModel();
            recipient.setId(userRecipient.getId());
            recipient.setName(userRecipient.getFullName());
            recipientModels.add(recipient);
        }

        recipients.clearAllRecipients();
        recipients.getGroupedRecipients().put(RecipientType.User, recipientModels);
        CreateMessageModel messageModel = messageStorage.getMessage() != null ?
                messageStorage.getMessage() : new CreateMessageModel();
        messageModel.setRecipients(recipients);
        messageStorage.pushCreateMessage(messageModel);
        actOnView(view -> view.navigateToCreateMessage());
    }
}
