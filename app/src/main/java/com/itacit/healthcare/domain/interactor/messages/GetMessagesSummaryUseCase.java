package com.itacit.healthcare.domain.interactor.messages;

import android.support.annotation.NonNull;

import com.itacit.healthcare.data.entries.MessagesSummary;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;

import rx.Observable;

/**
 * Created by root on 26.11.15.
 */
public class GetMessagesSummaryUseCase extends UseCase<MessagesSummary, Void, Void> {
    @Override
    protected Observable<MessagesSummary> buildUseCaseObservable(Void obsArgs) {
        return MessagesService.getApi().getMessagesSummary(new Object());
    }

    @NonNull
    @Override
    protected Void initArgs(Void args) {
        return null;
    }
}
