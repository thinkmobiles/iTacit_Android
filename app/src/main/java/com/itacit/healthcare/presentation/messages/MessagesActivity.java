package com.itacit.healthcare.presentation.messages;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.messages.fragments.NewMessageFragment;

/**
 * Created by root on 11.11.15.
 */
public class MessagesActivity extends BaseActivity {
	@Override
	protected int getLayoutRes() {
		return R.layout.activity_messages;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switchContent(NewMessageFragment.class, false);
	}
}
