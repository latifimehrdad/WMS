package com.example.wms.viewmodels.user.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.models.ModelToken;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_FragmentLogin {

    private Context context;
    private PublishSubject<String> Observables = null;
    private ModelToken modelToken;
    private String MessageResponcse;
    private ModelSettingInfo.ModelProfileSetting profile;

    public VM_FragmentLogin(Context context) {//_________________________________________ Start VM_FragmentLogin
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_FragmentLogin


    public void GetLoginToken(String PhoneNumbet, String Password) {//______________________________ Start GetLoginToken
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_password,
                        PhoneNumbet,
                        Password,
                        Authorization)
                .enqueue(new Callback<ModelToken>() {
                    @Override
                    public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, true);
                        if (MessageResponcse == null) {
                            modelToken = response.body();
                            SaveToken();
                            Observables.onNext("SuccessfulToken");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelToken> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetLoginToken


    public void GetLoginInformation() {//___________________________________________________________ Start GetLoginInformation

        StaticFunctions.isCancel = false;

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
                        MessageResponcse = CheckResponse(response, true);
                        if (MessageResponcse == null) {
                            profile = response.body().getResult();
                            SaveProfile();
                            Observables.onNext("Successful");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetLoginInformation


    private void SaveProfile() {//__________________________________________________________________ Start SaveProfile
        SharedPreferences.Editor token =
                context.getSharedPreferences("wmstoken", 0).edit();
        token.putString("name",profile.getName());
        token.putString("lastName",profile.getLastName());
        token.putInt("gender",profile.getGender());
        token.putBoolean("complateprofile",profile.getProfileCompleted());
        token.apply();
    }//_____________________________________________________________________________________________ End SaveProfile


    private void SaveToken() {//____________________________________________________________________ Start SaveToken

        SharedPreferences.Editor token =
                context.getSharedPreferences("wmstoken", 0).edit();

        token.putString("accesstoken", modelToken.getAccess_token());
        token.putString("tokentype", modelToken.getToken_type());
        token.putInt("expiresin", modelToken.getExpires_in());
        token.putString("phonenumber", modelToken.getPhoneNumber());
        token.putString("clientid", modelToken.getClient_id());
        token.putString("issued", modelToken.getIssued());
        token.putString("expires", modelToken.getExpires());
        token.apply();

    }//_____________________________________________________________________________________________ End SaveToken


    public String getMessageResponse() {//__________________________________________________________ Start getMessageResponse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponse


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
