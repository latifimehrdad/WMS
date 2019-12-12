/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.main;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.TokenModel;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivityViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private TokenModel tokenModel;

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
                        tokenModel = response.body();
                        Observables.onNext("Successful");
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


}
