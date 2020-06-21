package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelTimeSheetTimes extends ModelResponcePrimery {

    @SerializedName("result")
    ModelTimes result;

    public ModelTimes getResult() {
        return result;
    }
}
