package com.ngra.wms.viewmodels.learn;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemLearn;
import com.ngra.wms.models.MR_ItemLearn;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_LearnItem extends VM_Primary {

    private List<MD_ItemLearn> itemLearns;

    public VM_LearnItem(Activity context) {//_______________________________________________________ VM_LearnItem
        setContext(context);
    }//_____________________________________________________________________________________________ VM_LearnItem


    public void GetItems() {//______________________________________________________________________ GetItems

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSummaryWasteNotice(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ItemLearn>() {
            @Override
            public void onResponse(Call<MR_ItemLearn> call, Response<MR_ItemLearn> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    itemLearns = response.body().getItemLearns();
                    sendActionToObservable(StaticValues.ML_GetItemLearn);
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ItemLearn> call, Throwable t) {
                onFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetItems


    public List<MD_ItemLearn> getItemLearns() {//___________________________________________________ getItemLearns
        return itemLearns;
    }//_____________________________________________________________________________________________ getItemLearns


}
