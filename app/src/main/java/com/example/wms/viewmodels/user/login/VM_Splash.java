package com.example.wms.viewmodels.user.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
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

public class VM_Splash extends VM_Primary {

    private Context context;
    private ModelToken modelToken;
    private ModelSettingInfo.ModelProfileSetting profile;


    public VM_Splash(Context context) {//___________________________________________________________ VM_Splash
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Splash


    public void CheckToken() {//____________________________________________________________________ CheckToken

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            GetTokenFromServer();
        } else {
            String access_token = prefs.getString(context.getString(R.string.ML_AccessToken), null);
            String expires = prefs.getString(context.getString(R.string.ML_Expires), null);
            String PhoneNumber = prefs.getString(context.getString(R.string.ML_PhoneNumber), null);
            if ((access_token == null) || (expires == null))
                GetTokenFromServer();
            else {
                if (PhoneNumber != null) {
                    GetLoginInformation();
                } else
                    GetTokenFromServer();
            }

        }

    }//_____________________________________________________________________________________________ CheckToken


    public void GetTokenFromServer() {//____________________________________________________________ GetTokenFromServer

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        retrofitComponent
                .getRetrofitApiInterface()
                .getToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value)
                .enqueue(new Callback<ModelToken>() {
                    @Override
                    public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                        setResponseMessage(CheckResponse(response, true));
                        if (getResponseMessage() == null) {
                            modelToken = response.body();
                            if (StaticFunctions.SaveToken(context, modelToken))
                                getPublishSubject().onNext(StaticValues.ML_GotoLogin);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelToken> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetTokenFromServer


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization)
                .enqueue(new Callback<ModelSettingInfo>() {
                    @Override
                    public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        setResponseMessage(CheckResponse(response, true));
                        if (getResponseMessage() == null) {
                            profile = response.body().getResult();
                            if (StaticFunctions.SaveProfile(context, profile))
                                getPublishSubject().onNext(StaticValues.ML_GoToHome);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetLoginInformation


}



