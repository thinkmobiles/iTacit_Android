package com.itacit.healthcare.presentation.messages.models;

import android.net.Uri;

import com.itacit.healthcare.presentation.base.model.BaseModel;

/**
 * Created by root on 23.11.15.
 */
public class UserRecipientModel extends BaseModel {
    private String parent;
    private String role;
    private String fullName;
    private Uri imageUri;
    private String business;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roleName) {
        this.role = roleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String businessUnitName) {
        this.business = businessUnitName;
    }
}
