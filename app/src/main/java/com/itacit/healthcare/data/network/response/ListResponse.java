package com.itacit.healthcare.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class ListResponse<T> {
    @SerializedName("responseRows")
    @Expose
    private ArrayList<T> responseRows;

    public ArrayList<T> getResponseRows() {
        return responseRows;
    }

    public void setResponseRows(ArrayList<T> responseRows) {
        this.responseRows = responseRows;
    }
}
