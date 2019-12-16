package com.example.wms.viewmodels.user.register;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelRegisterCitizen;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;

public class ActivitySendPhoneNumberViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private boolean isCancel;
    private String MessageResponse;

    public ActivitySendPhoneNumberViewModel(Context context) {//____________________________________ Start ActivitySendPhoneNumberViewModel
        this.context = context;
        Observables = PublishSubject.create();
        ObserverObservables();
    }//_____________________________________________________________________________________________ End ActivitySendPhoneNumberViewModel


    public void SendNumber(String PhoneNumbet, String Password) {//________________________________ Start SendNumber
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
                .SendPhoneNumber(
                        PhoneNumbet, Password, Password, Authorization
                )
                .enqueue(new Callback<ModelRegisterCitizen>() {
                    @Override
                    public void onResponse(Call<ModelRegisterCitizen> call, Response<ModelRegisterCitizen> response) {
                        if (!isCancel) {
                            MessageResponse = CheckResponse(response,false);
                            if(MessageResponse == null)
                                Observables.onNext("Successful");
                            else {
                                Observables.onNext("Error");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelRegisterCitizen> call, Throwable t) {
                        if (!isCancel)
                            Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End SendNumber



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


    public String getMessageResponse() {//__________________________________________________________ Start getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ End getMessageResponse


}
