package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_userFundInfo {

    @SerializedName("info")
    MD_userFund info;

    @SerializedName("walletInfo")
    MD_WalletInfo walletInfo;

    @SerializedName("hasAccountNumber")
    boolean hasAccountNumber;


    public MD_userFundInfo(MD_userFund info, MD_WalletInfo walletInfo, boolean hasAccountNumber) {
        this.info = info;
        this.walletInfo = walletInfo;
        this.hasAccountNumber = hasAccountNumber;
    }

    public MD_userFund getInfo() {
        return info;
    }

    public void setInfo(MD_userFund info) {
        this.info = info;
    }

    public MD_WalletInfo getWalletInfo() {
        return walletInfo;
    }

    public void setWalletInfo(MD_WalletInfo walletInfo) {
        this.walletInfo = walletInfo;
    }

    public boolean isHasAccountNumber() {
        return hasAccountNumber;
    }

    public void setHasAccountNumber(boolean hasAccountNumber) {
        this.hasAccountNumber = hasAccountNumber;
    }
}
