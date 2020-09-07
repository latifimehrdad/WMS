package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_accountFundRequests extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_accountFundRequests> result;

    public MR_accountFundRequests(ArrayList<ModelMessage> messages, List<MD_accountFundRequests> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_accountFundRequests> getResult() {
        return result;
    }

    public void setResult(List<MD_accountFundRequests> result) {
        this.result = result;
    }
}
