package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_UserScorePriceReport extends ModelResponsePrimary {

    @SerializedName("result")
    MD_UserScorePriceReport result;

    public MR_UserScorePriceReport(ArrayList<ModelMessage> messages, MD_UserScorePriceReport result) {
        super(messages);
        this.result = result;
    }


    public MD_UserScorePriceReport getResult() {
        return result;
    }

    public void setResult(MD_UserScorePriceReport result) {
        this.result = result;
    }
}
