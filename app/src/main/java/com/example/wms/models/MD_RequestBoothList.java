package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MD_RequestBoothList extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_Booth> result;

    public MD_RequestBoothList(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_Booth> getResult() {
        return result;
    }

    public void setResult(List<MD_Booth> result) {
        this.result = result;
    }
}
