package com.itacit.healthcare.presentation.messages.presenters;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.views.NewsSearchView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

/**
 * Created by root on 11.11.15.
 */
public class NewMessagePresenter extends BasePresenter<NewMessageView> {

	public static final int SEARCH_TEXT_MIN_LENGTH = 3;
	private GetUsersUseCase getUsersUseCase;
	private UserMapper userMapper;
	private List<UserModel> userModels;

	public NewMessagePresenter(GetUsersUseCase getUsersUseCase, UserMapper userMapper) {
		this.getUsersUseCase = getUsersUseCase;
		this.userMapper = userMapper;
	}

	@Override
	protected void onViewAttach() {
		if (getView()!= null) {
			compositeSubscription.add(getView().getUsersSearchTextObs()
					.filter(text -> text.length() >= SEARCH_TEXT_MIN_LENGTH)
					.debounce(1, TimeUnit.SECONDS)
					.observeOn(getView().getUiThreadScheduler())
					.subscribe(this::searchUsers));

			compositeSubscription.add(getView().getFilterRemovedObs().subscribe(this::removeFilter, e -> e.printStackTrace()));
		}
	}

	public void searchUsers(String query) {
		getUsersUseCase.execute(new UsersListSubscriber(), query);
	}

	private void showUsersOnView() {
		if(getView() != null) getView().showUsers(userModels);
	}

	public void selectUserFilterById(String id) {
		for (UserModel userModel : userModels) {
			if (userModel.getId().equals(id)) {
				Filter filter = new Filter(id, userModel.getFullName(), Filter.FilterType.Author);
				if (getView() != null) getView().addFilter(filter);
			}
		}
	}

	public void unselectUserFilterById(String id) {
		for (UserModel userModel : userModels) {
			if (userModel.getId().equals(id)) {
				Filter filter = new Filter(id, userModel.getFullName(), Filter.FilterType.Author);
				if (getView() != null) getView().removeFilter(filter);
			}
		}
	}

	public void removeFilter(Filter filter) {
			if (getView() != null) getView().unselectUser(filter.getId());
	}

	public void onDateSelected() {
		Calendar calendar = Calendar.getInstance();

		String date = NewMessageView.dateFormat.format(calendar.getTime());
		actOnView(v -> v.addDate(date));
	}

	public void onDateClear() {
		actOnView(v -> v.resetDate());

	}

	private final class UsersListSubscriber extends Subscriber<List<User>> {

		@Override
		public void onCompleted() {
			showUsersOnView();
		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onNext(List<User> users) {
			userModels = userMapper.transform(users);
		}
	}
}
