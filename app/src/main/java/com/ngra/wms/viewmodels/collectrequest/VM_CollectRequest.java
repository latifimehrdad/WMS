package com.ngra.wms.viewmodels.collectrequest;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MR_ScoreList2;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_CollectRequest extends VM_Primary {


    private List<String> ScoreBooth;

    private List<String> ScoreVehicle;


    public VM_CollectRequest(Activity context) {//__________________________________________________ VM_CollectRequest
        setContext(context);
    }//_____________________________________________________________________________________________ VM_CollectRequest


    public void GetScoreList() {//__________________________________________________________________ GetScoreList

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getScoreList2(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ScoreList2>() {
            @Override
            public void onResponse(Call<MR_ScoreList2> call, Response<MR_ScoreList2> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    ScoreBooth = response.body().getResult().getBooth();
                    ScoreVehicle = response.body().getResult().getVehicle();
                    sendActionToObservable(StaticValues.ML_GetUserScore);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ScoreList2> call, Throwable t) {
                onFailureRequest();
            }
        });


    }//_____________________________________________________________________________________________ GetScoreList




    public List<String> getScoreBooth() {//_________________________________________________________ getScoreBooth
        return ScoreBooth;
    }//_____________________________________________________________________________________________ getScoreBooth



    public List<String> getScoreVehicle() {//_______________________________________________________ getScoreVehicle
        return ScoreVehicle;
    }//_____________________________________________________________________________________________ getScoreVehicle

}
