package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_UserScoreInfoList extends ModelResponsePrimary {

    @SerializedName("result")
    MD_UserScoreInfoList result;

    public MR_UserScoreInfoList(ArrayList<ModelMessage> messages, MD_UserScoreInfoList result) {
        super(messages);
        this.result = result;
    }

    public MD_UserScoreInfoList getResult() {
        return result;
    }

    public void setResult(MD_UserScoreInfoList result) {
        this.result = result;
    }
}
