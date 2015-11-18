package com.itacit.healthcare.presentation.messages.views.activity;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel;
import com.itacit.healthcare.presentation.messages.views.fragments.AddRecipientsFragment;

import rx.subjects.BehaviorSubject;

/**
 * Created by root on 11.11.15.
 */
public class MessagesActivity extends BaseActivity {

    private BehaviorSubject<RecipientsModel> recipients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        recipients = BehaviorSubject.create(new RecipientsModel());
        super.onCreate(savedInstanceState);
        switchContent(AddRecipientsFragment.class, false);
//		switchContent(MessagesFeedFragment.class, false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_messages;
    }

    public BehaviorSubject<RecipientsModel> getSelectedRecipientsSubj() {
        return recipients;
    }
}
