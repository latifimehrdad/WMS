package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_ScoreList {

    @SerializedName("normals")
    List<MD_GiveScoreItem> normals;

    @SerializedName("configs")
    List<MD_ScoreListConfig> configs;

    public MD_ScoreList(List<MD_GiveScoreItem> normals, List<MD_ScoreListConfig> configs) {
        this.normals = normals;
        this.configs = configs;
    }

    public List<MD_GiveScoreItem> getNormals() {
        return normals;
    }

    public void setNormals(List<MD_GiveScoreItem> normals) {
        this.normals = normals;
    }

    public List<MD_ScoreListConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<MD_ScoreListConfig> configs) {
        this.configs = configs;
    }
}
