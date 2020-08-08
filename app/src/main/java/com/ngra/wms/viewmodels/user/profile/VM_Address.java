package com.ngra.wms.viewmodels.user.profile;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.MR_TimeSheet;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Address extends VM_Primary {

    private ArrayList<MD_SpinnerItem> address;

    public VM_Address(Activity context) {//_________________________________________________________ VM_Address
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Address



    public void GetAddress() {//____________________________________________________________________ GetAddress

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        retrofitComponent
                .getRetrofitApiInterface()
                .getContactAddresses(Authorization)
                .enqueue(new Callback<MR_SpinnerItems>() {
                    @Override
                    public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            address = response.body().getResult();
                            SendMessageToObservable(StaticValues.ML_GetAddress);
                        } else
                            SendMessageToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                        OnFailureRequest();
                    }
                });

    }//_____________________________________________________________________________________________ GetAddress




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





    public ArrayList<MD_SpinnerItem> getAddress() {//_______________________________________________ getAddress
        return address;
    }//_____________________________________________________________________________________________ getAddress


}
