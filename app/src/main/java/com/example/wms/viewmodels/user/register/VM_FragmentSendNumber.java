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

public class VM_FragmentSendNumber {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private String MessageResponse;

    public VM_FragmentSendNumber(Context context) {//_______________________________________________ VM_FragmentSendNumber
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentSendNumber


    public void SendNumber(String PhoneNumber, String Password) {//_________________________________ SendNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .SendPhoneNumber(
                        PhoneNumber, Password, Password, Authorization
                )
                .enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        if (!StaticFunctions.isCancel) {
                            MessageResponse = CheckResponse(response,false);
                            if(MessageResponse == null)
                                Observables.onNext(StaticValues.ML_Success);
                            else {
                                Observables.onNext(StaticValues.ML_ResponseError);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        if (!StaticFunctions.isCancel)
                            Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ SendNumber


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables
}
