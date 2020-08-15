package com.ngra.wms.viewmodels.user.register;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MD_Token;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_VerifyCode extends VM_Primary {

    private String PhoneNumber;
    private String VerifyCode;
    private MD_Token MDToken;
    /*    private String Password;*/


    public VM_VerifyCode(Activity context) {//_______________________________________________________ VM_VerifyCode
        setContext(context);
    }//_____________________________________________________________________________________________ VM_VerifyCode


    public void SendVerifyCode() {//________________________________________________________________ SendVerifyCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

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
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            setResponseMessage(getResponseMessage(response.body()));
                            GetLoginVerify(getPhoneNumber(), getVerifyCode());
                            /*SendMessageToObservable(StaticValues.ML_GotoLogin);*/
                        } else
                            sendActionToObservable(StaticValues.ML_ResponseError);

                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        onFailureRequest();
                    }
                });

    }//_____________________________________________________________________________________________ SendVerifyCode


    public void SendNumber() {//____________________________________________________________________ SendNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendVerificationSms(
                        getPhoneNumber(),
                        Authorization));

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

    }//_____________________________________________________________________________________________ SendNumber


    public void GetLoginVerify(String PhoneNumber, String VerifyCode) {//___________________________ GetLoginVerify

        PhoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);


        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value_Login_Code,
                        PhoneNumber,
                        VerifyCode,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_Token>() {
            @Override
            public void onResponse(Call<MD_Token> call, Response<MD_Token> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    MDToken = response.body();
                    if (StaticFunctions.SaveToken(getContext(), MDToken))
                        GetLoginInformation();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_Token> call, Throwable t) {
                onFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetLoginVerify


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

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

    }//_____________________________________________________________________________________________ GetLoginInformation


    public void GetLoginCode(String PhoneNumber) {//________________________________________________ GetLoginCode

        PhoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);


        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .LoginCode(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        PhoneNumber,
                        Authorization));

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

    }//_____________________________________________________________________________________________ GetLoginCode


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


/*    public void setPassword(String password) {//____________________________________________________ setPassword
        password = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(password);
        Password = password;
    }//_____________________________________________________________________________________________ setPassword*/
}
