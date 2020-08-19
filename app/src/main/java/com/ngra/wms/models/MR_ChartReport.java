package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_ChartReport extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ChartReport> result;


    public MR_ChartReport(ArrayList<ModelMessage> messages, List<MD_ChartReport> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_ChartReport> getResult() {
        return result;
    }

    public void setResult(List<MD_ChartReport> result) {
        this.result = result;
    }
}
