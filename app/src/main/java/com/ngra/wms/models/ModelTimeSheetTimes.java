package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelTimeSheetTimes extends ModelResponsePrimary {

    @SerializedName("result")
    ModelTimes result;

    public ModelTimeSheetTimes(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public ModelTimes getResult() {
        return result;
    }
}
