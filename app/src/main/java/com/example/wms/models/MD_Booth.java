package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_Booth {

    @SerializedName("id")
    Integer id;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("author")
    String author;

    @SerializedName("location")
    MD_Location location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
