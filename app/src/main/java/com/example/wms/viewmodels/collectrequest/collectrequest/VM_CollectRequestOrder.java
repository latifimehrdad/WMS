package com.example.wms.viewmodels.collectrequest.collectrequest;

import android.app.Activity;
import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.MD_ItemWasteRequest;
import com.example.wms.models.MD_RequestItemsWast;
import com.example.wms.models.MD_RequestWasteRequest;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_CollectRequestOrder extends VM_Primary {


    private List<MD_ItemWasteRequest> md_itemWasteRequests;

    public VM_CollectRequestOrder(Activity context) {//_____________________________________________ VM_CollectRequestOrder
        setContext(context);
    }//_____________________________________________________________________________________________ VM_CollectRequestOrder



    public void GetItemsOfOrder() {//_______________________________________________________________ GetItemsOfOrder

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWasteRequests(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_RequestWasteRequest>() {
            @Override
            public void onResponse(Call<MD_RequestWasteRequest> call, Response<MD_RequestWasteRequest> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_itemWasteRequests = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_CollectOrderDone);
                }
                else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestWasteRequest> call, Throwable t) {
                OnFailureRequest();
            }
        });



    }//_____________________________________________________________________________________________ GetItemsOfOrder




    public List<MD_ItemWasteRequest> getMd_itemWasteRequests() {//__________________________________ getMd_itemWasteRequests
        if (md_itemWasteRequests == null)
            md_itemWasteRequests = new ArrayList<>();

        return md_itemWasteRequests;
    }//_____________________________________________________________________________________________ getMd_itemWasteRequests


}
