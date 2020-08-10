package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_ScoreList2 extends ModelResponsePrimary {

    @SerializedName("result")
    MD_ScoreList2 result;

    public MR_ScoreList2(ArrayList<ModelMessage> messages, MD_ScoreList2 result) {
        super(messages);
        this.result = result;
    }

    public MD_ScoreList2 getResult() {
        return result;
    }
}
