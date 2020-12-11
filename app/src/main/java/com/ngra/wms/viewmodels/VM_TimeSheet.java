package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.MR_TimeSheet;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_TimeSheet extends VM_Primary {

    private List<MD_TimeSheet> md_timeSheets;


    //______________________________________________________________________________________________ VM_TimeSheet
    public VM_TimeSheet(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_TimeSheet


    //______________________________________________________________________________________________ getVehicleTimes
    public void getVehicleTimes() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getVehicleTimes(RetrofitApis.app_token,aToken,authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_TimeSheet>() {
            @Override
            public void onResponse(Call<MR_TimeSheet> call, Response<MR_TimeSheet> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_timeSheets = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_TimeSheet> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getVehicleTimes


    //______________________________________________________________________________________________ getBoothTimes
    public void getBoothTimes() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBoothTimes(RetrofitApis.app_token,aToken,authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_TimeSheet>() {
            @Override
            public void onResponse(Call<MR_TimeSheet> call, Response<MR_TimeSheet> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_timeSheets = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_TimeSheet> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getBoothTimes


    //______________________________________________________________________________________________ sendPackageRequest
    public void sendPackageRequest(Integer timeId) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPackageRequest(
                        timeId,
                        RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    getLoginInformation();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendPackageRequest


    //______________________________________________________________________________________________ getLoginInformation
    public void getLoginInformation() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(RetrofitApis.app_token,aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                String m = checkResponse(response, true);
                if (m == null) {
                    if (getUtility().saveProfile(getContext(), response.body().getResult()))
                        sendActionToObservable(StaticValues.ML_SendPackageRequest);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
                ;
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getLoginInformation




    //______________________________________________________________________________________________ sendCollectRequest
    public void sendCollectRequest(MD_WasteAmountRequests md_wasteAmountRequests) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .RequestCollection(
                        md_wasteAmountRequests,
                        RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_CollectRequestDone);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendCollectRequest



    //______________________________________________________________________________________________ getMd_timesSheet
    public List<MD_TimeSheet> getMd_timesSheet() {
        return md_timeSheets;
    }
    //______________________________________________________________________________________________ getMd_timesSheet


}
