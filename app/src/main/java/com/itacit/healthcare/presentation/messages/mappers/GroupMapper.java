package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.Group;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;

/**
 * Created by root on 16.11.15.
 */
public class GroupMapper extends ModelMapper<RecipientModel, Group> {
    @Override
    public RecipientModel transform(Group dataEntry) {
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        RecipientModel model = new RecipientModel();
        model.setId(dataEntry.getId());
        model.setName(dataEntry.getName());
        return model;
    }
}
