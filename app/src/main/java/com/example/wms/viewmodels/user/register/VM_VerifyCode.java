package com.example.wms.viewmodels.user.register;

import android.app.Activity;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_VerifyCode extends VM_Primary {

    private String PhoneNumber;
    private String VerifyCode;
    private String Password;


    public VM_VerifyCode(Activity context) {//_______________________________________________________ VM_VerifyCode
        setContext(context);
    }//_____________________________________________________________________________________________ VM_VerifyCode


    public void SendVerifyCode() {//________________________________________________________________ SendVerifyCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

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
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            setResponseMessage(GetMessage(response));
                            SendMessageToObservable(StaticValues.ML_GotoLogin);
                        }
                        else
                            SendMessageToObservable(StaticValues.ML_ResponseError);

                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        OnFailureRequest();
                    }
                });

    }//_____________________________________________________________________________________________ SendVerifyCode



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

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response));
                    SendMessageToObservable(StaticValues.ML_Success);
                }
                else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendNumber



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

    public String getPassword() {//_________________________________________________________________ getPassword
        return Password;
    }//_____________________________________________________________________________________________ getPassword

    public void setPassword(String password) {//____________________________________________________ setPassword
        password = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(password);
        Password = password;
    }//_____________________________________________________________________________________________ setPassword
}
