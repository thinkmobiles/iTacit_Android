package com.itacit.healthcare.domain.models;

import com.itacit.healthcare.presentation.base.model.BaseModel;

/**
 * Created by root on 16.11.15.
 */
public class RecipientModel extends BaseModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
