package com.itacit.healthcare.presentation.news.mapper;

import android.net.Uri;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.presentation.base.ModelMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;

/**
 * Created by root on 02.11.15.
 */
public class AuthorModelMapper extends ModelMapper<AuthorModel, Author> {

	@Override
	public AuthorModel transform(Author dataEntry) {

		AuthorModel authorModel = new AuthorModel();
		try {
			long id = dataEntry.getId() != null ? Long.parseLong(dataEntry.getId()) : 0;
			if (id == 0) return null;
			authorModel.setId(id);

			authorModel.setFullName(dataEntry.getFullName() != null ? dataEntry.getFullName() : "");
			authorModel.setRole(dataEntry.getRole() != null ? dataEntry.getRole() : "");
			authorModel.setImageUri(Uri.parse(dataEntry.getImageUrl()  != null ? dataEntry.getImageUrl() : ""));
			return authorModel;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
}
