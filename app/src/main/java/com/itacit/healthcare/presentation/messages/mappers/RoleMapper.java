package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.Role;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.RoleModel;

/**
 * Created by root on 18.11.15.
 */
public class RoleMapper extends ModelMapper<RoleModel, Role> {

	@Override
	public RoleModel transform(Role dataEntry) {
		if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
			return null;
		}

		RoleModel model = new RoleModel();
		model.setId(dataEntry.getId());
		model.setTitle(dataEntry.getName());
		return model;
	}
}
