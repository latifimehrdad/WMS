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

public class VM_FragmentVerifyCode {

    private Context context;
    private PublishSubject<String> Observables = null;
    private String MessageResponse;


    public VM_FragmentVerifyCode(Context context) {//_________________________________________ Start VM_FragmentVerifyCode
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_FragmentVerifyCode


    public void SendVerifyCode(String PhoneNumbet, String VerifyCode) {//___________________________ Start SendVerifyCode
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .SendVerifyCode(
                        PhoneNumbet, VerifyCode, Authorization
                )
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (!StaticFunctions.isCancel) {
                            MessageResponse = CheckResponse(response, false);
                            if (MessageResponse == null)
                                Observables.onNext("Successful");
                            else
                                Observables.onNext("Error");

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        if (!StaticFunctions.isCancel)
                            Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End SendVerifyCode


    public String getMessageresponse() {//__________________________________________________________ Staring getMessageresponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ End getMessageresponse


    public PublishSubject<String> getObservables() {//______________________________________________ Staring getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
