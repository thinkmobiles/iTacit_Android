package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.FiltersEditText;
import com.itacit.healthcare.presentation.base.widgets.datePicker.DatePickerFragment;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.itacit.healthcare.presentation.messages.presenters.NewMessagePresenter;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.UsersAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by root on 11.11.15.
 */
public class NewMessageFragment extends BaseFragmentView<NewMessagePresenter, MessagesActivity>
		implements NewMessageView, UsersAdapter.OnUsersItemSelectedListener {
	@Bind(R.id.ib_add_FMN)          ImageButton ibAddRecipient;
	@Bind(R.id.et_recipients_FMN)   FiltersEditText etRecipientsView;
	@Bind(R.id.et_topic_FMN)        EditText etTopic;
	@Bind(R.id.ib_clear_FMN)        ImageButton ibClearDate;
	@Bind(R.id.et_date_FMN)         EditText etConfirmationDate;
	@Bind(R.id.rl_date_FMN)         RelativeLayout rlConfirmationDate;
	@Bind(R.id.et_message_body_FMN) EditText etMessageBody;

	private UsersAdapter usersAdapter;

	@OnClick(R.id.ib_add_FMN)
	void addRecipients() {
		presenter.addRecipients();
	}

	@Override
	protected void setUpView() {

	}

	@Override
	protected void setUpActionBar(ActionBar actionBar) {
		switchToolbarIndicator(false, v -> activity.switchContent(MessagesFeedFragment.class, false));

		actionBar.setHomeAsUpIndicator(null);
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
				sendMessage();
				return true;
			case R.id.action_set_date:
				showDatePicker();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	void sendMessage() {
		if (etRecipientsView.getText().toString().isEmpty() ||
				etTopic.getText().toString().isEmpty()) {
			Toast.makeText(getActivity(), getResources().getText(R.string.message_send_warning), Toast.LENGTH_LONG).show();
		} else {
			presenter.sendMessage();
		}
	}

	void showDatePicker() {
		DatePickerFragment fromDatePicker = new DatePickerFragment((datePicker, year, monthOfYear, dayOfMonth) ->
				presenter.onDateSelected(),
				() -> presenter.onDateClear());
		fromDatePicker.show(getFragmentManager(), "DatePicker");
	}

	@Override
	public void navigateToAddRecipients() {
		activity.switchContent(AddRecipientsFragment.class, false);
	}

	@Override
	public void showUsers(List<UserModel> users) {

		ArrayList<String> names = new ArrayList<>(users.size());
		for(UserModel userModel : users) {
			names.add(userModel.getFullName());
		}

		usersAdapter = new UsersAdapter(getActivity(), users);
		for (Filter filter : etRecipientsView.getSelectedFilters()) {
			usersAdapter.getSelectedUsersIds().add(filter.getId());
		}
		etRecipientsView.setAdapter(usersAdapter);
		etRecipientsView.showDropDown();
		usersAdapter.getFilter().filter(etRecipientsView.getInputText());
		usersAdapter.setOnUsersItemSelectedListener(this);
	}

	@Override
	public void addDate(String date) {
		rlConfirmationDate.setVisibility(View.VISIBLE);
		etConfirmationDate.setText(date);
	}

	@Override
	@OnClick(R.id.ib_clear_FMN)
	public void resetDate() {
		etConfirmationDate.setText("");
		rlConfirmationDate.setVisibility(View.GONE);
	}

	@Override
	public void addFilter(Filter filter) {
		etRecipientsView.addFilter(filter, true);
	}

	@Override
	public void removeFilter(Filter filter) {
		etRecipientsView.removeFilter(filter);
	}

	@Override
	public void unselectUser(String id) {
		usersAdapter.getSelectedUsersIds().remove(id);
		usersAdapter.notifyDataSetChanged();
	}

	@Override
	public Observable<String> getUsersSearchTextObs() {
		return RxTextView.textChangeEvents(etRecipientsView).map(e -> etRecipientsView.getInputText());
	}

	@Override
	public Observable<Filter> getFilterRemovedObs() {
		return etRecipientsView.getChipRemovedSubject();
	}

	@Override
	public BehaviorSubject<RecipientsModel> getSelectedRecipientsSubj() {
		return activity.getSelectedRecipientsSubj();
	}

	@Override
	public void onUsersSelected(String userId) {
		presenter.selectRecipient(userId);
	}

	@Override
	public void onUsersDeselected(String userId) {
		presenter.unselectRecipient(userId);
	}
}
