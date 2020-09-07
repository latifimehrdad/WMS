package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_WalletInfo {

    @SerializedName("minWithdrawal")
    double minWithdrawal;

    @SerializedName("maxWithdrawal")
    double maxWithdrawal;

    @SerializedName("settlePaymentInDays")
    Integer settlePaymentInDays;

    public MD_WalletInfo(double minWithdrawal, double maxWithdrawal, Integer settlePaymentInDays) {
        this.minWithdrawal = minWithdrawal;
        this.maxWithdrawal = maxWithdrawal;
        this.settlePaymentInDays = settlePaymentInDays;
    }

    public double getMinWithdrawal() {
        return minWithdrawal;
    }

    public void setMinWithdrawal(double minWithdrawal) {
        this.minWithdrawal = minWithdrawal;
    }

    public double getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(double maxWithdrawal) {
        this.maxWithdrawal = maxWithdrawal;
    }

    public Integer getSettlePaymentInDays() {
        return settlePaymentInDays;
    }

    public void setSettlePaymentInDays(Integer settlePaymentInDays) {
        this.settlePaymentInDays = settlePaymentInDays;
    }
}
