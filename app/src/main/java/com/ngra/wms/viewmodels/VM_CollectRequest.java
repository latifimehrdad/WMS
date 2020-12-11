package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
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


    //______________________________________________________________________________________________ VM_CollectRequest
    public VM_CollectRequest(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_CollectRequest


    //______________________________________________________________________________________________ getScoreList
    public void getScoreList() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getScoreList2(RetrofitApis.app_token,aToken,Authorization));

        if (getPrimaryCall() == null)
            return;

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


    }
    //______________________________________________________________________________________________ getScoreList


    //______________________________________________________________________________________________ getScoreBooth
    public List<String> getScoreBooth() {
        return ScoreBooth;
    }
    //______________________________________________________________________________________________ getScoreBooth


    //______________________________________________________________________________________________ getScoreVehicle
    public List<String> getScoreVehicle() {
        return ScoreVehicle;
    }
    //______________________________________________________________________________________________ getScoreVehicle

}
