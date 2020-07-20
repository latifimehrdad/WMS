package com.ngra.wms.viewmodels.collectrequest.collectrequest;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.models.MD_RequestItemsWast;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_CollectRequestPrimary extends VM_Primary {


    private List<MD_ItemWaste> md_itemWastes;


    public VM_CollectRequestPrimary(Activity context) {//___________________________________________ VM_CollectRequestPrimary
        setContext(context);
    }//_____________________________________________________________________________________________ VM_CollectRequestPrimary



    public void GetItemsOfWast() {//________________________________________________________________ GetItemsOfWast

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWasteList(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_RequestItemsWast>() {
            @Override
            public void onResponse(Call<MD_RequestItemsWast> call, Response<MD_RequestItemsWast> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_itemWastes = response.body().getItemsWast();
                    SendMessageToObservable(StaticValues.ML_GetItemsOfWasteIsSuccess);
                }
                else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestItemsWast> call, Throwable t) {
                OnFailureRequest();
            }
        });



    }//_____________________________________________________________________________________________ GetItemsOfWast



    public List<MD_ItemWaste> getMd_itemWastes() {//__________________________________________________ getMd_itemWasts
        if (md_itemWastes == null)
            md_itemWastes = new ArrayList<>();

        return md_itemWastes;
    }//_____________________________________________________________________________________________ getMd_itemWasts
}
