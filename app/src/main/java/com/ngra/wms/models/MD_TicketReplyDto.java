package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MD_TicketReplyDto {

    @SerializedName("ticketReplyId")
    Long ticketReplyId;

    @SerializedName("ticketRef")
    Long ticketRef;

    @SerializedName("body")
    String body;

    @SerializedName("type")
    Byte type;

    @SerializedName("fullName")
    String fullName;

    @SerializedName("avatarPath")
    String avatarPath;

    @SerializedName("createDate")
    Date createDate;


    public MD_TicketReplyDto(Long ticketReplyId, Long ticketRef, String body, Byte type, String fullName, String avatarPath, Date createDate) {
        this.ticketReplyId = ticketReplyId;
        this.ticketRef = ticketRef;
        this.body = body;
        this.type = type;
        this.fullName = fullName;
        this.avatarPath = avatarPath;
        this.createDate = createDate;
    }

    public Long getTicketReplyId() {
        return ticketReplyId;
    }

    public void setTicketReplyId(Long ticketReplyId) {
        this.ticketReplyId = ticketReplyId;
    }

    public Long getTicketRef() {
        return ticketRef;
    }

    public void setTicketRef(Long ticketRef) {
        this.ticketRef = ticketRef;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
