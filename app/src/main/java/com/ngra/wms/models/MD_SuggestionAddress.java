package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_SuggestionAddress {

    @SerializedName("place_id")
    Integer place_id;

    @SerializedName("boundingbox")
    List<Double> boundingbox;

    @SerializedName("lat")
    Double lat;

    @SerializedName("lon")
    Double lon;

    @SerializedName("display_name")
    String display_name;

    @SerializedName("address")
    MD_Address address;


    String totalAddress;

    public MD_SuggestionAddress(Integer place_id, List<Double> boundingbox, Double lat, Double lon, String display_name, MD_Address address, String totalAddress) {
        this.place_id = place_id;
        this.boundingbox = boundingbox;
        this.lat = lat;
        this.lon = lon;
        this.display_name = display_name;
        this.address = address;
        this.totalAddress = totalAddress;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public List<Double> getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(List<Double> boundingbox) {
        this.boundingbox = boundingbox;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public MD_Address getAddress() {
        return address;
    }

    public void setAddress(MD_Address address) {
        this.address = address;
    }

    public String getTotalAddress() {
        return totalAddress;
    }

    public void setTotalAddress(String totalAddress) {
        this.totalAddress = totalAddress;
    }
}
