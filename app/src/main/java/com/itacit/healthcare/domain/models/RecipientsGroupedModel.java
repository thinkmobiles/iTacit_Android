package com.itacit.healthcare.domain.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 17.11.15.
 */
public class RecipientsGroupedModel {
    public enum RecipientType { Business("businessUnitId"), Job("jobClassificationId"),
        Role("roleId"), Group("permissionGroupId"), User("employeeId");

        private final String typeName;

        RecipientType(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

    public enum PredefinedRecipients { myDirectReports("myDirectReports"), myIndirectReports("myIndirectReport"),
        myCoworkers("myCoworkers"), myJobs("myPositions"), myBusiness("myBusinessUnits");
        private final String typeName;

        PredefinedRecipients(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

    private HashMap<RecipientType, List<RecipientModel>> recipientGrouped = new HashMap<>(RecipientType.values().length);
    private List<PredefinedRecipients> predefined = new ArrayList<>(PredefinedRecipients.values().length);

    public void selectRecipients(PredefinedRecipients recipients) {
        predefined.add(recipients);
    }

    public void unselectRecipients(PredefinedRecipients recipients) {
        predefined.remove(recipients);
    }

    public void addRecipient(RecipientModel recipient, RecipientType type) {
        if (recipientGrouped.containsKey(type)) {
            recipientGrouped.get(type).add(recipient);
        } else {
            List<RecipientModel> recipients = new ArrayList<>();
            recipients.add(recipient);
            this.recipientGrouped.put(type, recipients);
        }
    }

    public void removeRecipient(RecipientModel recipient, RecipientType type) {
        if (recipientGrouped.containsKey(type)) {
            for (RecipientModel recipientModel : recipientGrouped.get(type)){
                if (recipient.getId().equals(recipientModel.getId())) {
                    recipientGrouped.get(type).remove(recipientModel);
                    break;
                }
            }
        }
    }

    public HashMap<RecipientType, List<RecipientModel>> getGroupedRecipients() {
        return recipientGrouped;
    }

    public List<PredefinedRecipients> getPredefined() {
        return predefined;
    }

    public boolean containsRecipient(String id, RecipientType type) {
        if (!recipientGrouped.containsKey(type)) {
            return false;
        }

        for (RecipientModel recipient : recipientGrouped.get(type)){
            if (recipient.getId().equals(id)) return true;
        }
        return false;
    }

    public int getRecipientsCount() {
        int sum = 0;
        for (RecipientType type : recipientGrouped.keySet()) {
            sum += recipientGrouped.get(type).size();
        }

        sum += predefined.size();
        return sum;
    }
}
