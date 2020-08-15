package com.ngra.wms.viewmodels.lottery;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_UserScoreInfoList;
import com.ngra.wms.models.MR_UserScoreInfoList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_UserScore extends VM_Primary {


    private MD_UserScoreInfoList md_userScoreInfoList;



    public VM_UserScore(Activity context) {//_______________________________________________________ VM_UserScore
        setContext(context);
    }//_____________________________________________________________________________________________ VM_UserScore



    public void GetUserScoreList() {//______________________________________________________________ GetUserScoreList

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserScoreInfoList(Authorization));

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


    }//_____________________________________________________________________________________________ GetUserScoreList


    public MD_UserScoreInfoList getMd_userScoreInfoList() {//_______________________________________ getMd_userScoreInfoList
        return md_userScoreInfoList;
    }//_____________________________________________________________________________________________ getMd_userScoreInfoList


}
