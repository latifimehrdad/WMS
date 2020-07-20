package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelBuildingTypes {

    @SerializedName("buildingTypes")
    List<MD_SpinnerItem> buildingTypes;

    @SerializedName("buildingUses")
    List<MD_SpinnerItem> buildingUses;

    public List<MD_SpinnerItem> getBuildingTypes() {
        return buildingTypes;
    }

    public List<MD_SpinnerItem> getBuildingUses() {
        return buildingUses;
    }
}
