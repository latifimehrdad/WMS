package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;
import com.ngra.wms.viewmodels.VM_Primary;

import java.util.ArrayList;

public class MR_GameReport extends ModelResponsePrimary {

    @SerializedName("result")
    MD_GameReport result;

    public MR_GameReport(ArrayList<ModelMessage> messages, MD_GameReport result) {
        super(messages);
        this.result = result;
    }

    public MD_GameReport getResult() {
        return result;
    }

    public void setResult(MD_GameReport result) {
        this.result = result;
    }
}
