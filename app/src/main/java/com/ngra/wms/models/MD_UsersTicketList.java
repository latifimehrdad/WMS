package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MD_UsersTicketList {

    @SerializedName("id")
    Long id;

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

    @SerializedName("replies")
    List<MD_TicketReplyDto> replies;


    public MD_UsersTicketList(ArrayList<ModelMessage> messages, Long id, Long ticketCategoryRef, Long ticketDepartmentRef, String requesterUserRef, String body, String subject, Byte status, List<MD_TicketReplyDto> replies) {
        this.id = id;
        this.ticketCategoryRef = ticketCategoryRef;
        this.ticketDepartmentRef = ticketDepartmentRef;
        this.requesterUserRef = requesterUserRef;
        this.body = body;
        this.subject = subject;
        this.status = status;
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<MD_TicketReplyDto> getReplies() {
        return replies;
    }

    public void setReplies(List<MD_TicketReplyDto> replies) {
        this.replies = replies;
    }
}
