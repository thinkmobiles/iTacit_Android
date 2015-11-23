package com.itacit.healthcare.domain.interactor.messages;

import com.itacit.healthcare.data.entries.RecipientsSummary;
import com.itacit.healthcare.data.network.request.RecipientsInfoRequest;
import com.itacit.healthcare.data.network.services.MessagesService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.PredefinedRecipients;
import static com.itacit.healthcare.domain.models.RecipientsGroupedModel.RecipientType;

/**
 * Created by root on 23.11.15.
 */
public class GetRecipientsSummaryUseCase extends UseCase<RecipientsSummary, RecipientsGroupedModel, RecipientsInfoRequest> {
    @Override
    protected Observable<RecipientsSummary> buildUseCaseObservable(RecipientsInfoRequest requestBody) {
        return MessagesService.getApi().getRecipientsSummary(requestBody);
    }

    @Override
    protected RecipientsInfoRequest initArgs(RecipientsGroupedModel recipientsModel) {
        RecipientsInfoRequest request = new RecipientsInfoRequest();
        List<Map<String, String>> recipients = new ArrayList<>();

        for (RecipientType type : RecipientType.values()) {
            if (recipientsModel.getGroupedRecipients().containsKey(type)) {
                List<RecipientModel> ids = recipientsModel.getGroupedRecipients().get(type);
                for (RecipientModel recipient : ids) {
                    Map<String, String> rowId = new HashMap<>();
                    rowId.put(type.toString(), recipient.getId());
                    recipients.add(rowId);
                }
            }
        }

        for (PredefinedRecipients type : PredefinedRecipients.values()) {
            if (recipientsModel.getPredefined().contains(type)) {
                Map<String, String> row = new HashMap<>();
                row.put(type.toString(), "Y");
                recipients.add(row);
            }
        }

        request.setRecipients(recipients);
        return request;
    }
}
