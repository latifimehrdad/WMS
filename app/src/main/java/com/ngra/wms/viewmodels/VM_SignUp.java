package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MR_Register;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_SignUp extends VM_Primary {


    private String phoneNumber;
    private String reagentCode;


    //______________________________________________________________________________________________ VM_SignUp
    public VM_SignUp(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_SignUp


    //______________________________________________________________________________________________ sendNumber
    public void sendNumber() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();


        setPhoneNumber(ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .persianToEnglish(getPhoneNumber()));


        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPhoneNumber(
                        getPhoneNumber(),
                        getReagentCode(),
                        authorization,
                        RetrofitApis.app_token));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_Register>() {
            @Override
            public void onResponse(Call<MR_Register> call, Response<MR_Register> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    if (response.body().getResult() == null)
                        sendActionToObservable(StaticValues.ML_Success);
                    else {
                        boolean unconfirmedMobile = response.body().getResult().isUnconfirmedMobile();
                        if (unconfirmedMobile)
                            sendActionToObservable(StaticValues.ML_Success);
                        else
                            sendActionToObservable(StaticValues.ML_ResponseError);
                    }
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Register> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }
    //______________________________________________________________________________________________ setPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public String getReagentCode() {
        return reagentCode;
    }
    //______________________________________________________________________________________________ setPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setReagentCode(String reagentCode) {
        this.reagentCode = reagentCode;
    }
    //______________________________________________________________________________________________ setPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //______________________________________________________________________________________________ setPhoneNumber


}
