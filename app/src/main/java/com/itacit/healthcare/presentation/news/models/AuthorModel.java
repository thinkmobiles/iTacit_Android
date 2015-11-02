package com.itacit.healthcare.presentation.news.models;

import android.net.Uri;

/**
 * Created by root on 02.11.15.
 */
public class AuthorModel {

	private long id;
	private String fullName;
	private String role;
	private Uri imageUri;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
