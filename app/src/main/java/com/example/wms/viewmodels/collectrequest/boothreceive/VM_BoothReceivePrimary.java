package com.example.wms.viewmodels.collectrequest.boothreceive;

import android.app.Activity;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.MD_Booth;
import com.example.wms.models.MD_RequestBoothList;
import com.example.wms.models.MD_WasteAmountRequests;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.models.ModelTimeSheetTimes;
import com.example.wms.models.ModelTimes;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_BoothReceivePrimary extends VM_Primary {

    private ModelTimes modelTimes;
    private List<MD_Booth> boothList;

    public VM_BoothReceivePrimary(Activity context) {//_____________________________________________ VM_BoothReceivePrimary
        setContext(context);
    }//_____________________________________________________________________________________________ VM_BoothReceivePrimary


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




    public void GetBoothList() {//__________________________________________________________________ GetBoothList

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBoothList(Authorization));

        getPrimaryCall().enqueue(new Callback<MD_RequestBoothList>() {
            @Override
            public void onResponse(Call<MD_RequestBoothList> call, Response<MD_RequestBoothList> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    boothList = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetBoothList);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MD_RequestBoothList> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetBoothList




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
                    setResponseMessage(GetMessage(response));
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





    public ModelTimes getModelTimes() {//___________________________________________________________ getModelTimes
        return modelTimes;
    }//_____________________________________________________________________________________________ getModelTimes


    public List<MD_Booth> getBoothList() {//________________________________________________________ getBoothList
        return boothList;
    }//_____________________________________________________________________________________________ getBoothList
}
