package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_Booth {

    @SerializedName("id")
    Integer Id;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("author")
    String author;

    @SerializedName("location")
    MD_Location location;

    @SerializedName("phoneNumber")
    String phoneNumber;


    public MD_Booth(Integer id, String name, String address, String author, MD_Location location, String phoneNumber) {
        Id = id;
        this.name = name;
        this.address = address;
        this.author = author;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public MD_Booth(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MD_Location getLocation() {
        return location;
    }

    public void setLocation(MD_Location location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
