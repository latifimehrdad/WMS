package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_LotteryNotification extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_LotteryNotification> result;

    public MR_LotteryNotification(ArrayList<ModelMessage> messages, List<MD_LotteryNotification> result) {
        super(messages);
        this.result = result;
    }


    public List<MD_LotteryNotification> getResult() {
        return result;
    }

    public void setResult(List<MD_LotteryNotification> result) {
        this.result = result;
    }
}
