package com.itacit.healthcare.data.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class NewsListResponse {
    @SerializedName("responseRows")
    @Expose
    private ArrayList<News> responseRows;

    public ArrayList<News> getResponseRows() {
        return responseRows;
    }

    public void setResponseRows(ArrayList<News> responseRows) {
        this.responseRows = responseRows;
    }
}
