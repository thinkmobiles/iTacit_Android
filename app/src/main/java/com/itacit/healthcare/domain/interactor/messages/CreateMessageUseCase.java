package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.network.request.CreateMessageRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel.PredefinedRecipients;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

import java.text.SimpleDateFormat;
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
	private final SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-mm-dd");
	private CreateMessageRequest requestBody;

	public void execute(Subscriber<Integer> subscriber,
	                    CreateMessageModel messageModel) {
		requestBody = transform(messageModel);
		execute(subscriber);
	}

	private CreateMessageRequest transform (CreateMessageModel model) {
		CreateMessageRequest requestBody = new CreateMessageRequest();
		requestBody.setSubject(model.getSubject());
		requestBody.setBody(model.getBody());

		List<Map<String, String>> recipients = new ArrayList<>();
		RecipientsGroupedModel recipientsGroupedModel = model.getRecipients();

		for (RecipientType type : RecipientType.values()) {
			parseRecipients(recipients, recipientsGroupedModel, type, type.toString());
		}

		for (PredefinedRecipients predefinedRecipients : PredefinedRecipients.values()) {
			Map<String, String> row = new HashMap<>();
			row.put(predefinedRecipients.toString(), "Y");
			recipients.add(row);
		}

		if (model.isReadRequired()) {
			requestBody.setReadRequiredYn("YES");
			requestBody.setReadRequiredDate(simpleFormatter.format(model.getReadRequiredDate()));
		}


		requestBody.setRecipients(recipients);
		return requestBody;
	}

	private void parseRecipients(List<Map<String, String>> recipients, RecipientsGroupedModel recipientsGroupedModel,
								 RecipientType type, String fieldName) {
		if (recipientsGroupedModel.getGroupedRecipients().containsKey(type)) {
			List<RecipientModel> ids = recipientsGroupedModel.getGroupedRecipients().get(type);
			for (RecipientModel recipient : ids) {
				Map<String, String> rowId = new HashMap<>();
				rowId.put(fieldName, recipient.getId());
				recipients.add(rowId);
			}
		}
	}

	@Override
	protected Observable<Integer> buildUseCaseObservable() {
		return MessagesService.getApi().createMessage(requestBody);
	}
}
