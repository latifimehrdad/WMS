package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelResponsePrimery {

    @SerializedName("messages")
    ArrayList<ModelMessage> messages;

    @SerializedName("result")
    ArrayList<Object> list;

    public ArrayList<ModelMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ModelMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<Object> getList() {
        return list;
    }

    public void setList(ArrayList<Object> list) {
        this.list = list;
    }
}
