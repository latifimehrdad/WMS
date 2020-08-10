package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_ScoreList2 {

    @SerializedName("vehicle")
    List<String> vehicle;

    @SerializedName("booth")
    List<String> booth;

    public MD_ScoreList2(List<String> vehicle, List<String> booth) {
        this.vehicle = vehicle;
        this.booth = booth;
    }

    public List<String> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<String> vehicle) {
        this.vehicle = vehicle;
    }

    public List<String> getBooth() {
        return booth;
    }

    public void setBooth(List<String> booth) {
        this.booth = booth;
    }
}
