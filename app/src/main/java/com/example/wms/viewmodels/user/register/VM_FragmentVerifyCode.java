package com.example.wms.viewmodels.user.register;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_FragmentVerifyCode {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private String MessageResponse;


    public VM_FragmentVerifyCode(Context context) {//_______________________________________________ VM_FragmentVerifyCode
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentVerifyCode


    public void SendVerifyCode(String PhoneNumber, String VerifyCode) {//___________________________ SendVerifyCode
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .SendVerifyCode(
                        PhoneNumber, VerifyCode, Authorization
                )
                .enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        if (!StaticFunctions.isCancel) {
                            MessageResponse = CheckResponse(response, false);
                            if (MessageResponse == null)
                                Observables.onNext(StaticValues.ML_GotoLogin);
                            else
                                Observables.onNext(StaticValues.ML_ResponseError);

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        if (!StaticFunctions.isCancel)
                            Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ SendVerifyCode


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables
}
