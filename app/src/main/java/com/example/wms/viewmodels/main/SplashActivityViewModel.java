/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.TokenModel;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;

public class SplashActivityViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private TokenModel tokenModel;
    private String MessageResponcse;

    public SplashActivityViewModel(Context context) {//_____________________________________________ Start SplashActivityViewModel
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End SplashActivityViewModel


    public void GetTokenFromServer() {//____________________________________________________________ Start GetTokenFromServer

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        retrofitComponent
                .getRetrofitApiInterface()
                .getToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value)
                .enqueue(new Callback<TokenModel>() {
                    @Override
                    public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                        MessageResponcse = CheckResponse(response, true);
                        if (MessageResponcse == null) {
                            tokenModel = response.body();
                            SaveToken();
                            Observables.onNext("Successful");
                        }
                        else
                            Observables.onNext("Error");


                    }

                    @Override
                    public void onFailure(Call<TokenModel> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });


    }//_____________________________________________________________________________________________ End GetTokenFromServer


    public TokenModel getTokenModel() {//___________________________________________________________ Start getTokenModel
        return tokenModel;
    }//_____________________________________________________________________________________________ End getTokenModel


    private void SaveToken() {//____________________________________________________________________ Start SaveToken

        SharedPreferences.Editor token =
                context.getSharedPreferences("wmstoken", 0).edit();

        token.putString("accesstoken", tokenModel.getAccess_token());
        token.putString("tokentype", tokenModel.getToken_type());
        token.putInt("expiresin", tokenModel.getExpires_in());
        token.putString("clientid", tokenModel.getClient_id());
        token.putString("issued", tokenModel.getIssued());
        token.putString("expires", tokenModel.getExpires());
        token.apply();

    }//_____________________________________________________________________________________________ End SaveToken


    public String getMessageResponcse() {//_________________________________________________________ Start getMessageResponcse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponcse
}
