package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.Group;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.GroupModel;

/**
 * Created by root on 16.11.15.
 */
public class GroupMapper extends ModelMapper<GroupModel, Group> {
    @Override
    public GroupModel transform(Group dataEntry) {
        if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
            return null;
        }

        GroupModel model = new GroupModel();
        model.setId(dataEntry.getId());
        model.setTitle(dataEntry.getName());
        return model;
    }
}
