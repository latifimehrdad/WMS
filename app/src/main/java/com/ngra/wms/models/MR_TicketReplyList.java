package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_TicketReplyList extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_TicketReplyDto> result;

    public MR_TicketReplyList(ArrayList<ModelMessage> messages, List<MD_TicketReplyDto> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_TicketReplyDto> getResult() {
        return result;
    }

    public void setResult(List<MD_TicketReplyDto> result) {
        this.result = result;
    }
}
