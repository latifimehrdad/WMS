package com.example.wms.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MD_GiveScoreItem {

    @SerializedName("id")
    Integer id;

    @SerializedName("title")
    String title;

    @SerializedName("value")
    float value;

    @SerializedName("description")
    @Expose
    String description;

    public MD_GiveScoreItem(Integer id, String title, float value, String description) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
