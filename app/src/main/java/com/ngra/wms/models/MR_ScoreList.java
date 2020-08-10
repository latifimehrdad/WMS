package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_ScoreList extends ModelResponsePrimary {


    @SerializedName("result")
    MD_ScoreList result;

    public MR_ScoreList(ArrayList<ModelMessage> messages, MD_ScoreList result) {
        super(messages);
        this.result = result;
    }

    public MD_ScoreList getResult() {
        return result;
    }

    public void setResult(MD_ScoreList result) {
        this.result = result;
    }
}
