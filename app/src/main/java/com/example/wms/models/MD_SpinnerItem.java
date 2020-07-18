package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_SpinnerItem {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    public MD_SpinnerItem() {
    }

    public MD_SpinnerItem(String id, String title) {
        this.id = id;
        this.title = title;
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
}
