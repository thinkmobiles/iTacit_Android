package com.itacit.healthcare.presentation.news.mappers;

import android.net.Uri;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;

/**
 * Created by root on 02.11.15.
 */
public class AuthorMapper extends ModelMapper<AuthorModel, Author> {

	@Override
	public AuthorModel transform(Author dataEntry) {

		AuthorModel authorModel = new AuthorModel();
		if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
			return null;
		}
		authorModel.setId(dataEntry.getId());

		authorModel.setFullName(dataEntry.getFullName() != null ? dataEntry.getFullName() : "");
		authorModel.setRole(dataEntry.getRole() != null ? dataEntry.getRole() : "");
		authorModel.setImageUri(Uri.parse(dataEntry.getImageUrl() != null ? dataEntry.getImageUrl() : ""));
		return authorModel;
	}
}
