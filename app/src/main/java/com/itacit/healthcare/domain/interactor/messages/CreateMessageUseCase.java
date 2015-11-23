package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.network.request.CreateMessageRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.domain.models.CreateMessageModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.global.utils.DomainConvetors;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Nerevar on 11/12/2015.
 */
public class CreateMessageUseCase extends UseCase<Integer, CreateMessageModel, CreateMessageRequest> {
	private final SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-mm-dd");


	private CreateMessageRequest transform (CreateMessageModel model) {
		CreateMessageRequest requestBody = new CreateMessageRequest();
		requestBody.setSubject(model.getSubject());
		requestBody.setBody(model.getBody());

		RecipientsGroupedModel recipientsModel = model.getRecipients();
		List<Map<String, String>> recipients = DomainConvetors.convertRecipients(recipientsModel);

		if (model.isReadRequired()) {
			requestBody.setReadRequiredYn("YES");
			requestBody.setReadRequiredDate(simpleFormatter.format(model.getReadRequiredDate()));
		}

		requestBody.setRecipients(recipients);
		return requestBody;
	}

	@Override
	protected Observable<Integer> buildUseCaseObservable(CreateMessageRequest obsArgs) {
		return MessagesService.getApi().createMessage(obsArgs);
	}

	@Override
	protected CreateMessageRequest initArgs(CreateMessageModel args) {
		return transform(args);
	}
}
