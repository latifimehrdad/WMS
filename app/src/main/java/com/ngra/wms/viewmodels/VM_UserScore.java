package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_UserScoreInfoList;
import com.ngra.wms.models.MR_UserScoreInfoList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_UserScore extends VM_Primary {


    private MD_UserScoreInfoList md_userScoreInfoList;


    //______________________________________________________________________________________________ VM_UserScore
    public VM_UserScore(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_UserScore


    //______________________________________________________________________________________________ getUserScoreList
    public void getUserScoreList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserScoreInfoList(authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_UserScoreInfoList>() {
            @Override
            public void onResponse(Call<MR_UserScoreInfoList> call, Response<MR_UserScoreInfoList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_userScoreInfoList = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetUserScore);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_UserScoreInfoList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getUserScoreList


    //______________________________________________________________________________________________ getMd_userScoreInfoList
    public MD_UserScoreInfoList getMd_userScoreInfoList() {
        return md_userScoreInfoList;
    }
    //______________________________________________________________________________________________ getMd_userScoreInfoList


}
