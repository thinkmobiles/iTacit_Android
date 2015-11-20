package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;

import rx.Observable;

/**
 * Created by root on 20.11.15.
 */
public class ConfirmMessageReadUseCase extends UseCase<Void, String, String> {
    @Override
    protected Observable<Void> buildUseCaseObservable(String messageId) {
        return MessagesService.getApi().confirmMessageRead(messageId);
    }

    @Override
    protected String initArgs(String messageId) {
        return messageId;
    }
}
