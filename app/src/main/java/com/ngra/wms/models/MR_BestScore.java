package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_BestScore extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_BestScore> result;

    public MR_BestScore(ArrayList<ModelMessage> messages, List<MD_BestScore> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_BestScore> getResult() {
        return result;
    }

    public void setResult(List<MD_BestScore> result) {
        this.result = result;
    }


}
