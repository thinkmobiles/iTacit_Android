package com.itacit.healthcare.presentation.messages.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 17.11.15.
 */
public class RecipientsModel {
    public enum RecipientType {Business, Job, Role, Group, User}
    public enum PredefinedRecipients { myDirectReports, myIndirectReports, myCoworkers, myJobs, myBusiness }

    private HashMap<RecipientType, List<String>> recipients = new HashMap<>(RecipientType.values().length);
    private List<PredefinedRecipients> predefined = new ArrayList<>(PredefinedRecipients.values().length);

    public void selectRecipients(PredefinedRecipients recipients) {
        predefined.add(recipients);
    }

    public void unselectRecipients(PredefinedRecipients recipients) {
        predefined.remove(recipients);
    }

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

    public HashMap<RecipientType, List<String>> getIds() {
        return recipients;
    }

    public List<PredefinedRecipients> getPredefined() {
        return predefined;
    }

    public boolean containsRecipient(String id, RecipientType type) {
        return recipients.containsKey(type) && recipients.get(type).contains(id);
    }

    public int getRecipientsCount() {
        int sum = 0;
        for (RecipientType type : recipients.keySet()) {
            sum += recipients.get(type).size();
        }

        sum += predefined.size();
        return sum;
    }
}
