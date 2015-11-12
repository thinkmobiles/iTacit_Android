package com.itacit.healthcare.presentation.messages.presenters;

import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;
import com.itacit.healthcare.presentation.news.mappers.UserMapper;
import com.itacit.healthcare.presentation.news.models.UserModel;

import java.util.List;

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

		}
	}
}
