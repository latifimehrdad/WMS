package com.example.wms.viewmodels.user.profile;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelBuildingRenovationCode;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;
import static com.example.wms.utility.StaticFunctions.GetMessage;

public class VM_FragmentProfileCode {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private String BuildingRenovationCode;
    private String MessageResponse;

    public VM_FragmentProfileCode(Context context) {//______________________________________________ VM_FragmentProfileCode
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentProfileCode



    public void SendCode() {//______________________________________________________________________ SendCode
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .SendBuildingRenovationCode(
                        getBuildingRenovationCode(),
                        Authorization)
                .enqueue(new Callback<ModelResponsePrimary>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            MessageResponse = GetMessage(response);
                            MainActivity.complateprofile = true;
                            Observables.onNext(StaticValues.ML_EditProfile);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });
    }//_____________________________________________________________________________________________ SendCode


    public void GetCode() {//_______________________________________________________________________ GetCode

        StaticFunctions.isCancel = false;

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
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            if (response.body().getResult() == null)
                                Observables.onNext(StaticValues.ML_GetAccountNumberNull);
                            else {
                                MessageResponse = response.body().getResult();
                                Observables.onNext(StaticValues.ML_GetRenovationCode);
                            }
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelBuildingRenovationCode> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetCode


    public String getBuildingRenovationCode() {//___________________________________________________ getBuildingRenovationCode
        return BuildingRenovationCode;
    }//_____________________________________________________________________________________________ getBuildingRenovationCode


    public void setBuildingRenovationCode(String buildingRenovationCode) {//________________________ setBuildingRenovationCode
        BuildingRenovationCode = buildingRenovationCode;
    }//_____________________________________________________________________________________________ setBuildingRenovationCode


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables
}
