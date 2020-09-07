package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_userFund {

    @SerializedName("credit")
    double credit;

    public MD_userFund(double credit) {
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
