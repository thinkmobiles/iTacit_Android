package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.JobClassification;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.JobModel;

/**
 * Created by root on 16.11.15.
 */
public class JobMapper extends ModelMapper<JobModel, JobClassification> {
    @Override
    public JobModel transform(JobClassification dataEntry) {
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        JobModel model = new JobModel();
        model.setId(dataEntry.getId());
        model.setTitle(dataEntry.getName());
        return model;
    }
}
