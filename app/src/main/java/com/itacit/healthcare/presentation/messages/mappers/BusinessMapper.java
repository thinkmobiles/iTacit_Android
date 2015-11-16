package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.Business;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.BusinessModel;

/**
 * Created by root on 16.11.15.
 */
public class BusinessMapper extends ModelMapper<BusinessModel, Business> {
    @Override
    public BusinessModel transform(Business dataEntry) {
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        BusinessModel model = new BusinessModel();
        model.setId(dataEntry.getId());
        model.setTitle(dataEntry.getNameFull());
        return model;
    }
}
