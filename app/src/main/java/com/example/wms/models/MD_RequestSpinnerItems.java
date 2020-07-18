package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MD_RequestSpinnerItems extends ModelResponsePrimary {

    @SerializedName("result")
    ArrayList<MD_SpinnerItem> result;

    public MD_RequestSpinnerItems(ArrayList<ModelMessage> messages) {
        super(messages);
    }


    public ArrayList<MD_SpinnerItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<MD_SpinnerItem> result) {
        this.result = result;
    }
}
