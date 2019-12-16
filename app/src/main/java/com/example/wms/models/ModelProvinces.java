package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelProvinces {

    @SerializedName("messages")
    ArrayList<ModelMessage> messages;

    @SerializedName("result")
    ArrayList<ModelProvince> result;


    public ArrayList<ModelMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ModelMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<ModelProvince> getResult() {
        return result;
    }

    public void setResult(ArrayList<ModelProvince> result) {
        this.result = result;
    }
}
