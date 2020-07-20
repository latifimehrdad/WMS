package com.ngra.wms.viewmodels.user.profile;

import android.app.Activity;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelBuildingRenovationCode;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.activitys.MainActivity;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfileCode extends VM_Primary {

    private String BuildingRenovationCode;

    public VM_ProfileCode(Activity context) {//_____________________________________________________ VM_ProfileCode
        setContext(context);
    }//_____________________________________________________________________________________________ VM_ProfileCode


    public void SendCode() {//_________________________________________ SendCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendBuildingRenovationCode(
                        BuildingRenovationCode,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response));
                    MainActivity.complateprofile = true;
                    SendMessageToObservable(StaticValues.ML_EditProfile);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });
    }//_____________________________________________________________________________________________ SendCode


    public void GetCode() {//_______________________________________________________________________ GetCode

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBuildingRenovationCode(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelBuildingRenovationCode>() {
            @Override
            public void onResponse(Call<ModelBuildingRenovationCode> call, Response<ModelBuildingRenovationCode> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    if (response.body().getResult() == null)
                        SendMessageToObservable(StaticValues.ML_GetAccountNumberNull);
                    else {
                        setResponseMessage(response.body().getResult());
                        SendMessageToObservable(StaticValues.ML_GetRenovationCode);
                    }
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelBuildingRenovationCode> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetCode


    public void setBuildingRenovationCode(String buildingRenovationCode) {//________________________ setBuildingRenovationCode
        BuildingRenovationCode = buildingRenovationCode;
    }//_____________________________________________________________________________________________ setBuildingRenovationCode
}
