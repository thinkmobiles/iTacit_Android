package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.Role;
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;

/**
 * Created by root on 18.11.15.
 */
public class RoleMapper extends ModelMapper<RecipientModel, Role> {

	@Override
	public RecipientModel transform(Role dataEntry) {
		if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
			return null;
		}

		RecipientModel model = new RecipientModel();
		model.setId(dataEntry.getId());
		model.setName(dataEntry.getName());
		return model;
	}
}
