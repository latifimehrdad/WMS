package com.ngra.wms.viewmodels;

import android.app.Activity;

import androidx.databinding.library.baseAdapters.BR;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_BestScore;
import com.ngra.wms.models.MD_GameReport;
import com.ngra.wms.models.MR_BestScore;
import com.ngra.wms.models.MR_GameReport;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_BestScore extends VM_Primary {


    private List<MD_BestScore> md_bestScores;

    private MD_GameReport md_gameReport;


    //______________________________________________________________________________________________ VM_BestScore
    public VM_BestScore(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_BestScore


    //______________________________________________________________________________________________ getBestScore
    public void getBestScore(String scoreCategoryName) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .gettopscorelist(scoreCategoryName, authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_BestScore>() {
            @Override
            public void onResponse(Call<MR_BestScore> call, Response<MR_BestScore> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_bestScores = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetBestScore);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_BestScore> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ getBestScore



    //______________________________________________________________________________________________ getBestScore
    public void getGameReport() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getGameReport(authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_GameReport>() {
            @Override
            public void onResponse(Call<MR_GameReport> call, Response<MR_GameReport> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setMd_gameReport(response.body().getResult());

                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_GameReport> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ getBestScore



    //______________________________________________________________________________________________ getMd_bestScores
    public List<MD_BestScore> getMd_bestScores() {
        return md_bestScores;
    }
    //______________________________________________________________________________________________ getMd_bestScores



    //______________________________________________________________________________________________ getMd_gameReport
    public MD_GameReport getMd_gameReport() {
        return md_gameReport;
    }
    //______________________________________________________________________________________________ getMd_gameReport


    //______________________________________________________________________________________________ setMd_gameReport
    public void setMd_gameReport(MD_GameReport md_gameReport) {
        this.md_gameReport = md_gameReport;
        notifyChange();
    }
    //______________________________________________________________________________________________ setMd_gameReport


}
