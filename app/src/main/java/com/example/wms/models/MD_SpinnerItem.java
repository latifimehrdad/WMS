package com.example.wms.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MD_SpinnerItem {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("data")
    @Expose
    String data;

    public MD_SpinnerItem() {
    }

    public MD_SpinnerItem(String id, String title, String data) {
        this.id = id;
        this.title = title;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
