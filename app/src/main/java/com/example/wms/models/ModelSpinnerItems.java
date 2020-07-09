package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelSpinnerItems extends ModelResponsePrimary {

    @SerializedName("result")
    ArrayList<ModelSpinnerItem> result;

    public ModelSpinnerItems(ArrayList<ModelMessage> messages) {
        super(messages);
    }


    public ArrayList<ModelSpinnerItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<ModelSpinnerItem> result) {
        this.result = result;
    }
}
