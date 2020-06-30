package com.example.wms.viewmodels.user.register;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponsePrimary;
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
    private String PhoneNumber;
    private String Password;


    public VM_SignUp(Context context) {//___________________________________________________________ VM_SignUp
        this.context = context;
    }//_____________________________________________________________________________________________ VM_SignUp


    public void SendNumber() {//____________________________________________________________________ SendNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPhoneNumber(
                        getPhoneNumber(),
                        getPassword(),
                        getPassword(),
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null)
                    getPublishSubject().onNext(StaticValues.ML_Success);
                else {
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendNumber


    public String getPhoneNumber() {//______________________________________________________________ getPhoneNumber
        return PhoneNumber;
    }//_____________________________________________________________________________________________ getPhoneNumber

    public void setPhoneNumber(String phoneNumber) {//______________________________________________ setPhoneNumber
        PhoneNumber = phoneNumber;
    }//_____________________________________________________________________________________________ setPhoneNumber

    public String getPassword() {//_________________________________________________________________ getPassword
        return Password;
    }//_____________________________________________________________________________________________ getPassword

    public void setPassword(String password) {//____________________________________________________ setPassword
        Password = password;
    }//_____________________________________________________________________________________________ setPassword
}
