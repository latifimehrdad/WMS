package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_ScoreReport extends ModelResponsePrimary {

    @SerializedName("result")
    MD_ScoreReport result;

    public MR_ScoreReport(ArrayList<ModelMessage> messages, MD_ScoreReport result) {
        super(messages);
        this.result = result;
    }

    public MD_ScoreReport getResult() {
        return result;
    }

    public void setResult(MD_ScoreReport result) {
        this.result = result;
    }
}
