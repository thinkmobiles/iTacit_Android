package com.itacit.healthcare.presentation.messages.views.activity;

import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.itacit.healthcare.presentation.messages.views.fragments.MessagesFeedFragment;
import com.itacit.healthcare.presentation.messages.views.fragments.NewMessageFragment;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.messages.views.fragments.AddRecipientsFragment;

import rx.subjects.BehaviorSubject;

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
		switchContent(AddRecipientsFragment.class, false);
	}
}
