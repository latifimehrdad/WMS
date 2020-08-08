package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_TimeSheet extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_TimeSheet> result;

    public MR_TimeSheet(ArrayList<ModelMessage> messages, List<MD_TimeSheet> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_TimeSheet> getResult() {
        return result;
    }

    public void setResult(List<MD_TimeSheet> result) {
        this.result = result;
    }
}
