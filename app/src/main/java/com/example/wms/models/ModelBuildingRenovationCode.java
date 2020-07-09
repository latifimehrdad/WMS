package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelBuildingRenovationCode extends ModelResponsePrimary {

    @SerializedName("result")
    String result;

    public ModelBuildingRenovationCode(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
