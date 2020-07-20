package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelTimes {

    @SerializedName("times")
    List<ModelTime> times;

    public List<ModelTime> getTimes() {
        return times;
    }
}
