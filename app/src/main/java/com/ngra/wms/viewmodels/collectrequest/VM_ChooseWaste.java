package com.ngra.wms.viewmodels.collectrequest;

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


    public VM_ChooseWaste(Activity context) {//_____________________________________________________ VM_ChooseWaste
        setContext(context);
    }//_____________________________________________________________________________________________ VM_ChooseWaste



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

        getPrimaryCall().enqueue(new Callback<MR_ItemsWast>() {
            @Override
            public void onResponse(Call<MR_ItemsWast> call, Response<MR_ItemsWast> response) {
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
            public void onFailure(Call<MR_ItemsWast> call, Throwable t) {
                OnFailureRequest();
            }
        });



    }//_____________________________________________________________________________________________ GetItemsOfWast



    public List<MD_ItemWaste> getMd_itemWastes() {//________________________________________________ getMd_itemWastes
        return md_itemWastes;
    }//_____________________________________________________________________________________________ getMd_itemWastes


}