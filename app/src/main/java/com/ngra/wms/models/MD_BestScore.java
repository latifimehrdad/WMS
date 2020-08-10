package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_BestScore {

    @SerializedName("firstName")
    String firstName;

    @SerializedName("lastName")
    String lastName;

    @SerializedName("cityName")
    String cityName;

    @SerializedName("point")
    double point;

    @SerializedName("monthName")
    String monthName;

    @SerializedName("month")
    String month;

    @SerializedName("year")
    String year;

    @SerializedName("fullName")
    String fullName;

    public MD_BestScore(String firstName, String lastName, String cityName, double point, String monthName, String month, String year, String fullName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityName = cityName;
        this.point = point;
        this.monthName = monthName;
        this.month = month;
        this.year = year;
        this.fullName = fullName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
