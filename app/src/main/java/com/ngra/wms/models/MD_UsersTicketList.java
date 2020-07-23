package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MD_UsersTicketList {

    @SerializedName("id")
    Integer id;

    @SerializedName("ticketCategoryRef")
    Long ticketCategoryRef;

    @SerializedName("ticketDepartmentRef")
    Long ticketDepartmentRef;

    @SerializedName("requesterUserRef")
    String requesterUserRef;

    @SerializedName("body")
    String body;

    @SerializedName("subject")
    String subject;

    @SerializedName("status")
    Byte status;



    public MD_UsersTicketList(ArrayList<ModelMessage> messages, Integer id, Long ticketCategoryRef, Long ticketDepartmentRef, String requesterUserRef, String body, String subject, Byte status) {
        this.id = id;
        this.ticketCategoryRef = ticketCategoryRef;
        this.ticketDepartmentRef = ticketDepartmentRef;
        this.requesterUserRef = requesterUserRef;
        this.body = body;
        this.subject = subject;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTicketCategoryRef() {
        return ticketCategoryRef;
    }

    public void setTicketCategoryRef(Long ticketCategoryRef) {
        this.ticketCategoryRef = ticketCategoryRef;
    }

    public Long getTicketDepartmentRef() {
        return ticketDepartmentRef;
    }

    public void setTicketDepartmentRef(Long ticketDepartmentRef) {
        this.ticketDepartmentRef = ticketDepartmentRef;
    }

    public String getRequesterUserRef() {
        return requesterUserRef;
    }

    public void setRequesterUserRef(String requesterUserRef) {
        this.requesterUserRef = requesterUserRef;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
