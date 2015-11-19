package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.messages.models.UserModel;

import java.text.SimpleDateFormat;
import java.util.List;

import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public interface NewMessageView extends View {

	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

	void navigateToAddRecipients();
	void showUsers(List<UserModel> users);
	void showMessageInvalid();
	String getMessageBody();
	void showDate(String date);
	void resetDate();
	void addFilter(Filter filter);
	void removeFilter(Filter filter);
	Observable<String> getUsersSearchTextObs();
	Observable<Filter> getFilterRemovedObs();
	MessageStorage getMessageStorage();
}
