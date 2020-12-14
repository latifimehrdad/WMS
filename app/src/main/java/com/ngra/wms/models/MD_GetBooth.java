package com.ngra.wms.models;

public class MD_GetBooth {

    MD_Location location;

    String app_token;

    public MD_GetBooth(MD_Location location, String app_token) {
        this.location = location;
        this.app_token = app_token;
    }

    public MD_Location getLocation() {
        return location;
    }

    public void setLocation(MD_Location location) {
        this.location = location;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }
}
