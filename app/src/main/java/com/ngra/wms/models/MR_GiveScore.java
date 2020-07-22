package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_GiveScore extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_GiveScoreItem> result;


    public MR_GiveScore(ArrayList<ModelMessage> messages, List<MD_GiveScoreItem> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_GiveScoreItem> getResult() {
        return result;
    }

    public void setResult(List<MD_GiveScoreItem> result) {
        this.result = result;
    }
}
