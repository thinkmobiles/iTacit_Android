package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.CreateReplyUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.presenters.NewReplyPresenter;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;

import butterknife.Bind;

/**
 * Created by root on 19.11.15.
 */
public class NewReplyFragment extends BaseFragmentView<NewReplyPresenter, MessagesActivity> {
	@Bind(R.id.iv_icon_FMNR)        ImageView ivIcon;
	@Bind(R.id.tv_header_FMNR)      TextView tvHeader;
	@Bind(R.id.et_body_FMNR)        EditText etBody;

	private enum ReplyPrivacy {All, Private}
	private ReplyPrivacy privacyState;
	private String recipient = "";

	@Override
	protected void setUpView() {

		etBody.setText(recipient + ",");
	}

	@Override
	protected void setUpActionBar(ActionBar actionBar) {
		switchToolbarIndicator(false, v -> activity.switchContent(MessageRepliesFragment.class));

		privacyState = ReplyPrivacy.All;

		if (privacyState.equals(ReplyPrivacy.All)) {
			recipient = getString(R.string.all);
			tvHeader.setText(getText(R.string.reply_to_all));
			ivIcon.setVisibility(View.GONE);
		} else {
			tvHeader.setText(getText(R.string.private_reply) + " " + recipient);
		}

		actionBar.setHomeAsUpIndicator(null);
		activity.setActionBarShadowVisible(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(getString(R.string.reply_to) + " " + recipient);

		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDefaultDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_message_new_reply;
	}

	@Override
	protected NewReplyPresenter createPresenter() {
		return new NewReplyPresenter(new CreateReplyUseCase());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_reply, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				return true;
			case R.id.action_send:
				presenter.sendReply(etBody.getText().toString());
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
