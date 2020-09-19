package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_userFund;
import com.ngra.wms.models.MD_userFundInfo;
import com.ngra.wms.models.MR_userFundInfo;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_NewWallet extends VM_Primary {

    private MD_userFundInfo md_userFund;
    private String amount;


    //______________________________________________________________________________________________ VM_NewWallet
    public VM_NewWallet(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_NewWallet


    //______________________________________________________________________________________________ getUserFundInfo
    public void getUserFundInfo() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserFundInfo(Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_userFundInfo>() {
            @Override
            public void onResponse(Call<MR_userFundInfo> call, Response<MR_userFundInfo> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_userFund = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetUserScorePriceReport);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_userFundInfo> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getUserFundInfo


    //______________________________________________________________________________________________ settlementDemand
    public void settlementDemand() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        Integer value = Integer.valueOf(getAmount().replaceAll(",", ""));

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .settlementDemand(value, Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_Success);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ settlementDemand


    //______________________________________________________________________________________________ getMd_userFundInfo
    public MD_userFundInfo getMd_userFundInfo() {
        return md_userFund;
    }
    //______________________________________________________________________________________________ getMd_userFundInfo



    //______________________________________________________________________________________________ getMd_userFundInfo
    public String getAmount() {
        return amount;
    }
    //______________________________________________________________________________________________ getMd_userFundInfo



    //______________________________________________________________________________________________ getMd_userFundInfo
    public void setAmount(String amount) {
        this.amount = amount;
    }
    //______________________________________________________________________________________________ getMd_userFundInfo



}
