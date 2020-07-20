package com.ngra.wms.viewmodels.lottery;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_GiveScoreItem;
import com.ngra.wms.models.MD_RequestGiveScore;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_LotteryGiveScore extends VM_Primary {


    private List<MD_GiveScoreItem> md_giveScoreItemList;

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

        getPrimaryCall().enqueue(new Callback<MD_RequestGiveScore>() {
            @Override
            public void onResponse(Call<MD_RequestGiveScore> call, Response<MD_RequestGiveScore> response) {
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
            public void onFailure(Call<MD_RequestGiveScore> call, Throwable t) {
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

        getPrimaryCall().enqueue(new Callback<MD_RequestGiveScore>() {
            @Override
            public void onResponse(Call<MD_RequestGiveScore> call, Response<MD_RequestGiveScore> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_giveScoreItemList = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetGiveScore);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MD_RequestGiveScore> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetGiveScoreList



    public List<MD_GiveScoreItem> getMd_giveScoreItemList() {//_____________________________________ getMd_giveScoreItemList
        return md_giveScoreItemList;
    }//_____________________________________________________________________________________________ getMd_giveScoreItemList


    public List<MD_GiveScoreItem> getMd_userScoreItemList() {//_____________________________________ getMd_userScoreItemList
        return md_userScoreItemList;
    }//_____________________________________________________________________________________________ getMd_userScoreItemList
}
