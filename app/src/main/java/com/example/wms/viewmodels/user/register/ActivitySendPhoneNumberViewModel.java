package com.example.wms.viewmodels.user.register;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponcePrimery;
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

public class ActivitySendPhoneNumberViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private String MessageResponse;

    public ActivitySendPhoneNumberViewModel(Context context) {//____________________________________ Start ActivitySendPhoneNumberViewModel
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End ActivitySendPhoneNumberViewModel


    public void SendNumber(String PhoneNumbet, String Password) {//________________________________ Start SendNumber
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .SendPhoneNumber(
                        PhoneNumbet, Password, Password, Authorization
                )
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (!StaticFunctions.isCancel) {
                            MessageResponse = CheckResponse(response,false);
                            if(MessageResponse == null)
                                Observables.onNext("Successful");
                            else {
                                Observables.onNext("Error");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        if (!StaticFunctions.isCancel)
                            Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End SendNumber


    public String getMessageResponse() {//__________________________________________________________ Start getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ End getMessageResponse


}
