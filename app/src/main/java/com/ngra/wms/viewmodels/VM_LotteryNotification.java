package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_LotteryNotification;
import com.ngra.wms.models.MR_LotteryNotification;
import com.ngra.wms.models.MR_ScoreList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_LotteryNotification extends VM_Primary {



    private List<MD_LotteryNotification> md_lotteryNotifications;


    //______________________________________________________________________________________________ VM_LotteryNotification
    public VM_LotteryNotification(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_LotteryNotification



    //______________________________________________________________________________________________ getGiveScoreList
    public void getGiveScoreList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getLotteryNotification(RetrofitApis.app_token,aToken,Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_LotteryNotification>() {
            @Override
            public void onResponse(Call<MR_LotteryNotification> call, Response<MR_LotteryNotification> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_lotteryNotifications = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_LotteryNotification);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_LotteryNotification> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getGiveScoreList



    //______________________________________________________________________________________________ getMd_lotteryNotifications
    public List<MD_LotteryNotification> getMd_lotteryNotifications() {
        if (md_lotteryNotifications == null)
            md_lotteryNotifications = new ArrayList<>();
        return md_lotteryNotifications;
    }
    //______________________________________________________________________________________________ getMd_lotteryNotifications


}
