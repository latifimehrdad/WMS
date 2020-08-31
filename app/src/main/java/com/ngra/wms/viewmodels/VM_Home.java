package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ScoreReport;
import com.ngra.wms.models.MR_ScoreReport;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ngra.wms.views.activitys.MainActivity.complateprofile;

public class VM_Home extends VM_Primary {

    private MD_ScoreReport md_scoreReport;


    //______________________________________________________________________________________________ VM_Home
    public VM_Home(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Home


    //______________________________________________________________________________________________ checkProfile
    public void checkProfile() {

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            complateprofile = false;
        } else {
            complateprofile = prefs.getBoolean(getContext().getString(R.string.ML_CompleteProfile), false);
        }

        if (!complateprofile) {
            Handler handler = new Handler();
            handler.postDelayed(() -> sendActionToObservable(StaticValues.ML_GotoProfile), 10);
        }
    }
    //______________________________________________________________________________________________ checkProfile




    //______________________________________________________________________________________________ checkProfile
    public String getPhoneNumber() {

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return "";
        } else {
            return prefs.getString(getContext().getString(R.string.ML_PhoneNumber), "");
        }
    }
    //______________________________________________________________________________________________ checkProfile




    //______________________________________________________________________________________________ getPackageState
    public int getPackageState() {

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return 0;
        } else {
            return prefs.getInt(getContext().getString(R.string.ML_PackageRequestStatus), 0);
        }
    }
    //______________________________________________________________________________________________ getPackageState


    //______________________________________________________________________________________________ isAddressCompleted
    public boolean isAddressCompleted() {
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(getContext().getString(R.string.ML_AddressCompleted), false);
        }
    }
    //______________________________________________________________________________________________ isAddressCompleted


    //______________________________________________________________________________________________ getScoreReport
    public void getScoreReport() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getScoreReport(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ScoreReport>() {
            @Override
            public void onResponse(Call<MR_ScoreReport> call, Response<MR_ScoreReport> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_scoreReport = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetUserScore);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ScoreReport> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getScoreReport


    //______________________________________________________________________________________________ getMd_scoreReport
    public MD_ScoreReport getMd_scoreReport() {
        return md_scoreReport;
    }
    //______________________________________________________________________________________________ getMd_scoreReport


}
