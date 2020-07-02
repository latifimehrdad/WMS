package com.example.wms.viewmodels.packrequest;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.models.ModelTimeSheetTimes;
import com.example.wms.models.ModelTimes;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_PackageRequestPrimary extends VM_Primary {

    private Context context;
    private ModelTimes modelTimes;

    public VM_PackageRequestPrimary(Context context) {//____________________________________________ VM_PackageRequestPrimary
        this.context = context;
    }//_____________________________________________________________________________________________ VM_PackageRequestPrimary


    public void SendPackageRequest(Integer timeId) {//______________________________________________ SendPackageRequest

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

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
                    setResponseMessage(GetMessage(response));
                    GetLoginInformation();
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);

            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ SendPackageRequest


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                String m = CheckResponse(response, true);
                if (m == null) {
                    if (StaticFunctions.SaveProfile(context, response.body().getResult()))
                        getPublishSubject().onNext(StaticValues.ML_SendPackageRequest);
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ GetLoginInformation


    public void GetTypeTimes() {//__________________________________________________________________ GetTypeTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<ModelTimeSheetTimes>() {
            @Override
            public void onResponse(Call<ModelTimeSheetTimes> call, Response<ModelTimeSheetTimes> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    modelTimes = response.body().getResult();
                    getPublishSubject().onNext(StaticValues.ML_GetTimeSheetTimes);
                } else {
                    setResponseMessage(CheckResponse(response, false));
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelTimeSheetTimes> call, Throwable t) {
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ GetTypeTimes


    public boolean IsPackageState() {//_____________________________________________________________ IsPackageState
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(context.getString(R.string.ML_IsPackageState), false);
        }
    }//_____________________________________________________________________________________________ IsPackageState



    public int GetPackageState() {//________________________________________________________________ CheckGetPackage

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return 0;
        } else {
            return prefs.getInt(context.getString(R.string.ML_PackageRequest), 0);
        }
    }//_____________________________________________________________________________________________ CheckGetPackage



    public ModelTimes getModelTimes() {//___________________________________________________________ getModelTimes
        return modelTimes;
    }//_____________________________________________________________________________________________ getModelTimes


}
