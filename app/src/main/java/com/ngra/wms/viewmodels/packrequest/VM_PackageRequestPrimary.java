package com.ngra.wms.viewmodels.packrequest;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.ModelTimeSheetTimes;
import com.ngra.wms.models.ModelTimes;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_PackageRequestPrimary extends VM_Primary {

    private ModelTimes modelTimes;

    public VM_PackageRequestPrimary(Activity context) {//___________________________________________ VM_PackageRequestPrimary
        setContext(context);
    }//_____________________________________________________________________________________________ VM_PackageRequestPrimary


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
                if (StaticFunctions.isCancel)
                    return;
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


    public void GetTypeTimes() {//__________________________________________________________________ GetTypeTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<ModelTimeSheetTimes>() {
            @Override
            public void onResponse(Call<ModelTimeSheetTimes> call, Response<ModelTimeSheetTimes> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    modelTimes = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetTimeSheetTimes);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelTimeSheetTimes> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetTypeTimes


    public Byte GetPackageStatus() {//_______________________________________________________________ GetPackageStatus

        if (getContext() != null) {
            SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
            if (prefs == null) {
                return 0;
            } else {
                return (byte) prefs.getInt(getContext().getString(R.string.ML_PackageRequestStatus), 0);
            }
        }
        return 0;
    }//_____________________________________________________________________________________________ GetPackageStatus



    public ModelTimes getModelTimes() {//___________________________________________________________ getModelTimes
        return modelTimes;
    }//_____________________________________________________________________________________________ getModelTimes

}
