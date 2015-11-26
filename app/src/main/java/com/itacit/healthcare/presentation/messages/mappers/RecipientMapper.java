package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.Recipient;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.UserRecipientModel;

/**
 * Created by root on 26.11.15.
 */
public class RecipientMapper extends ModelMapper<UserRecipientModel, Recipient> {
    @Override
    public UserRecipientModel transform(Recipient dataEntry) {
        UserRecipientModel recipientModel = new UserRecipientModel();
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        recipientModel.setId(dataEntry.getId());
        recipientModel.setFullName(convertString(dataEntry.getNameFull()));
        recipientModel.setRole(convertString(dataEntry.getRoleName()));
        recipientModel.setImageUri(convertUri(dataEntry.getImageUrl()));
        return recipientModel;
    }
}
