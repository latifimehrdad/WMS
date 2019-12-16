package com.example.wms.viewmodels.user.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelToken;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;

public class ActivityBeforLoginViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private boolean isCancel;
    private ModelToken modelToken;
    private String MessageResponcse;

    public ActivityBeforLoginViewModel(Context context) {//_________________________________________ Start ActivityBeforLoginViewModel
        this.context = context;
        Observables = PublishSubject.create();
        ObserverObservables();
    }//_____________________________________________________________________________________________ End ActivityBeforLoginViewModel


    public void GetLoginToken(String PhoneNumbet, String Password) {//______________________________ Start GetLoginToken
        isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = "Bearer ";
        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs != null) {
            String access_token = prefs.getString("accesstoken", null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }


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
                        if(isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, true);
                        if (MessageResponcse == null) {
                            modelToken = response.body();
                            SaveToken();
                            Observables.onNext("SuccessfulToken");
                        }
                        else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelToken> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetLoginToken


    public void GetLoginInformation() {//___________________________________________________________ Start GetLoginInformation
        Observables.onNext("Successful");
    }//_____________________________________________________________________________________________ End GetLoginInformation


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        switch (s) {
                            case "CancelByUser":
                                isCancel = true;
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


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


}
