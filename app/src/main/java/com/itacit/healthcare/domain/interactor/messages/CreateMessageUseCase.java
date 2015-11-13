package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.network.request.CreateMessageRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nerevar on 11/12/2015.
 */
public class CreateMessageUseCase extends UseCase<Integer> {
	private CreateMessageRequest requestBody;

	public void execute(Subscriber<Integer> subscriber,
	                    CreateMessageRequest messageRequest) {
		this.requestBody = messageRequest;
		execute(subscriber);
	}

	@Override
	protected Observable<Integer> buildUseCaseObservable() {
		return MessagesService.getApi().createMessage(requestBody);
	}
}