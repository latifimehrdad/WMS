package com.ngra.wms.viewmodels.user.register;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MR_Register;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_SignUp extends VM_Primary {


    private String PhoneNumber;
    private String Password;


    public VM_SignUp(Activity context) {//___________________________________________________________ VM_SignUp
        setContext(context);
    }//_____________________________________________________________________________________________ VM_SignUp


    public void SendNumber() {//____________________________________________________________________ SendNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPhoneNumber(
                        getPhoneNumber(),
                        getPassword(),
                        getPassword(),
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Register>() {
            @Override
            public void onResponse(Call<MR_Register> call, Response<MR_Register> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response.body()));
                    if (response.body().getResult() == null)
                        SendMessageToObservable(StaticValues.ML_Success);
                    else {
                        boolean unconfirmedMobile = response.body().getResult().isUnconfirmedMobile();
                        if (unconfirmedMobile)
                            SendMessageToObservable(StaticValues.ML_Success);
                        else
                            SendMessageToObservable(StaticValues.ML_ResponseError);
                    }
                } else {
                        SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Register> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendNumber


    public String getPhoneNumber() {//______________________________________________________________ getPhoneNumber
        return PhoneNumber;
    }//_____________________________________________________________________________________________ getPhoneNumber

    public void setPhoneNumber(String phoneNumber) {//______________________________________________ setPhoneNumber
        phoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(phoneNumber);
        PhoneNumber = phoneNumber;
    }//_____________________________________________________________________________________________ setPhoneNumber

    public String getPassword() {//_________________________________________________________________ getPassword
        return Password;
    }//_____________________________________________________________________________________________ getPassword

    public void setPassword(String password) {//____________________________________________________ setPassword
        Password = password;
    }//_____________________________________________________________________________________________ setPassword
}
