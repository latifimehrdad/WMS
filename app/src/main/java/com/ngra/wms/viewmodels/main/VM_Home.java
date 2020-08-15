package com.ngra.wms.viewmodels.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_ScoreReport;
import com.ngra.wms.models.MR_ScoreReport;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ngra.wms.views.activitys.MainActivity.complateprofile;

public class VM_Home extends VM_Primary {

    private MD_ScoreReport md_scoreReport;


    public VM_Home(Activity context) {//____________________________________________________________ VM_Home
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Home



    public void CheckProfile() {//__________________________________________________________________ CheckProfile

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

    }//_____________________________________________________________________________________________ CheckProfile


    public int GetPackageState() {//________________________________________________________________ CheckGetPackage

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return 0;
        } else {
            return prefs.getInt(getContext().getString(R.string.ML_PackageRequestStatus), 0);
        }
    }//_____________________________________________________________________________________________ CheckGetPackage



    public boolean IsAddressCompleted() {//_________________________________________________________ IsAddressCompleted
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(getContext().getString(R.string.ML_AddressCompleted), false);
        }
    }//_____________________________________________________________________________________________ IsAddressCompleted



    public void GetScoreReport() {//________________________________________________________________ GetScoreReport

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


    }//_____________________________________________________________________________________________ GetScoreReport


    public MD_ScoreReport getMd_scoreReport() {//___________________________________________________ getMd_scoreReport
        return md_scoreReport;
    }//_____________________________________________________________________________________________ getMd_scoreReport


}
