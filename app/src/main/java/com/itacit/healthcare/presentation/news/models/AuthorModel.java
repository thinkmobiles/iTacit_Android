package com.itacit.healthcare.presentation.news.models;

import android.net.Uri;

import com.itacit.healthcare.presentation.base.model.BaseModel;

/**
 * Created by root on 02.11.15.
 */
public class AuthorModel extends BaseModel {

	private String fullName;
	private String role;
	private Uri imageUri;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Uri getImageUri() {
		return imageUri;
	}

	public void setImageUri(Uri imageUri) {
		this.imageUri = imageUri;
	}
}
