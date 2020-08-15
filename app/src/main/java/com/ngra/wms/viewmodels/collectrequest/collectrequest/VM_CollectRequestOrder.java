package com.ngra.wms.viewmodels.collectrequest.collectrequest;

import android.app.Activity;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemWasteRequest;
import com.ngra.wms.models.MR_WasteRequest;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

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
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_WasteRequest> call, Throwable t) {
                onFailureRequest();
            }
        });



    }//_____________________________________________________________________________________________ GetItemsOfOrder




    public List<MD_ItemWasteRequest> getMd_itemWasteRequests() {//__________________________________ getMd_itemWasteRequests
        if (md_itemWasteRequests == null)
            md_itemWasteRequests = new ArrayList<>();

        return md_itemWasteRequests;
    }//_____________________________________________________________________________________________ getMd_itemWasteRequests


}
