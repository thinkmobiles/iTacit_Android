package com.itacit.healthcare.presentation.messages.mappers;

import android.net.Uri;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.UserModel;

/**
 * Created by root on 12.11.15.
 */
public class UserMapper extends ModelMapper<UserModel, User> {

	@Override
	public UserModel transform(User dataEntry) {
		UserModel userModel = new UserModel();
		if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
			return null;
		}
		userModel.setId(dataEntry.getId());
		userModel.setFirstName(dataEntry.getNameFirst() != null ? dataEntry.getNameFirst() : "");
		userModel.setLastName(dataEntry.getNameLast() != null ? dataEntry.getNameLast() : "");
		userModel.setFullName(dataEntry.getNameFull() != null ? dataEntry.getNameFull() : "");
		userModel.setRole(dataEntry.getRoleName() != null ? dataEntry.getRoleName() : "");
		userModel.setJobName(dataEntry.getJobName() != null ? dataEntry.getJobName() : "");
		userModel.setBusinessName(dataEntry.getBusinessName() != null ? dataEntry.getBusinessName() : "");
		userModel.setEmail(dataEntry.getEmail() != null ? dataEntry.getEmail() : "");
		userModel.setPhoneNumber(dataEntry.getPhoneNumber() != null ? dataEntry.getPhoneNumber() : "");
		userModel.setImageUri(Uri.parse(dataEntry.getImageUrl() != null ? dataEntry.getImageUrl() : ""));
		return userModel;
	}
}
