package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_LotteryNotification {

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("cover")
    String cover;


    public MD_LotteryNotification(String title, String description, String cover) {
        this.title = title;
        this.description = description;
        this.cover = cover;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
