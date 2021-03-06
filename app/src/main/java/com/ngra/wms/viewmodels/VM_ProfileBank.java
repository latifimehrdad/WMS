package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.ModelUserAccounts;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.activities.MainActivity;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfileBank extends VM_Primary {

    private ModelUserAccounts.ModelUserAccountNumber modelUserAccountNumber;
    private ArrayList<MD_SpinnerItem> banks;
    private String accountNumber;
    private String bankId;


    //______________________________________________________________________________________________ VM_ProFileBank
    public VM_ProfileBank(Activity context) {
        setContext(context);
        setAccountNumber("IR");
    }
    //______________________________________________________________________________________________ VM_ProFileBank


    //______________________________________________________________________________________________ sendAccountNumber
    public void sendAccountNumber() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendAccountNumber(
                        getBankId(),
                        getAccountNumber(),
                        RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    MainActivity.completeProfile = true;
                    sendActionToObservable(StaticValues.ML_EditProfile);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ sendAccountNumber


    //______________________________________________________________________________________________ getUserAccountNumber
    public void getUserAccountNumber() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserAccountNumber(RetrofitApis.app_token,aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelUserAccounts>() {
            @Override
            public void onResponse(Call<ModelUserAccounts> call, Response<ModelUserAccounts> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    if (response.body().getResult() == null)
                        sendActionToObservable(StaticValues.ML_GetAccountNumberNull);
                    else {
                        modelUserAccountNumber = response.body().getResult();
                        sendActionToObservable(StaticValues.ML_GetAccountNumbers);
                    }
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelUserAccounts> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getUserAccountNumber


    //______________________________________________________________________________________________ getBankList
    public void getBankList() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBanks(RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_SpinnerItems>() {
            @Override
            public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    banks = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetBanks);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getBankList


    //______________________________________________________________________________________________ getAccountNumbers
    public ModelUserAccounts.ModelUserAccountNumber getModelUserAccountNumber() {
        return modelUserAccountNumber;
    }
    //______________________________________________________________________________________________ getAccountNumbers


    //______________________________________________________________________________________________ getBanks
    public ArrayList<MD_SpinnerItem> getBanks() {
        return banks;
    }
    //______________________________________________________________________________________________ getBanks


    //______________________________________________________________________________________________ getAccountNumber
    public String getAccountNumber() {
        return accountNumber;
    }
    //______________________________________________________________________________________________ getAccountNumber


    //______________________________________________________________________________________________ setAccountNumber
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    //______________________________________________________________________________________________ setAccountNumber


    //______________________________________________________________________________________________ getBankId
    public String getBankId() {
        return bankId;
    }
    //______________________________________________________________________________________________ getBankId


    //______________________________________________________________________________________________ setBankId
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
    //______________________________________________________________________________________________ setBankId

}
