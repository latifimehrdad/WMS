package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemLearn;
import com.ngra.wms.models.MR_ItemLearn;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_LearnItem extends VM_Primary {

    private List<MD_ItemLearn> itemLearns;

    //______________________________________________________________________________________________ VM_LearnItem
    public VM_LearnItem(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_LearnItem


    //______________________________________________________________________________________________ getItems
    public void getItems() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSummaryWasteNotice(RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_ItemLearn>() {
            @Override
            public void onResponse(Call<MR_ItemLearn> call, Response<MR_ItemLearn> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    itemLearns = response.body().getItemLearns();
                    sendActionToObservable(StaticValues.ML_GetItemLearn);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ItemLearn> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getItems


    //______________________________________________________________________________________________ getItemLearns
    public List<MD_ItemLearn> getItemLearns() {
        return itemLearns;
    }
    //______________________________________________________________________________________________ getItemLearns


}
