package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_BoothList extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_Booth> result;

    public MR_BoothList(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_Booth> getResult() {
        return result;
    }

    public void setResult(List<MD_Booth> result) {
        this.result = result;
    }
}
