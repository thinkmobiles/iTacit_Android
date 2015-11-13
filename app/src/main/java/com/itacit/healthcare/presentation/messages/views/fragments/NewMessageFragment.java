package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.presenters.NewMessagePresenter;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public class NewMessageFragment extends BaseFragmentView<NewMessagePresenter, MessagesActivity> implements NewMessageView {
	@Bind(R.id.ib_add_FMN)          ImageButton ibAddRecipient;
	@Bind(R.id.et_recipients_FMN)   FiltersEditText etRecipientsView;
	@Bind(R.id.et_topic_FMN)        EditText etTopic;
	@Bind(R.id.ib_clear_FMN)        ImageButton ibClearDate;
	@Bind(R.id.et_date_FMN)         EditText etConfirmationDate;
	@Bind(R.id.rl_date_FMN)         RelativeLayout rlConfirmationDate;
	@Bind(R.id.et_message_body_FMN) EditText etMessageBody;

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
		return new NewMessagePresenter(new GetUsersUseCase(0, 100), new UserMapper());
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

	@Override
	public void showUsers(List<UserModel> users) {

		ArrayList<String> names = new ArrayList<>(users.size());
		for(UserModel userModel : users) {
			names.add(userModel.getFullName());
		}
		etRecipientsView.setAdapter(new ArrayAdapter<>(activity, R.layout.list_item_search_news, names));
	}

	@Override
	public Observable<String> getUsersSearchTextObs() {
		return RxTextView.textChangeEvents(etRecipientsView).map(e -> etRecipientsView.getInputText());
	}
}
