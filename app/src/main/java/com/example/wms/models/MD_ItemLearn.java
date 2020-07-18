package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_ItemLearn {

    @SerializedName("id")
    Integer id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("color")
    String color;

    @SerializedName("noticeType")
    Byte noticeType;

    public MD_ItemLearn(Integer id, String title, String description, String color, Byte noticeType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.noticeType = noticeType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Byte getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Byte noticeType) {
        this.noticeType = noticeType;
    }
}
