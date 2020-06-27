package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelTimeSheetTimes extends ModelResponsePrimary {

    @SerializedName("result")
    ModelTimes result;

    public ModelTimes getResult() {
        return result;
    }
}
