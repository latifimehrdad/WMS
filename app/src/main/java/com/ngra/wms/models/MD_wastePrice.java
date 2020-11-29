package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_wastePrice {

    @SerializedName("price")
    double price;

    @SerializedName("value")
    double value;

    public MD_wastePrice(double price, double value) {
        this.price = price;
        this.value = value;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
