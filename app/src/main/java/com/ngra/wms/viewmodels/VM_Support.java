package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Support extends VM_Primary {

    private ArrayList<MD_SpinnerItem> md_spinnerItems;

    //______________________________________________________________________________________________ VM_Support
    public VM_Support(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Support


    //______________________________________________________________________________________________ getAllDepartments
    public void getAllDepartments() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAllCategories(
                        Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_SpinnerItems>() {
            @Override
            public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_spinnerItems = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetAllDepartments);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getAllDepartments


    //______________________________________________________________________________________________ submitTicket
    public void submitTicket(
            String DepartmentId,
            String Subject,
            String Description,
            String CategoryId) {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SubmitTicket(
                        DepartmentId,
                        Subject,
                        Description,
                        CategoryId,
                        Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.ML_Success);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ submitTicket


    //______________________________________________________________________________________________ getMd_spinnerItems
    public ArrayList<MD_SpinnerItem> getMd_spinnerItems() {
        return md_spinnerItems;
    }
    //______________________________________________________________________________________________ getMd_spinnerItems


}
