package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelHousingBuildings  extends ModelResponsePrimary {

    @SerializedName("result")
    ModelBuildingTypes result;

    public ModelHousingBuildings(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public ModelBuildingTypes getResult() {
        return result;
    }
}
