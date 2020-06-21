package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelHousingBuildings  extends ModelResponcePrimery {

    @SerializedName("result")
    ModelBuildingTypes result;

    public ModelBuildingTypes getResult() {
        return result;
    }
}
