package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemWasteRequest;
import com.ngra.wms.models.MR_WasteRequest;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_CollectRequestOrder extends VM_Primary {


    private List<MD_ItemWasteRequest> md_itemWasteRequests;

    //______________________________________________________________________________________________ VM_CollectRequestOrder
    public VM_CollectRequestOrder(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_CollectRequestOrder


    //______________________________________________________________________________________________ getItemsOfOrder
    public void getItemsOfOrder() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWasteRequests(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_WasteRequest>() {
            @Override
            public void onResponse(Call<MR_WasteRequest> call, Response<MR_WasteRequest> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_itemWasteRequests = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_CollectOrderDone);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_WasteRequest> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ getItemsOfOrder



    //______________________________________________________________________________________________ cancelRequestWaste
    public void cancelRequestWaste(Integer position) {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .WasteCollectionCanceled(
                        getMd_itemWasteRequests().get(position).getRequestCode(),
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_CancelRequest);
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
    //______________________________________________________________________________________________ cancelRequestWaste





    //______________________________________________________________________________________________ getMd_itemWasteRequests
    public List<MD_ItemWasteRequest> getMd_itemWasteRequests() {
        if (md_itemWasteRequests == null)
            md_itemWasteRequests = new ArrayList<>();

        return md_itemWasteRequests;
    }
    //______________________________________________________________________________________________ getMd_itemWasteRequests


}
