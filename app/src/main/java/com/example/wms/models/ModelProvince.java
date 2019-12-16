package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelProvince {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    public ModelProvince() {
    }

    public ModelProvince(String id, String title) {
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
