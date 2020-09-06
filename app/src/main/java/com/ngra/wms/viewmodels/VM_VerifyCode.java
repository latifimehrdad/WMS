package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MD_Token;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_VerifyCode extends VM_Primary {

    private String PhoneNumber;
    private String VerifyCode;
    private MD_Token MDToken;


    //______________________________________________________________________________________________ VM_VerifyCode
    public VM_VerifyCode(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_VerifyCode



    //______________________________________________________________________________________________ sendVerifyCode
    public void sendVerifyCode() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        retrofitComponent
                .getRetrofitApiInterface()
                .SendVerifyCode(
                        getPhoneNumber(),
                        getVerifyCode(),
                        authorization
                )
                .enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            setResponseMessage(getResponseMessage(response.body()));
                            getLoginVerify(getPhoneNumber(), getVerifyCode());
                            /*SendMessageToObservable(StaticValues.ML_GotoLogin);*/
                        } else
                            sendActionToObservable(StaticValues.ML_ResponseError);

                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        onFailureRequest();
                    }
                });

    }
    //______________________________________________________________________________________________ sendVerifyCode



    //______________________________________________________________________________________________ sendNumber
    public void sendNumber() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendVerificationSms(
                        getPhoneNumber(),
                        authorization));

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
    //______________________________________________________________________________________________ sendNumber



    //______________________________________________________________________________________________ getLoginVerify
    public void getLoginVerify(String phoneNumber, String verifyCode) {

        phoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .persianToEnglish(phoneNumber);


        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value_Login_Code,
                        phoneNumber,
                        verifyCode,
                        authorization));

        getPrimaryCall().enqueue(new Callback<MD_Token>() {
            @Override
            public void onResponse(Call<MD_Token> call, Response<MD_Token> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    MDToken = response.body();
                    if (StaticFunctions.SaveToken(getContext(), MDToken))
                        getLoginInformation();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_Token> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getLoginVerify



    //______________________________________________________________________________________________ getLoginInformation
    public void getLoginInformation() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        authorization));

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    if (StaticFunctions.SaveProfile(getContext(), response.body().getResult()))
                        sendActionToObservable(StaticValues.ML_GoToHome);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getLoginInformation



    //_____________________________________________________________________________________________ getLoginCode
    public void getLoginCode(String phoneNumber) {

        phoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .persianToEnglish(phoneNumber);


        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .LoginCode(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        phoneNumber,
                        authorization));

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
        return PhoneNumber;
    }
    //______________________________________________________________________________________________ getPhoneNumber



    //______________________________________________________________________________________________ setPhoneNumber
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    //______________________________________________________________________________________________ setPhoneNumber


    //______________________________________________________________________________________________ getVerifyCode
    public String getVerifyCode() {
        return VerifyCode;
    }
    //______________________________________________________________________________________________ getVerifyCode



    //______________________________________________________________________________________________ setVerifyCod
    public void setVerifyCode(String verifyCode) {
        VerifyCode = verifyCode;
    }
    //______________________________________________________________________________________________ setVerifyCode



}
