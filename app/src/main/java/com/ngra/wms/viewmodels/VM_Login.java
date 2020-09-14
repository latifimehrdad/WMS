package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Login extends VM_Primary {


    private String phoneNumber;


    //______________________________________________________________________________________________ VM_Login
    public VM_Login(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Login


    //______________________________________________________________________________________________ getLoginCode
    public void getLoginCode() {

        setPhoneNumber(ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .persianToEnglish(getPhoneNumber()));


        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .LoginCode(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        getPhoneNumber(),
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_Success);
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
    //______________________________________________________________________________________________ getLoginCode


    //______________________________________________________________________________________________ getPhoneNumber
    public String getPhoneNumber() {
        if (phoneNumber == null)
            return "";
        else
            return phoneNumber;
    }
    //______________________________________________________________________________________________ getPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //______________________________________________________________________________________________ setPhoneNumber


}
