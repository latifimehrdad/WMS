package com.ngra.wms.viewmodels.collectrequest.recyclingcar;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.models.MR_TimeSheet;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_RecyclingCarPrimary extends VM_Primary {

//    private MR_TimeSheet MRTimes;

    public VM_RecyclingCarPrimary(Activity context) {//______________________________________________ VM_RecyclingCarPrimary
        setContext(context);
    }//_____________________________________________________________________________________________ VM_RecyclingCarPrimary

    public void GetTypeTimes() {//__________________________________________________________________ GetTypeTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<MD_TimeSheet>() {
            @Override
            public void onResponse(Call<MD_TimeSheet> call, Response<MD_TimeSheet> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
//                    MRTimes = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MD_TimeSheet> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetTypeTimes



    public void SendCollectRequest(MD_WasteAmountRequests md_wasteAmountRequests) {//_______________ SendCollectRequest

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .RequestCollection(
                        md_wasteAmountRequests,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response.body()));
                    SendMessageToObservable(StaticValues.ML_CollectRequestDone);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendCollectRequest



/*
    public MR_TimeSheet getMRTimes() {//___________________________________________________________ getModelTimes
        return MRTimes;
    }//_____________________________________________________________________________________________ getModelTimes
*/

}
