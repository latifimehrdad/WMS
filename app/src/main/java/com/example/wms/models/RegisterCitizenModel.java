package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterCitizenModel {

    @SerializedName("status")
    private Integer status;

    @SerializedName("messages")
    private ArrayList<MessageModel> messages;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageModel> messages) {
        this.messages = messages;
    }
}
