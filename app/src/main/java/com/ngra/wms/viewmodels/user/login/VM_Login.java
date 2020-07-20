package com.ngra.wms.viewmodels.user.login;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.ModelToken;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Login extends VM_Primary {

    private ModelToken modelToken;

    public VM_Login(Activity context) {//___________________________________________________________ VM_Login
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Login


    public void GetLoginToken(String PhoneNumber, String Password) {//______________________________ GetLoginToken

        PhoneNumber = ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .PersianToEnglish(PhoneNumber);

        

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_password,
                        PhoneNumber,
                        Password,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelToken>() {
            @Override
            public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    modelToken = response.body();
                    if (StaticFunctions.SaveToken(getContext(), modelToken))
                        GetLoginInformation();
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelToken> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetLoginToken


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    if (StaticFunctions.SaveProfile(getContext(), response.body().getResult()))
                        SendMessageToObservable(StaticValues.ML_GoToHome);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetLoginInformation

}
