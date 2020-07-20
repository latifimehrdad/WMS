package com.ngra.wms.viewmodels.learn;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ItemLearn;
import com.ngra.wms.models.MD_RequestItemLearn;
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

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSummaryWasteNotice(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_RequestItemLearn>() {
            @Override
            public void onResponse(Call<MD_RequestItemLearn> call, Response<MD_RequestItemLearn> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    itemLearns = response.body().getItemLearns();
                    SendMessageToObservable(StaticValues.ML_GetItemLearn);
                }
                else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MD_RequestItemLearn> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetItems


    public List<MD_ItemLearn> getItemLearns() {//___________________________________________________ getItemLearns
        return itemLearns;
    }//_____________________________________________________________________________________________ getItemLearns


}
