package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelBuildingRenovationCode extends ModelResponsePrimary {

    @SerializedName("result")
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
