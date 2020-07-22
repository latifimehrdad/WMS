package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_ItemLearn extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ItemLearn> itemLearns;


    public MR_ItemLearn(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_ItemLearn> getItemLearns() {
        return itemLearns;
    }

    public void setItemLearns(List<MD_ItemLearn> itemLearns) {
        this.itemLearns = itemLearns;
    }
}
