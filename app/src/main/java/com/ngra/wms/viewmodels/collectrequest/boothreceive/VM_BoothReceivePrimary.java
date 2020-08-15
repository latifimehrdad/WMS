package com.ngra.wms.viewmodels.collectrequest.boothreceive;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MR_BoothList;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_BoothReceivePrimary extends VM_Primary {

    private List<MD_Booth> boothList;

    public VM_BoothReceivePrimary(Activity context) {//_____________________________________________ VM_BoothReceivePrimary
        setContext(context);
    }//_____________________________________________________________________________________________ VM_BoothReceivePrimary


/*
    public void GetTypeTimes() {//__________________________________________________________________ GetTypeTimes

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization));

        getPrimaryCall().enqueue(new Callback<ModelTimeSheetTimes>() {
            @Override
            public void onResponse(Call<ModelTimeSheetTimes> call, Response<ModelTimeSheetTimes> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    modelTimes = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetTimeSheetTimes);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelTimeSheetTimes> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetTypeTimes
*/




    public void GetBoothList() {//__________________________________________________________________ GetBoothList

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBoothList(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_BoothList>() {
            @Override
            public void onResponse(Call<MR_BoothList> call, Response<MR_BoothList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    boothList = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetBoothList);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_BoothList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetBoothList




    public void SendCollectRequest(MD_WasteAmountRequests md_wasteAmountRequests) {//_______________ SendCollectRequest

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .RequestCollection(
                        md_wasteAmountRequests,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_CollectRequestDone);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendCollectRequest





/*
    public ModelTimes getModelTimes() {//___________________________________________________________ getModelTimes
        return modelTimes;
    }//_____________________________________________________________________________________________ getModelTimes
*/


    public List<MD_Booth> getBoothList() {//________________________________________________________ getBoothList
        return boothList;
    }//_____________________________________________________________________________________________ getBoothList
}
