package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ModelPackage {

    @SerializedName("packageRequest")
    Byte packageRequest;

    @SerializedName("requestDate")
    Date requestDate;

    @SerializedName("fromDeliver")
    Date FromDeliver;

    @SerializedName("toDeliver")
    Date ToDeliver;

    public Byte getPackageRequest() {
        return packageRequest;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setPackageRequest(Byte packageRequest) {
        this.packageRequest = packageRequest;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getFromDeliver() {
        return FromDeliver;
    }

    public void setFromDeliver(Date fromDeliver) {
        FromDeliver = fromDeliver;
    }

    public Date getToDeliver() {
        return ToDeliver;
    }

    public void setToDeliver(Date toDeliver) {
        ToDeliver = toDeliver;
    }
}
