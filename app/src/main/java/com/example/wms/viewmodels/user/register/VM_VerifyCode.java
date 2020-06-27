package com.example.wms.viewmodels.user.register;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_VerifyCode extends VM_Primary {

    private Context context;
    private String PhoneNumber;
    private String VerifyCode;


    public VM_VerifyCode(Context context) {//_______________________________________________________ VM_VerifyCode
        this.context = context;
    }//_____________________________________________________________________________________________ VM_VerifyCode


    public void SendVerifyCode() {//________________________________________________________________ SendVerifyCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .SendVerifyCode(
                        getPhoneNumber(),
                        getVerifyCode(),
                        Authorization
                )
                .enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        if (!StaticFunctions.isCancel) {
                            setResponseMessage(CheckResponse(response, false));
                            if (getResponseMessage() == null)
                                getPublishSubject().onNext(StaticValues.ML_GotoLogin);
                            else
                                getPublishSubject().onNext(StaticValues.ML_ResponseError);

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ SendVerifyCode


    public String getPhoneNumber() {//______________________________________________________________ getPhoneNumber
        return PhoneNumber;
    }//_____________________________________________________________________________________________ getPhoneNumber

    public void setPhoneNumber(String phoneNumber) {//______________________________________________ setPhoneNumber
        PhoneNumber = phoneNumber;
    }//_____________________________________________________________________________________________ setPhoneNumber

    public String getVerifyCode() {//_______________________________________________________________ getVerifyCode
        return VerifyCode;
    }//_____________________________________________________________________________________________ getVerifyCode

    public void setVerifyCode(String verifyCode) {//________________________________________________ setVerifyCode
        VerifyCode = verifyCode;
    }//_____________________________________________________________________________________________ setVerifyCode
}
