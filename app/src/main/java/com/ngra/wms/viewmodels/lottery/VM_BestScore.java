package com.ngra.wms.viewmodels.lottery;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_BestScore;
import com.ngra.wms.models.MR_BestScore;
import com.ngra.wms.models.MR_GiveScore;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_BestScore extends VM_Primary {


    private List<MD_BestScore> md_bestScores;


    public VM_BestScore(Activity context) {//_______________________________________________________ VM_BestScore
        setContext(context);
    }//_____________________________________________________________________________________________ VM_BestScore



    public void GetBestScore() {//__________________________________________________________________ GetBestScore

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .gettopscorelist(Authorization));

        getPrimaryCall().enqueue(new Callback<MR_BestScore>() {
            @Override
            public void onResponse(Call<MR_BestScore> call, Response<MR_BestScore> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_bestScores = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetBestScore);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_BestScore> call, Throwable t) {
                OnFailureRequest();
            }
        });


    }//_____________________________________________________________________________________________ GetBestScore




    public List<MD_BestScore> getMd_bestScores() {//________________________________________________ getMd_bestScores
        return md_bestScores;
    }//_____________________________________________________________________________________________ getMd_bestScores




}
