package com.example.wms.viewmodels.user.profile;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelBuildingRenovationCode;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;
import static com.example.wms.utility.StaticFunctions.GetMessage;

public class VM_ProfileCode extends VM_Primary {

    private Context context;

    public VM_ProfileCode(Context context) {//______________________________________________________ VM_ProfileCode
        this.context = context;
    }//_____________________________________________________________________________________________ VM_ProfileCode


    public void SendCode(String BuildingRenovationCode) {//_________________________________________ SendCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .SendBuildingRenovationCode(
                        BuildingRenovationCode,
                        Authorization)
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            setResponseMessage(GetMessage(response));
                            MainActivity.complateprofile = true;
                            getPublishSubject().onNext(StaticValues.ML_EditProfile);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });
    }//_____________________________________________________________________________________________ SendCode


    public void GetCode() {//_______________________________________________________________________ GetCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getBuildingRenovationCode(
                        Authorization)
                .enqueue(new Callback<ModelBuildingRenovationCode>() {
                    @Override
                    public void onResponse(Call<ModelBuildingRenovationCode> call, Response<ModelBuildingRenovationCode> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            if (response.body().getResult() == null)
                                getPublishSubject().onNext(StaticValues.ML_GetAccountNumberNull);
                            else {
                                setResponseMessage(response.body().getResult());
                                getPublishSubject().onNext(StaticValues.ML_GetRenovationCode);
                            }
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelBuildingRenovationCode> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetCode


}
