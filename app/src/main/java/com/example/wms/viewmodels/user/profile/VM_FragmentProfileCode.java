package com.example.wms.viewmodels.user.profile;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelBuildingRenovationCode;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.models.ModelUserAccounts;
import com.example.wms.utility.StaticFunctions;
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
    private PublishSubject<String> Observables = null;
    private String BuildingRenovationCode;
    private String MessageResponcse;

    public VM_FragmentProfileCode(Context context) {//_______________________________________ Start VM_FragmentProfileCode
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_FragmentProfileCode



    public void SendCode() {//______________________________________________________________________ Start SendCode
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
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            MessageResponcse = GetMessage(response);
                            MainActivity.complateprofile = true;
                            Observables.onNext("SuccessfulEdit");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });
    }//_____________________________________________________________________________________________ End SendCode


    public void GetCode() {//_______________________________________________________________________ Start GetCode

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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            if (response.body().getResult() == null)
                                Observables.onNext("SuccessfulNull");
                            else {
                                MessageResponcse = response.body().getResult();
                                Observables.onNext("SuccessfulGetCode");
                            }
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelBuildingRenovationCode> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetCode


    public String getBuildingRenovationCode() {//___________________________________________________ Start getBuildingRenovationCode
        return BuildingRenovationCode;
    }//_____________________________________________________________________________________________ End getBuildingRenovationCode


    public void setBuildingRenovationCode(String buildingRenovationCode) {//________________________ Start setBuildingRenovationCode
        BuildingRenovationCode = buildingRenovationCode;
    }//_____________________________________________________________________________________________ End setBuildingRenovationCode


    public String getMessageResponcse() {//_________________________________________________________ Start getMessageResponcse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponcse


    public PublishSubject<String> getObservables() {//______________________________________________ Start getObservables
        return Observables;
    }//_____________________________________________________________________________________________ End getObservables
}
