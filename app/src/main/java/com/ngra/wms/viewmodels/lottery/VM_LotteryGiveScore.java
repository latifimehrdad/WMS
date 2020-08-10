package com.ngra.wms.viewmodels.lottery;

import android.app.Activity;

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

    public VM_LotteryGiveScore(Activity context) {//________________________________________________ VM_LotteryGiveScore
        setContext(context);
    }//_____________________________________________________________________________________________ VM_LotteryGiveScore


    public void GetUserScoreList() {//______________________________________________________________ GetUserScoreList

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .GetUserScore(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_GiveScore>() {
            @Override
            public void onResponse(Call<MR_GiveScore> call, Response<MR_GiveScore> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_userScoreItemList = response.body().getResult();
                    Collections.reverse(md_userScoreItemList);
                    SendMessageToObservable(StaticValues.ML_GetUserScore);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_GiveScore> call, Throwable t) {
                OnFailureRequest();
            }
        });


    }//_____________________________________________________________________________________________ GetUserScoreList


    public void GetGiveScoreList() {//______________________________________________________________ GetGiveScoreList

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getScoreList(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ScoreList>() {
            @Override
            public void onResponse(Call<MR_ScoreList> call, Response<MR_ScoreList> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    scoreItemsNormal = response.body().getResult().getNormals();
                    scoreListConfigs = response.body().getResult().getConfigs();
                    SendMessageToObservable(StaticValues.ML_GetGiveScore);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_ScoreList> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetGiveScoreList


    public List<MD_GiveScoreItem> getScoreItemsNormal() {//_____________________________________ getScoreItemsNormal
        return scoreItemsNormal;
    }//_____________________________________________________________________________________________ getScoreItemsNormal


    public List<MD_ScoreListConfig> getScoreListConfigs() {//_____________________________________ getScoreListConfigs
        return scoreListConfigs;
    }//_____________________________________________________________________________________________ getScoreListConfigs


    public List<MD_GiveScoreItem> getMd_userScoreItemList() {//_____________________________________ getMd_userScoreItemList
        return md_userScoreItemList;
    }//_____________________________________________________________________________________________ getMd_userScoreItemList

}
