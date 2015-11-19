package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.JobClassification;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;

/**
 * Created by root on 16.11.15.
 */
public class JobMapper extends ModelMapper<RecipientModel, JobClassification> {
    @Override
    public RecipientModel transform(JobClassification dataEntry) {
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        RecipientModel model = new RecipientModel();
        model.setId(dataEntry.getId());
        model.setName(dataEntry.getName());
        return model;
    }
}
