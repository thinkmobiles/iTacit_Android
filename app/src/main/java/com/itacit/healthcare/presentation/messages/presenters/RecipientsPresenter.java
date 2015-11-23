package com.itacit.healthcare.presentation.messages.presenters;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.RecipientInfo;
import com.itacit.healthcare.domain.interactor.messages.GetRecipientsFullListUseCase;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.models.UserRecipientModel;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.RecipientsView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsPresenter extends BasePresenter<RecipientsView> {
    private MessageStorage messageStorage;
    private RecipientsGroupedModel recipients;
    private GetRecipientsFullListUseCase getRecipientsFullList;
    List<UserRecipientModel> usersRecipients = new ArrayList<>();

    public RecipientsPresenter(GetRecipientsFullListUseCase getRecipientsFullList) {
        this.getRecipientsFullList = getRecipientsFullList;
    }

    @Override
    protected void onAttachedView(@NonNull RecipientsView view) {
        messageStorage = view.getMessageStorage();
        recipients = messageStorage.getMessage().getRecipients();
        getRecipientsFullList.execute(new Subscriber<List<RecipientInfo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<RecipientInfo> recipientInfos) {
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

                actOnView(view1 -> view.showRecipients(usersRecipients));
            }
        }, recipients);
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
        CreateMessageModel messageModel =  messageStorage.getMessage() != null ?
                messageStorage.getMessage() : new CreateMessageModel();
        messageModel.setRecipients(recipients);
        messageStorage.pushCreateMessage(messageModel);
        actOnView(view -> view.navigateToCreateMessage());
    }
}
