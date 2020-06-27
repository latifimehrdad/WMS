package com.example.wms.viewmodels.user.login;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.models.ModelToken;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_Login extends VM_Primary {

    private Context context;
    private ModelToken modelToken;
    private String PhoneNumber;
    private String Password;

    public VM_Login(Context context) {//____________________________________________________________ VM_Login
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Login


    public void GetLoginToken() {//_________________________________________________________________ GetLoginToken

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_password,
                        getPhoneNumber(),
                        getPassword(),
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelToken>() {
            @Override
            public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    modelToken = response.body();
                    if (StaticFunctions.SaveToken(context, modelToken))
                        GetLoginInformation();
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelToken> call, Throwable t) {
                if (getPrimaryCall().isCanceled())
                    getPublishSubject().onNext(StaticValues.ML_RequestCancel);
                else
                    getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            }
        });

    }//_____________________________________________________________________________________________ GetLoginToken


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    if (StaticFunctions.SaveProfile(context, response.body().getResult()))
                        getPublishSubject().onNext(StaticValues.ML_GoToHome);
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                if (getPrimaryCall().isCanceled())
                    getPublishSubject().onNext(StaticValues.ML_RequestCancel);
                else
                    getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            }
        });

    }//_____________________________________________________________________________________________ GetLoginInformation


    public String getPhoneNumber() {//______________________________________________________________ getPhoneNumber
        return PhoneNumber;
    }//_____________________________________________________________________________________________ getPhoneNumber

    public void setPhoneNumber(String phoneNumber) {//______________________________________________ setPhoneNumber
        PhoneNumber = phoneNumber;
    }//_____________________________________________________________________________________________ setPhoneNumber

    public String getPassword() {//_________________________________________________________________ getPassword
        return Password;
    }//_____________________________________________________________________________________________ getPassword

    public void setPassword(String password) {//____________________________________________________ setPassword
        Password = password;
    }//_____________________________________________________________________________________________ setPassword
}
