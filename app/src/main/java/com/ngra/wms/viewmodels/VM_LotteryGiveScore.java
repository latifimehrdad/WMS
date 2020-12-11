package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_GiveScoreItem;
import com.ngra.wms.models.MD_ScoreListConfig;
import com.ngra.wms.models.MR_GiveScore;
import com.ngra.wms.models.MR_ScoreList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_LotteryGiveScore extends VM_Primary {

    private List<MD_GiveScoreItem> scoreItemsNormal;

    private List<MD_ScoreListConfig> scoreListConfigs;

    private List<MD_GiveScoreItem> md_userScoreItemList;


    //______________________________________________________________________________________________ VM_LotteryGiveScore
    public VM_LotteryGiveScore(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_LotteryGiveScore


    //______________________________________________________________________________________________ getUserScoreList
    public void getUserScoreList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .GetUserScore(RetrofitApis.app_token,aToken,Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_GiveScore>() {
            @Override
            public void onResponse(Call<MR_GiveScore> call, Response<MR_GiveScore> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_userScoreItemList = response.body().getResult();
                    Collections.reverse(md_userScoreItemList);
                    sendActionToObservable(StaticValues.ML_GetUserScore);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_GiveScore> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ getUserScoreList


    //______________________________________________________________________________________________ getGiveScoreList
    public void getGiveScoreList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getScoreList(RetrofitApis.app_token,aToken,Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_ScoreList>() {
            @Override
            public void onResponse(Call<MR_ScoreList> call, Response<MR_ScoreList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    scoreItemsNormal = response.body().getResult().getNormals();
                    scoreListConfigs = response.body().getResult().getConfigs();
                    sendActionToObservable(StaticValues.ML_GetGiveScore);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_ScoreList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getGiveScoreList


    //______________________________________________________________________________________________ getScoreItemsNormal
    public List<MD_GiveScoreItem> getScoreItemsNormal() {
        return scoreItemsNormal;
    }
    //______________________________________________________________________________________________ getScoreItemsNormal


    //______________________________________________________________________________________________ getScoreListConfigs
    public List<MD_ScoreListConfig> getScoreListConfigs() {
        return scoreListConfigs;
    }
    //______________________________________________________________________________________________ getScoreListConfigs


    //______________________________________________________________________________________________ getMd_userScoreItemList
    public List<MD_GiveScoreItem> getMd_userScoreItemList() {
        return md_userScoreItemList;
    }
    //______________________________________________________________________________________________ getMd_userScoreItemList

}
