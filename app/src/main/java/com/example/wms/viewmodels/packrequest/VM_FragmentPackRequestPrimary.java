package com.example.wms.viewmodels.packrequest;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelHousingBuildings;
import com.example.wms.models.ModelTimeSheetTimes;
import com.example.wms.models.ModelTimes;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_FragmentPackRequestPrimary {

    private Context context;
    private PublishSubject<Byte> Observables;
    private String MessageResponse;
    private ModelTimes modelTimes;

    public VM_FragmentPackRequestPrimary(Context context) {//_______________________________________ VM_FragmentPackRequestPrimary
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentPackRequestPrimary



    public void GetTypeTimes() {//__________________________________________________________________ GetTypeTimes

        StaticFunctions.isCancel = false;
        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getTimes(Authorization)
                .enqueue(new Callback<ModelTimeSheetTimes>() {
                    @Override
                    public void onResponse(Call<ModelTimeSheetTimes> call, Response<ModelTimeSheetTimes> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            modelTimes = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetTimeSheetTimes);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);

                    }

                    @Override
                    public void onFailure(Call<ModelTimeSheetTimes> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetTypeTimes



    public boolean IsPackageState() {//_____________________________________________________________ IsPackageState
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(context.getString(R.string.ML_IsPackageState), false);
        }
    }//_____________________________________________________________________________________________ IsPackageState



    public int GetPackageState() {//________________________________________________________________ CheckGetPackage

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return 0;
        } else {
            return prefs.getInt(context.getString(R.string.ML_PackageRequest), 0);
        }
    }//_____________________________________________________________________________________________ CheckGetPackage





    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables


    public ModelTimes getModelTimes() {//___________________________________________________________ getModelTimes
        return modelTimes;
    }//_____________________________________________________________________________________________ getModelTimes
}
