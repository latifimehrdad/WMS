package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_ScoreListConfig {

    @SerializedName("title")
    String title;

    @SerializedName("items")
    List<MD_ScoreListItem> items;

    public MD_ScoreListConfig(String title, List<MD_ScoreListItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MD_ScoreListItem> getItems() {
        return items;
    }

    public void setItems(List<MD_ScoreListItem> items) {
        this.items = items;
    }


}
