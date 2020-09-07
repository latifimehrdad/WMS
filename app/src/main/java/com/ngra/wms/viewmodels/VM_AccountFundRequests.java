package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_accountFundRequests;
import com.ngra.wms.models.MR_accountFundRequests;
import com.ngra.wms.models.MR_userFundInfo;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_AccountFundRequests extends VM_Primary {


    private List<MD_accountFundRequests> md_accountFundRequests;


    //______________________________________________________________________________________________ VM_AccountFundRequests
    public VM_AccountFundRequests(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_AccountFundRequests


    //______________________________________________________________________________________________ getAccountFundRequests
    public void getAccountFundRequests() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAccountFundRequests(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_accountFundRequests>() {
            @Override
            public void onResponse(Call<MR_accountFundRequests> call, Response<MR_accountFundRequests> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_accountFundRequests = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_AccountFundRequest);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_accountFundRequests> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getAccountFundRequests


    //______________________________________________________________________________________________ getAccountFundRequests
    public List<MD_accountFundRequests> getMd_accountFundRequests() {
        return md_accountFundRequests;
    }
    //______________________________________________________________________________________________ getAccountFundRequests

}
