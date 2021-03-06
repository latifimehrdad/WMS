package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelResponsePrimary {

    @SerializedName("messages")
    ArrayList<ModelMessage> messages;

    public ModelResponsePrimary(ArrayList<ModelMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<ModelMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ModelMessage> messages) {
        this.messages = messages;
    }
}
