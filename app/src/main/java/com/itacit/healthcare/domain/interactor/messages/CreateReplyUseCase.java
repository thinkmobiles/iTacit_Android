package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.data.network.request.CreateReplyRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.presentation.messages.models.CreateReplyModel;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by root on 19.11.15.
 */
public class CreateReplyUseCase extends UseCase<Reply> {

	private CreateReplyRequest requestBody;

	public void execute(Subscriber<Reply> subscriber,
	                    CreateReplyModel replyModel) {
		requestBody = transform(replyModel);
		execute(subscriber);
	}

	private CreateReplyRequest transform (CreateReplyModel model) {
		CreateReplyRequest requestBody = new CreateReplyRequest();
		requestBody.setMessageId(model.getId());
		requestBody.setBody(model.getBody());
		requestBody.setSenderID(model.getSenderId());
		requestBody.setPrivateYn(model.getIsPrivate() ? "Y" : "N");
		return  requestBody;
	}

	@Override
	protected Observable<Reply> buildUseCaseObservable() {
		return MessagesService.getApi().createReply(requestBody);
	}
}
