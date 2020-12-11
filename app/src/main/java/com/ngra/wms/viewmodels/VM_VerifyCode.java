package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MD_Token;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_VerifyCode extends VM_Primary {

    private String PhoneNumber;
    private MD_Token MDToken;
    private String verifyNumber1;
    private String verifyNumber2;
    private String verifyNumber3;
    private String verifyNumber4;
    private String verifyNumber5;
    private String verifyNumber6;


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
        String verifyCode = getVerifyNumber1() + getVerifyNumber2() + getVerifyNumber3() +
                getVerifyNumber4() + getVerifyNumber5() + getVerifyNumber6();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendVerifyCode(
                        getPhoneNumber(),
                        verifyCode,
                        authorization,
                        RetrofitApis.app_token
                ));

        if (getPrimaryCall() == null)
            return;

                getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            setResponseMessage(getResponseMessage(response.body()));
                            getLoginVerify();
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
                        authorization,
                        RetrofitApis.app_token));

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
    //______________________________________________________________________________________________ sendNumber


    //______________________________________________________________________________________________ getLoginVerify
    public void getLoginVerify() {

        setPhoneNumber(ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .persianToEnglish(getPhoneNumber()));


        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String verifyCode = getVerifyNumber1() + getVerifyNumber2() + getVerifyNumber3() +
                getVerifyNumber4() + getVerifyNumber5() + getVerifyNumber6();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value_Login_Code,
                        getPhoneNumber(),
                        verifyCode,
                        authorization,
                        RetrofitApis.app_token));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MD_Token>() {
            @Override
            public void onResponse(Call<MD_Token> call, Response<MD_Token> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    MDToken = response.body();
                    if (getUtility().saveToken(getContext(), MDToken))
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
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    if (getUtility().saveProfile(getContext(), response.body().getResult()))
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
                        authorization,
                        RetrofitApis.app_token));

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
        return PhoneNumber;
    }
    //______________________________________________________________________________________________ getPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    //______________________________________________________________________________________________ setPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumbe
    public String getVerifyNumber1() {
        return verifyNumber1;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public void setVerifyNumber1(String verifyNumber1) {
        this.verifyNumber1 = verifyNumber1;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public String getVerifyNumber2() {
        return verifyNumber2;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public void setVerifyNumber2(String verifyNumber2) {
        this.verifyNumber2 = verifyNumber2;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public String getVerifyNumber3() {
        return verifyNumber3;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public void setVerifyNumber3(String verifyNumber3) {
        this.verifyNumber3 = verifyNumber3;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public String getVerifyNumber4() {
        return verifyNumber4;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public void setVerifyNumber4(String verifyNumber4) {
        this.verifyNumber4 = verifyNumber4;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public String getVerifyNumber5() {
        return verifyNumber5;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public void setVerifyNumber5(String verifyNumber5) {
        this.verifyNumber5 = verifyNumber5;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public String getVerifyNumber6() {
        return verifyNumber6;
    }
    //______________________________________________________________________________________________ setPhoneNumbe


    //______________________________________________________________________________________________ setPhoneNumbe
    public void setVerifyNumber6(String verifyNumber6) {
        this.verifyNumber6 = verifyNumber6;
    }
    //______________________________________________________________________________________________ setPhoneNumbe




}
