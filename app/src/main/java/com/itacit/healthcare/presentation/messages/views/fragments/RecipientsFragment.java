package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.presenters.RecipientsPresenter;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.RecipientsView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsFragment extends BaseFragmentView<RecipientsPresenter, MessagesActivity>
        implements RecipientsView {
    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipients_list;
    }

    @Override
    protected RecipientsPresenter createPresenter() {
        return new RecipientsPresenter();
    }

    @Override
    public void showRecipients(RecipientsGroupedModel recipientsGroupedModel) {

    }

    @Override
    public void removeRecipient(String userId, RecipientsGroupedModel.RecipientType recipientType) {
        switch (recipientType) {
            case Business:
                break;
            case Job:
                break;
            case Role:
                break;
            case Group:
                break;
            case User:
                break;
        }
    }

    @Override
    public void removeRecipientGroup(RecipientsGroupedModel.PredefinedRecipients recipientType) {

    }

    @Override
    public MessageStorage getMessageStorage() {
        return activity;
    }
}
