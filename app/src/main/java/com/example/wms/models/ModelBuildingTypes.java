package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelBuildingTypes {

    @SerializedName("buildingTypes")
    List<ModelSpinnerItem> buildingTypes;

    @SerializedName("buildingUses")
    List<ModelSpinnerItem> buildingUses;

    public List<ModelSpinnerItem> getBuildingTypes() {
        return buildingTypes;
    }

    public List<ModelSpinnerItem> getBuildingUses() {
        return buildingUses;
    }
}
