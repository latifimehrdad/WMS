package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_UserScorePriceReport;
import com.ngra.wms.models.MR_ScoreList;
import com.ngra.wms.models.MR_UserScorePriceReport;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_NewWallet extends VM_Primary {

    private MD_UserScorePriceReport md_userScorePriceReport;


    //______________________________________________________________________________________________ VM_NewWallet
    public VM_NewWallet(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_NewWallet




    //______________________________________________________________________________________________ getUserScorePriceReport
    public void getUserScorePriceReport() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserScorePriceReport(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_UserScorePriceReport>() {
            @Override
            public void onResponse(Call<MR_UserScorePriceReport> call, Response<MR_UserScorePriceReport> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_userScorePriceReport = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetUserScorePriceReport);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_UserScorePriceReport> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getUserScorePriceReport



    //______________________________________________________________________________________________ getMd_userScorePriceReport
    public MD_UserScorePriceReport getMd_userScorePriceReport() {
        return md_userScorePriceReport;
    }
    //______________________________________________________________________________________________ getMd_userScorePriceReport


}
