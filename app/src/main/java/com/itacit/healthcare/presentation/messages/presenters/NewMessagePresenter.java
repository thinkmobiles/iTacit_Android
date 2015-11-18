package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.domain.interactor.messages.CreateMessageUseCase;
import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.CreateMessageModel;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;

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
	private CreateMessageUseCase createMessageUseCase;
	private UserMapper userMapper;
	private List<UserModel> userModels;

	private CreateMessageModel messageModel;
	private MessageStorage messageStorage;


	public NewMessagePresenter(GetUsersUseCase getUsersUseCase,
							   CreateMessageUseCase createMessageUseCase, UserMapper userMapper) {
		this.getUsersUseCase = getUsersUseCase;
		this.createMessageUseCase = createMessageUseCase;
		this.userMapper = userMapper;
	}

	@Override
	protected void onAttachedView(@NonNull NewMessageView view) {
		compositeSubscription.add(view.getUsersSearchTextObs()
				.filter(text -> text.length() >= SEARCH_TEXT_MIN_LENGTH)
				.debounce(1, TimeUnit.SECONDS)
				.observeOn(view.getUiThreadScheduler())
				.subscribe(this::searchUsers));

		compositeSubscription.add(view.getFilterRemovedObs().subscribe(this::removeRecipient));
		messageStorage = view.getMessageStorage();
		messageModel = messageStorage.getMessage();
	}

	public void searchUsers(String query) {
		getUsersUseCase.execute(new UsersListSubscriber(), query);
	}

	private void showUsersOnView() {
		if(getView() != null) getView().showUsers(userModels);
	}

	public void selectRecipient(String id) {
		for (UserModel userModel : userModels) {
			if (userModel.getId().equals(id)) {
				Filter filter = new Filter(id, userModel.getFullName(), Filter.FilterType.Author);
				actOnView(view -> view.addFilter(filter));
				messageModel.getRecipients().addRecipient(id, RecipientsModel.RecipientType.User);
				break;
			}
		}
	}

	public void unselectRecipient(String id) {
		for (UserModel userModel : userModels) {
			if (userModel.getId().equals(id)) {
				Filter filter = new Filter(id, userModel.getFullName(), Filter.FilterType.Author);
				actOnView(view -> view.removeFilter(filter));
				messageModel.getRecipients().removeRecipient(id, RecipientsModel.RecipientType.User);
				break;
			}
		}
	}

	public void removeRecipient(Filter filter) {
		actOnView(view -> view.unselectUser(filter.getId()));
	}

	public void onDateSelected(int year, int month, int day) {
		Calendar calendar = new GregorianCalendar(year, month, day);
		messageModel.setReadRequired(true);
		messageModel.setReadRequiredDate(calendar.getTime());
		String date = NewMessageView.dateFormat.format(calendar.getTime());
		actOnView(v -> v.showDate(date));
	}

	public void onDateClear() {
		messageModel.setReadRequired(false);
		actOnView(NewMessageView::resetDate);
	}

	public void addRecipients() {
		actOnView(view -> {
			messageStorage.pushCreateMessage(messageModel);
			view.navigateToAddRecipients();
		});
	}

	public void sendMessage(String subject, String body) {
		if (!isMessageValid(subject, body)) {
			actOnView(NewMessageView::showMessageInvalid);
			return;
		}

		messageModel.setSubject(subject);
		messageModel.setBody(body);

		createMessageUseCase.execute(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer integer) {

			}
		}, messageModel);
	}

	private boolean isMessageValid(String subject, String body) {
		return !subject.isEmpty() &&
				!(messageModel.getRecipients().getIds().isEmpty() &&
						messageModel.getRecipients().getPredefined().isEmpty()) &&
				!body.isEmpty();
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
