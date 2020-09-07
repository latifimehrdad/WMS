package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_userFundInfo extends ModelResponsePrimary{

    @SerializedName("result")
    MD_userFundInfo result;

    public MR_userFundInfo(ArrayList<ModelMessage> messages, MD_userFundInfo result) {
        super(messages);
        this.result = result;
    }

    public MD_userFundInfo getResult() {
        return result;
    }

    public void setResult(MD_userFundInfo result) {
        this.result = result;
    }
}
