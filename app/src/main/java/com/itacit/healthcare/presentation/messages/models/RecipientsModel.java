package com.itacit.healthcare.presentation.messages.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 17.11.15.
 */
public class RecipientsModel {
       private HashMap<RecipientType, List<String>> recipients = new HashMap<>(RecipientType.values().length);

    public void addRecipient(String id, RecipientType type) {
        if (recipients.containsKey(type)) {
            recipients.get(type).add(id);
        } else {
            List<String> ids = new ArrayList<>();
            ids.add(id);
            recipients.put(type, ids);
        }
    }

    public void removeRecipient(String id, RecipientType type) {
        if (recipients.containsKey(type)) {
            recipients.get(type).remove(id);
        }
    }

    public List<String> getRecipients(RecipientType type) {
        return recipients.get(type);
    }

    public boolean contains(String id, RecipientType type) {
        if (!recipients.containsKey(type)) {
            return false;
        } else {
            return recipients.get(type).contains(id);
        }
    }

    public int getRecipientsCount() {
        int sum = 0;
        for(RecipientType type : recipients.keySet()) {
            sum += recipients.get(type).size();
        }
        return sum;
    }

    public enum RecipientType { Business, Job, Role, Group, User }
}
