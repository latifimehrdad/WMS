package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelBuildingRenovationCode;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.activities.MainActivity;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfileCode extends VM_Primary {

    private String buildingRenovationCode;

    //______________________________________________________________________________________________ VM_ProfileCode
    public VM_ProfileCode(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_ProfileCode


    //______________________________________________________________________________________________ sendCode
    public void sendCode() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendBuildingRenovationCode(
                        buildingRenovationCode,
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
                    MainActivity.completeProfile = true;
                    sendActionToObservable(StaticValues.ML_EditProfile);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ sendCode


    //______________________________________________________________________________________________ getCode
    public void getCode() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBuildingRenovationCode(
                        RetrofitApis.app_token,
                        aToken,
                        Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelBuildingRenovationCode>() {
            @Override
            public void onResponse(Call<ModelBuildingRenovationCode> call, Response<ModelBuildingRenovationCode> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    if (response.body().getResult() == null)
                        sendActionToObservable(StaticValues.ML_GetAccountNumberNull);
                    else {
                        setResponseMessage(response.body().getResult());
                        setBuildingRenovationCode(response.body().getResult());
                        notifyChange();
                    }
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelBuildingRenovationCode> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getCode


    //______________________________________________________________________________________________ setBuildingRenovationCode
    public void setBuildingRenovationCode(String buildingRenovationCode) {
        this.buildingRenovationCode = buildingRenovationCode;
    }
    //______________________________________________________________________________________________ setBuildingRenovationCode


    //______________________________________________________________________________________________ getBuildingRenovationCode
    public String getBuildingRenovationCode() {
        return buildingRenovationCode;
    }
    //______________________________________________________________________________________________ getBuildingRenovationCode


}
