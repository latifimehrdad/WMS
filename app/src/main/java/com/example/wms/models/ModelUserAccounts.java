package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelUserAccounts extends ModelResponsePrimary {

    @SerializedName("result")
    ModelUserAccountNumber result;


    public ModelUserAccountNumber getResult() {
        return result;
    }

    public void setResult(ModelUserAccountNumber result) {
        this.result = result;
    }

    public class ModelUserAccountNumber {

        @SerializedName("accountNumber")
        String accountNumber;

        @SerializedName("bank")
        ModelSpinnerItem bank;

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public ModelSpinnerItem getBank() {
            return bank;
        }

        public void setBank(ModelSpinnerItem bank) {
            this.bank = bank;
        }
    }
}
