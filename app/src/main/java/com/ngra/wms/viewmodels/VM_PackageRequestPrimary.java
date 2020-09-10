package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelPackage;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_PackageRequestPrimary extends VM_Primary {


    //______________________________________________________________________________________________ VM_PackageRequestPrimary
    public VM_PackageRequestPrimary(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_PackageRequestPrimary


    //______________________________________________________________________________________________ sendPackageRequest
    public void sendPackageRequest(Integer timeId) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendPackageRequest(
                        timeId,
                        Authorization));

        if (getPrimaryCall() == null)
            return;


        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    getLoginInformation();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendPackageRequest


    //______________________________________________________________________________________________ getLoginInformation
    public void getLoginInformation() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                String m = checkResponse(response, true);
                if (m == null) {
                    ApplicationUtility utility = ApplicationWMS.getApplicationWMS(getContext())
                            .getUtilityComponent().getApplicationUtility();
                    if (utility.saveProfile(getContext(), response.body().getResult()))
                        sendActionToObservable(StaticValues.ML_SendPackageRequest);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
                ;
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getLoginInformation


    //______________________________________________________________________________________________ getTypeTimes
    public void getTypeTimes() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MD_TimeSheet>() {
            @Override
            public void onResponse(Call<MD_TimeSheet> call, Response<MD_TimeSheet> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
//                    MRTimes = response.body().;
                    sendActionToObservable(StaticValues.ML_GetTimeSheet);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MD_TimeSheet> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getTypeTimes


    //______________________________________________________________________________________________ getPackageStatus
    public Byte getPackageStatus() {

        if (getContext() != null) {
            SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
            if (prefs == null) {
                return 0;
            } else {
                return (byte) prefs.getInt(getContext().getString(R.string.ML_PackageRequestStatus), 0);
            }
        }
        return 0;
    }
    //______________________________________________________________________________________________ getPackageStatus



    //______________________________________________________________________________________________ packageRequestDate
    public ModelPackage packageRequestDate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            ModelPackage modelPackage = new ModelPackage();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            String sDate = prefs.getString(context.getString(R.string.ML_PackageRequestDate), null);
            Date date;
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setRequestDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            sDate = prefs.getString(context.getString(R.string.ML_PackageRequestFrom), null);
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setFromDeliver(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            sDate = prefs.getString(context.getString(R.string.ML_PackageRequestTo), null);
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setToDeliver(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return modelPackage;
        } else
            return null;
    }
    //______________________________________________________________________________________________ packageRequestDate


}
