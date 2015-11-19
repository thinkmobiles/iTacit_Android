package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;

import rx.Observable;

/**
 * Created by root on 19.11.15.
 */
public class ArchiveMessageUseCase extends UseCase<Object, String, String> {

    @Override
    protected Observable<Object> buildUseCaseObservable(String messageId) {
        return MessagesService.getApi().archiveMessage(messageId);
    }

    @Override
    protected String initArgs(String messageId) {
        return messageId;
    }
}
