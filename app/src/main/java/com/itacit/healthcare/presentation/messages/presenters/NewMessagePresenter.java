package com.itacit.healthcare.presentation.messages.presenters;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.UserModel;

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
		}
	}

	public void searchUsers(String query) {
		getUsersUseCase.execute(new UsersListSubscriber(), query);
	}

	private void showUsersOnView() {
		if(getView() != null) getView().showUsers(userModels);
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
