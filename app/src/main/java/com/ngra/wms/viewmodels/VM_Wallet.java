package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ChartReport;
import com.ngra.wms.models.MD_ScoreListConfig;
import com.ngra.wms.models.MD_UserScorePriceReport;
import com.ngra.wms.models.MR_ChartReport;
import com.ngra.wms.models.MR_ScoreList;
import com.ngra.wms.models.MR_UserScorePriceReport;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Wallet extends VM_Primary {


    private List<MD_ScoreListConfig> scoreListConfigs;
    private List<MD_ChartReport> md_chartReports;
    private MD_UserScorePriceReport md_userScorePriceReport;


    //______________________________________________________________________________________________ VM_Wallet
    public VM_Wallet(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Wallet


    //______________________________________________________________________________________________ getGiveScoreList
    public void getGiveScoreList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getScoreList(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ScoreList>() {
            @Override
            public void onResponse(Call<MR_ScoreList> call, Response<MR_ScoreList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    scoreListConfigs = response.body().getResult().getConfigs();
                    sendActionToObservable(StaticValues.ML_GetGiveScore);
                    getChartReport();

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


    //______________________________________________________________________________________________ getChartReport
    public void getChartReport() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getChartReport(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ChartReport>() {
            @Override
            public void onResponse(Call<MR_ChartReport> call, Response<MR_ChartReport> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_chartReports = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetReport);
                    getUserScorePriceReport();
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ChartReport> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getChartReport


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


    //______________________________________________________________________________________________ getScoreListConfigs
    public List<MD_ScoreListConfig> getScoreListConfigs() {
        return scoreListConfigs;
    }
    //______________________________________________________________________________________________ getScoreListConfigs


    //______________________________________________________________________________________________ getMd_chartReports
    public List<MD_ChartReport> getMd_chartReports() {
        return md_chartReports;
    }
    //______________________________________________________________________________________________ getMd_chartReports


    //______________________________________________________________________________________________ getMd_userScorePriceReport
    public MD_UserScorePriceReport getMd_userScorePriceReport() {
        return md_userScorePriceReport;
    }
    //______________________________________________________________________________________________ getMd_userScorePriceReport


}
