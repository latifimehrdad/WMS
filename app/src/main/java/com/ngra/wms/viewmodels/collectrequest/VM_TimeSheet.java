package com.ngra.wms.viewmodels.collectrequest;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_Time;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.models.MR_TimeSheet;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_TimeSheet extends VM_Primary {

    private List<MD_TimeSheet> md_timeSheets;


    public VM_TimeSheet(Activity context) {//_______________________________________________________ VM_TimeSheet
        setContext(context);
    }//_____________________________________________________________________________________________ VM_TimeSheet




    public void GetVehicleTimes() {//_______________________________________________________________ GetVehicleTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getVehicleTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TimeSheet>() {
            @Override
            public void onResponse(Call<MR_TimeSheet> call, Response<MR_TimeSheet> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_timeSheets = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_TimeSheet> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetVehicleTimes






    public void GetBoothTimes() {//_________________________________________________________________ GetBoothTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBoothTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TimeSheet>() {
            @Override
            public void onResponse(Call<MR_TimeSheet> call, Response<MR_TimeSheet> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_timeSheets = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_TimeSheet> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetBoothTimes




    public void SendPackageRequest(Integer timeId) {//______________________________________________ SendPackageRequest

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPackageRequest(
                        timeId,
                        Authorization));
        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response.body()));
                    GetLoginInformation();
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendPackageRequest




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
                String m = CheckResponse(response, true);
                if (m == null) {
                    if (StaticFunctions.SaveProfile(getContext(), response.body().getResult()))
                        SendMessageToObservable(StaticValues.ML_SendPackageRequest);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);;
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetLoginInformation



    public List<MD_TimeSheet> getMd_timesSheet() {//_____________________________________________________ getMd_times
        return md_timeSheets;
    }//_____________________________________________________________________________________________ getMd_times



}
