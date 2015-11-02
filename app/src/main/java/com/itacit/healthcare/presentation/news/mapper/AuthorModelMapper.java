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

		long id = dataEntry.getId() != null ? Long.parseLong(dataEntry.getId()) : 0;
		authorModel.setId(id);
		authorModel.setFullName(dataEntry.getFullName());
		authorModel.setRole(dataEntry.getRole());
		authorModel.setImageUri(Uri.parse(dataEntry.getImageUrl()));

		return authorModel;
	}
}
