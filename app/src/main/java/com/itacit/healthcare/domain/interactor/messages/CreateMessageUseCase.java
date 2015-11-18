package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.network.request.CreateMessageRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.presentation.messages.models.CreateMessageModel;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel;
import com.itacit.healthcare.presentation.messages.models.RecipientsModel.RecipientType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nerevar on 11/12/2015.
 */
public class CreateMessageUseCase extends UseCase<Integer> {
	private CreateMessageRequest requestBody;

	public void execute(Subscriber<Integer> subscriber,
	                    CreateMessageModel messageModel) {
		requestBody = transform(messageModel);
		execute(subscriber);
	}

	private CreateMessageRequest transform (CreateMessageModel model) {
		final String USER_ID = "employeeId";

		CreateMessageRequest requestBody = new CreateMessageRequest();
		requestBody.setSubject(model.getSubject());
		requestBody.setBody(model.getBody());

		List<Map<String, String>> recipients = new ArrayList<>();
		RecipientsModel recipientsModel = model.getRecipients();
		//UserIds
		if (recipientsModel.getRecipients().containsKey(RecipientType.User)) {
			List<String> usersIds = recipientsModel.getRecipients().get(RecipientType.User);
			for (String userid : usersIds) {
				Map<String, String> userIdRow = new HashMap<>();
				userIdRow.put(USER_ID, userid);
				recipients.add(userIdRow);
			}
		}
		//BusinessIds
		if (recipientsModel.getRecipients().containsKey(RecipientType.Business)) {
			List<String> businessIds = recipientsModel.getRecipients().get(RecipientType.Business);
			for (String businessId : businessIds) {
				Map<String, String> businessIdRow = new HashMap<>();
				businessIdRow.put(USER_ID, businessId);
				recipients.add(businessIdRow);
			}
		}

		requestBody.setRecipients(recipients);
		return requestBody;
	}


	@Override
	protected Observable<Integer> buildUseCaseObservable() {
		return MessagesService.getApi().createMessage(requestBody);
	}
}
