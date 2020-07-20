package com.ngra.wms.viewmodels.callwithus;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_RequestSpinnerItems;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Support extends VM_Primary {

    private ArrayList<MD_SpinnerItem> md_spinnerItems;

    public VM_Support(Activity context) {//_________________________________________________________ VM_Support
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Support


    public void GetAllDepartments() {//_____________________________________________________________ GetAllDepartments

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAllCategories(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_RequestSpinnerItems>() {
            @Override
            public void onResponse(Call<MD_RequestSpinnerItems> call, Response<MD_RequestSpinnerItems> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_spinnerItems = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetAllDepartments);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_RequestSpinnerItems> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetAllDepartments




    public void SubmitTicket(
            String DepartmentId,
            String Subject,
            String Description,
            String CategoryId) {//__________________________________________________________________ SubmitTicket

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SubmitTicket(
                        DepartmentId,
                        Subject,
                        Description,
                        CategoryId,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response));
                    SendMessageToObservable(StaticValues.ML_Success);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SubmitTicket



    public ArrayList<MD_SpinnerItem> getMd_spinnerItems() {//_______________________________________ getMd_spinnerItems
        return md_spinnerItems;
    }//_____________________________________________________________________________________________ getMd_spinnerItems


}
