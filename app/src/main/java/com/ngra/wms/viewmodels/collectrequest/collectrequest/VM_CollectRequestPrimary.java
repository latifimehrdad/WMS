package com.ngra.wms.viewmodels.collectrequest.collectrequest;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.models.MR_TimeSheet;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_CollectRequestPrimary extends VM_Primary {


/*    private List<MD_ItemWaste> md_itemWastes;*/
/*    private MR_TimeSheet MRTimes;*/
    private ArrayList<MD_SpinnerItem> WasteEstimates;


    public VM_CollectRequestPrimary(Activity context) {//___________________________________________ VM_CollectRequestPrimary
        setContext(context);
    }//_____________________________________________________________________________________________ VM_CollectRequestPrimary



/*
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
*/




    public void GetTypeTimes() {//__________________________________________________________________ GetTypeTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<MD_TimeSheet>() {
            @Override
            public void onResponse(Call<MD_TimeSheet> call, Response<MD_TimeSheet> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
//                    MRTimes = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MD_TimeSheet> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetTypeTimes




    public void GetVolumeList() {//_________________________________________________________________ GetVolumeList

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        Call<MR_SpinnerItems> call = retrofitComponent
                .getRetrofitApiInterface()
                .getWasteEstimateList(
                        Authorization);

        call.enqueue(new Callback<MR_SpinnerItems>() {
            @Override
            public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    WasteEstimates = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetVolume);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetVolumeList




    public void SendCollectRequest(MD_WasteAmountRequests md_wasteAmountRequests) {//_______________ SendCollectRequest

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .RequestCollection(
                        md_wasteAmountRequests,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response.body()));
                    SendMessageToObservable(StaticValues.ML_CollectRequestDone);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendCollectRequest




/*
    public MR_TimeSheet getMRTimes() {//___________________________________________________________ getModelTimes

        return MRTimes;
    }//_____________________________________________________________________________________________ getModelTimes
*/



/*
    public List<MD_ItemWaste> getMd_itemWastes() {//________________________________________________ getMd_itemWasts
        if (md_itemWastes == null)
            md_itemWastes = new ArrayList<>();

        return md_itemWastes;
    }//_____________________________________________________________________________________________ getMd_itemWasts
*/


    public ArrayList<MD_SpinnerItem> getWasteEstimates() {//________________________________________ getWasteEstimates
        return WasteEstimates;
    }//_____________________________________________________________________________________________ getWasteEstimates

}
