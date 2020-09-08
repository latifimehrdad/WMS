package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelBuildingRenovationCode;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.activitys.MainActivity;
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

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendBuildingRenovationCode(
                        buildingRenovationCode,
                        authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                if (StaticFunctions.isCancel)
                    return;
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

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBuildingRenovationCode(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelBuildingRenovationCode>() {
            @Override
            public void onResponse(Call<ModelBuildingRenovationCode> call, Response<ModelBuildingRenovationCode> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    if (response.body().getResult() == null)
                        sendActionToObservable(StaticValues.ML_GetAccountNumberNull);
                    else {
                        setResponseMessage(response.body().getResult());
                        sendActionToObservable(StaticValues.ML_GetRenovationCode);
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

}
