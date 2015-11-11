package com.itacit.healthcare.presentation.messages.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.messages.presenters.NewMessagePresenter;
import com.itacit.healthcare.presentation.messages.views.INewMessageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 11.11.15.
 */
public class NewMessageFragment extends BaseFragmentView<NewMessagePresenter> implements INewMessageView {
	@Bind(R.id.ib_add_FMN)
	ImageButton ibAddRecipient;
	@Bind(R.id.et_recipients_FMN)
	FiltersEditText etRecipients;
	@Bind(R.id.et_topic_FMN)
	EditText etTopic;
	@Bind(R.id.ib_clear_FMN)
	ImageButton ibClearDate;
	@Bind(R.id.et_date_FMN)
	EditText etConfirmationDate;
	@Bind(R.id.rl_date_FMN)
	RelativeLayout rlConfirmationDate;
	@Bind(R.id.et_message_body_FMN)
	EditText etMessageBody;

	@Override
	protected void setUpView() {

	}

	@Override
	protected void setUpActionBar(ActionBar actionBar) {
		activity.setActionBarShadowVisible(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.title_new_message);

		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDefaultDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_message_new;
	}

	@Override
	protected NewMessagePresenter createPresenter() {
		return new NewMessagePresenter();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_new_message, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:

				return true;

			case R.id.action_send:

				return true;

			case R.id.action_set_date:
				rlConfirmationDate.setVisibility(View.VISIBLE);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
