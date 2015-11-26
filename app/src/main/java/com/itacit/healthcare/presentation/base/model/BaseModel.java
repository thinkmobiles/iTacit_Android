package com.itacit.healthcare.presentation.base.model;

/**
 * Created by root on 26.11.15.
 */
public class BaseModel {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseModel) {
            BaseModel model = (BaseModel) o;
            return id.equals(model.getId());
        }

        return false;
    }
}
