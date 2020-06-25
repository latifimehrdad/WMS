package com.example.wms.viewmodels.user.register;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_SignUp extends VM_Primary {

    private Context context;

    public VM_SignUp(Context context) {//___________________________________________________________ VM_SignUp
        this.context = context;
    }//_____________________________________________________________________________________________ VM_SignUp


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
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                            setResponseMessage(CheckResponse(response,false));
                            if(getResponseMessage() == null)
                                getPublishSubject().onNext(StaticValues.ML_Success);
                            else {
                                getPublishSubject().onNext(StaticValues.ML_ResponseError);
                            }
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ SendNumber


}
