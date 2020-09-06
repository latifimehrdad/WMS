package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MR_Register;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_SignUp extends VM_Primary {


    private String PhoneNumber;



    //______________________________________________________________________________________________ VM_SignUp
    public VM_SignUp(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_SignUp


    //______________________________________________________________________________________________ sendNumber
    public void sendNumber(String ReagentCode) {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPhoneNumber(
                        getPhoneNumber(),
                        ReagentCode,
                        authorization));

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


    //______________________________________________________________________________________________ getPhoneNumber
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    //______________________________________________________________________________________________ getPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .persianToEnglish(phoneNumber);
        PhoneNumber = phoneNumber;
    }
    //______________________________________________________________________________________________ setPhoneNumber


}
