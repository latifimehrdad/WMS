package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelRegisterCitizen {


    @SerializedName("messages")
    private ArrayList<ModelMessage> messages;

    public ArrayList<ModelMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ModelMessage> messages) {
        this.messages = messages;
    }
}
