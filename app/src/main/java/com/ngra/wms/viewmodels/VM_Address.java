package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Address extends VM_Primary {

    private ArrayList<MD_SpinnerItem> address;

    //______________________________________________________________________________________________ VM_Address
    public VM_Address(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Address


    //______________________________________________________________________________________________ getContactAddress
    public void getContactAddress() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        retrofitComponent
                .getRetrofitApiInterface()
                .getContactAddresses(RetrofitApis.app_token,aToken,authorization)
                .enqueue(new Callback<MR_SpinnerItems>() {
                    @Override
                    public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            address = response.body().getResult();
                            sendActionToObservable(StaticValues.ML_GetAddress);
                        } else
                            sendActionToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                        onFailureRequest();
                    }
                });

    }
    //______________________________________________________________________________________________ getContactAddress


    //______________________________________________________________________________________________ sendCollectRequest
    public void sendCollectRequest(MD_WasteAmountRequests md_wasteAmountRequests) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .RequestCollection(
                        md_wasteAmountRequests,
                        RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

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


    //______________________________________________________________________________________________ getAddress
    public ArrayList<MD_SpinnerItem> getAddress() {
        return address;
    }
    //______________________________________________________________________________________________ getAddress


}
