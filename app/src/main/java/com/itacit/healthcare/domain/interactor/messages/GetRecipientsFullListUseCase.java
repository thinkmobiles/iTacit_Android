package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.RecipientInfo;
import com.itacit.healthcare.data.network.request.RecipientsInfoRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.global.utils.DomainConvetors;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by root on 23.11.15.
 */
public class GetRecipientsFullListUseCase extends UseCase<List<RecipientInfo>, RecipientsGroupedModel, RecipientsInfoRequest> {
    @Override
    protected Observable<List<RecipientInfo>> buildUseCaseObservable(RecipientsInfoRequest requestBody) {
        return MessagesService.getApi().getRecipientsFullList(requestBody)
                .map(ListResponse::getResponseRows);
    }

    @Override
    protected RecipientsInfoRequest initArgs(RecipientsGroupedModel recipientsModel) {
        RecipientsInfoRequest request = new RecipientsInfoRequest();
        List<Map<String, String>> recipients = DomainConvetors.convertRecipients(recipientsModel);
        request.setRecipients(recipients);
        return request;
    }
}
