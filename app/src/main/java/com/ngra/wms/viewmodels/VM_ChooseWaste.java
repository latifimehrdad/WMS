package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.models.MR_ItemsWast;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_ChooseWaste extends VM_Primary {


    private List<MD_ItemWaste> md_itemWastes;


    //______________________________________________________________________________________________ VM_ChooseWaste
    public VM_ChooseWaste(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_ChooseWaste


    //______________________________________________________________________________________________ getItemsOfWast
    public void getItemsOfWast() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWasteList(
                        authorization));

        getPrimaryCall().enqueue(new Callback<MR_ItemsWast>() {
            @Override
            public void onResponse(Call<MR_ItemsWast> call, Response<MR_ItemsWast> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_itemWastes = response.body().getItemsWast();
                    sendActionToObservable(StaticValues.ML_GetItemsOfWasteIsSuccess);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ItemsWast> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ getItemsOfWast


    //______________________________________________________________________________________________ getMd_itemWastes
    public List<MD_ItemWaste> getMd_itemWastes() {
        return md_itemWastes;
    }
    //______________________________________________________________________________________________ getMd_itemWastes


}
