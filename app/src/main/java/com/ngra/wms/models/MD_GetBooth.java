package com.ngra.wms.models;

public class MD_GetBooth {

    MD_Location location;

    public MD_GetBooth(MD_Location location) {
        this.location = location;
    }

    public MD_Location getLocation() {
        return location;
    }

    public void setLocation(MD_Location location) {
        this.location = location;
    }
}
