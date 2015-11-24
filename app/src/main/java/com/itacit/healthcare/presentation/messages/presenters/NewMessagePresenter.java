package com.itacit.healthcare.presentation.messages.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.domain.interactor.messages.CreateMessageUseCase;
import com.itacit.healthcare.domain.interactor.users.GetUsersUseCase;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.messages.mappers.UserMapper;
import com.itacit.healthcare.presentation.messages.models.UserModel;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.NewMessageView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 11.11.15.
 */
public class NewMessagePresenter extends BasePresenter<NewMessageView> {
	public static final int SEARCH_TEXT_MIN_LENGTH = 3;
	private GetUsersUseCase getUsersUseCase;
	private CreateMessageUseCase createMessageUseCase;
	private UserMapper userMapper;

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
		messageStorage = view.getMessageStorage();
		messageModel = messageStorage.getMessage();
		compositeSubscription.add(view.getUsersSearchTextObs()
				.filter(text -> text.length() >= SEARCH_TEXT_MIN_LENGTH)
				.debounce(1, TimeUnit.SECONDS)
				.observeOn(view.getUiThreadScheduler())
				.subscribe(this::searchUsers));

		compositeSubscription.add(view.getFilterRemovedObs().subscribe(this::onChipRemoved));

		showPrevSelectedUsers();
	}

	private void showPrevSelectedUsers() {
		List<RecipientModel> userRecipients = messageModel.getRecipients()
				.getGroupedRecipients().get(RecipientType.User);
		if (userRecipients != null && !userRecipients.isEmpty()) {
			for (RecipientModel recipient : userRecipients) {
				actOnView(view -> view.addFilter(new Filter(recipient.getId(), recipient.getName(),
						Filter.FilterType.Author)));
			}
		}
	}

	private void onChipRemoved(Filter filter) {
		RecipientModel recipient = new RecipientModel();
		recipient.setId(filter.getId());
		messageModel.getRecipients().removeRecipient(recipient, RecipientType.User);
	}

	public void searchUsers(String query) {
		getUsersUseCase.execute(new UsersListSubscriber(), query);
	}

	private void showUsersOnView(List<User> users) {
		List<UserModel> userModels = userMapper.transform(users);
		actOnView(view -> view.showUsers(userModels));
	}

	public boolean isUserSelected(String id) {
		return messageModel.getRecipients().containsRecipient(id, RecipientType.User);
	}

	public void onUserClicked(UserModel user) {
		Filter filter = new Filter(user.getId(), user.getFullName(), Filter.FilterType.Author);
		RecipientModel recipient = new RecipientModel();
		recipient.setId(user.getId());
		recipient.setName(user.getFullName());

		if (isUserSelected(user.getId())) {
			messageModel.getRecipients().removeRecipient(recipient, RecipientType.User);
			actOnView(view -> view.removeFilter(filter));
		} else {
			actOnView(view -> view.addFilter(filter));
			messageModel.getRecipients().addRecipient(recipient, RecipientType.User);
		}
	}

	public void onDateSelected(int year, int month, int day) {
		Calendar calendar = new GregorianCalendar(year, month, day);
		messageModel.setReadRequired(true);
		messageModel.setReadRequiredDate(calendar.getTime());
		if (isDateValid()) {
			String date = NewMessageView.dateFormat.format(calendar.getTime());
			actOnView(v -> v.showDate(date));
		} else {
			actOnView(NewMessageView::showDateError);
		}
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
				Log.v("My Log", "Message sended with topic: " + messageModel.getSubject());
			}
		}, messageModel);
	}

	private boolean isMessageValid(String subject, String body) {
		return !subject.isEmpty() &&
				!(messageModel.getRecipients().getGroupedRecipients().isEmpty() &&
						messageModel.getRecipients().getPredefined().isEmpty()) &&
				!body.isEmpty();
	}

	private boolean isDateValid() {
		return !messageModel.isReadRequired() || messageModel.getReadRequiredDate().after(new Date());
	}

	private final class UsersListSubscriber extends Subscriber<List<User>> {

		@Override
		public void onCompleted() {
		}

		@Override
		public void onError(Throwable e) {
		}

		@Override
		public void onNext(List<User> users) {
			showUsersOnView(users);
		}
	}
}
