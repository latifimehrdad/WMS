package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MD_RequestHi extends ModelResponsePrimary {

    @SerializedName("result")
    MD_Hi result;


    public MD_RequestHi(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public MD_Hi getResult() {
        return result;
    }

    public void setResult(MD_Hi result) {
        this.result = result;
    }
}
