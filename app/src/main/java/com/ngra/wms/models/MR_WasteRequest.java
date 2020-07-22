package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_WasteRequest extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ItemWasteRequest> result;

    public MR_WasteRequest(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_ItemWasteRequest> getResult() {
        return result;
    }

    public void setResult(List<MD_ItemWasteRequest> result) {
        this.result = result;
    }
}
