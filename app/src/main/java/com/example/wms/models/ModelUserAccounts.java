package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelUserAccounts extends ModelResponsePrimary {

    @SerializedName("result")
    ModelUserAccountNumber result;

    public ModelUserAccounts(ArrayList<ModelMessage> messages) {
        super(messages);
    }


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
        MD_SpinnerItem bank;

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public MD_SpinnerItem getBank() {
            return bank;
        }

        public void setBank(MD_SpinnerItem bank) {
            this.bank = bank;
        }
    }
}
