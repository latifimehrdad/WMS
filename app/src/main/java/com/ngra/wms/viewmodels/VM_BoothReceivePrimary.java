package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MR_BoothList;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_BoothReceivePrimary extends VM_Primary {

    private List<MD_Booth> md_boothList;

    //______________________________________________________________________________________________ VM_BoothReceivePrimary
    public VM_BoothReceivePrimary(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_BoothReceivePrimary


    //______________________________________________________________________________________________ getBoothList
    public void getBoothList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBoothList(authorization));

        getPrimaryCall().enqueue(new Callback<MR_BoothList>() {
            @Override
            public void onResponse(Call<MR_BoothList> call, Response<MR_BoothList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_boothList = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetBoothList);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_BoothList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getBoothList


    //______________________________________________________________________________________________ sendCollectRequest
    public void sendCollectRequest(MD_WasteAmountRequests md_wasteAmountRequests) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .RequestCollection(
                        md_wasteAmountRequests,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_CollectRequestDone);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendCollectRequest


    //______________________________________________________________________________________________ getMd_boothList
    public List<MD_Booth> getMd_boothList() {
        return md_boothList;
    }
    //______________________________________________________________________________________________ getMd_boothList
}
