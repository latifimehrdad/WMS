package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_TicketList extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_UsersTicketList> result;

    public MR_TicketList(ArrayList<ModelMessage> messages, List<MD_UsersTicketList> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_UsersTicketList> getResult() {
        return result;
    }

    public void setResult(List<MD_UsersTicketList> result) {
        this.result = result;
    }
}
