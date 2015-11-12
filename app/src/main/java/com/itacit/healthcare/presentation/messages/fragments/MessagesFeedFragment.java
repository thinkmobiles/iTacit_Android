package com.itacit.healthcare.presentation.messages.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.MessagesActivity;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedFragment extends BaseFragmentView<MessagesFeedPresenter, MessagesActivity> implements NewMessageView {
    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {

    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected MessagesFeedPresenter createPresenter() {
        return null;
    }
}
