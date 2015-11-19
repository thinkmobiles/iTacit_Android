package com.itacit.healthcare.presentation.messages.views.activity;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.fragments.AddRecipientsFragment;

import java.util.Stack;

/**
 * Created by root on 11.11.15.
 */
public class MessagesActivity extends BaseActivity implements MessageStorage {

    private Stack<CreateMessageModel> messageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        messageStorage = new Stack<>();
        messageStorage.setSize(1);
        messageStorage.push(new CreateMessageModel());

        super.onCreate(savedInstanceState);
        switchContent(AddRecipientsFragment.class, false);
//		switchContent(MessagesFeedFragment.class, false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_messages;
    }

    public CreateMessageModel getMessage() {
        return messageStorage.peek();
    }

    public CreateMessageModel popMessage() {
        CreateMessageModel messageModel = messageStorage.peek();
        messageStorage.push(new CreateMessageModel());
        return messageModel;
    }

    public void pushCreateMessage(CreateMessageModel model) {
        messageStorage.push(model);
    }

}
