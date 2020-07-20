package com.ngra.wms.viewmodels.user.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_Hi;
import com.ngra.wms.models.MD_RequestHi;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.ModelToken;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Splash extends VM_Primary {

    private ModelToken modelToken;
    private ModelSettingInfo.ModelProfileSetting profile;
    private MD_Hi md_hi;

    public VM_Splash(Activity context) {//__________________________________________________________ VM_Splash
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Splash


    public void CheckToken() {//____________________________________________________________________ CheckToken

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            GetTokenFromServer();
        } else {
            String access_token = prefs.getString(getContext().getString(R.string.ML_AccessToken), null);
            String expires = prefs.getString(getContext().getString(R.string.ML_Expires), null);
            String PhoneNumber = prefs.getString(getContext().getString(R.string.ML_PhoneNumber), null);
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
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value));

        getPrimaryCall().enqueue(new Callback<ModelToken>() {
            @Override
            public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    modelToken = response.body();
                    if (StaticFunctions.SaveToken(getContext(), modelToken))
                        SendMessageToObservable(StaticValues.ML_GotoLogin);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelToken> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetTokenFromServer


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    profile = response.body().getResult();
                    if (profile != null) {
                        if (StaticFunctions.SaveProfile(getContext(), profile))
                            GetHi();
                    } else {
                        if (StaticFunctions.LogOut(getContext()))
                            GetTokenFromServer();
                    }
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetLoginInformation


    public void GetHi() {//_________________________________________________________________________ GetHi

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getHi("1",
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_RequestHi>() {
            @Override
            public void onResponse(Call<MD_RequestHi> call, Response<MD_RequestHi> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    md_hi = response.body().getResult();
                    setResponseMessage("");
                    CheckUpdate();
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_RequestHi> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetHi


    private void CheckUpdate() {//__________________________________________________________________ CheckUpdate
        PackageInfo pInfo = null;
        Integer Version = 0;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            Version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Version = 0;
        }

        String v = md_hi.getVersion();
        v = v.replaceAll("v", "");



//        if (Version < Integer.parseInt(v))
            SendMessageToObservable(StaticValues.ML_GoToUpdate);
//        else
//            SendMessageToObservable(StaticValues.ML_GoToHome);

    }//_____________________________________________________________________________________________ CheckUpdate


    public MD_Hi getMd_hi() {//_____________________________________________________________________ getMd_hi
        return md_hi;
    }//_____________________________________________________________________________________________ getMd_hi

}



