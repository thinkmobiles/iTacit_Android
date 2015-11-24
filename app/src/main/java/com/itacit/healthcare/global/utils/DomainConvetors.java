package com.itacit.healthcare.global.utils;

import android.support.annotation.NonNull;

import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 23.11.15.
 */
public class DomainConvetors {
    @NonNull
    public static List<Map<String, String>> convertRecipients(RecipientsGroupedModel recipientsModel) {
        List<Map<String, String>> recipients = new ArrayList<>();

        for (RecipientsGroupedModel.RecipientType type : RecipientsGroupedModel.RecipientType.values()) {
            if (recipientsModel.getGroupedRecipients().containsKey(type)) {
                List<RecipientModel> ids = recipientsModel.getGroupedRecipients().get(type);
                for (RecipientModel recipient : ids) {
                    Map<String, String> rowId = new HashMap<>();
                    rowId.put(type.toString(), recipient.getId());
                    recipients.add(rowId);
                }
            }
        }

        for (RecipientsGroupedModel.PredefinedRecipients type : RecipientsGroupedModel.PredefinedRecipients.values()) {
            if (recipientsModel.getPredefined().contains(type)) {
                Map<String, String> row = new HashMap<>();
                row.put(type.toString(), "Y");
                recipients.add(row);
            }
        }
        return recipients;
    }
}
