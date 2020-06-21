package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ModelPackage {

    @SerializedName("packageRequest")
    Byte packageRequest;

    @SerializedName("requestDate")
    Date requestDate;

    public Byte getPackageRequest() {
        return packageRequest;
    }

    public Date getRequestDate() {
        return requestDate;
    }
}
