package com.example.wms.viewmodels.user.profile;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.models.ModelSpinnerItems;
import com.example.wms.models.ModelUserAccounts;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.fragments.FragmentPrimary;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;
import static com.example.wms.utility.StaticFunctions.GetMessage;

public class VM_ProfileBank extends VM_Primary {

    private Context context;
    private ModelUserAccounts.ModelUserAccountNumber accountNumbers;
    private ArrayList<ModelSpinnerItem> banks;

    public VM_ProfileBank(Context context) {//______________________________________________________ VM_ProFileBank
        this.context = context;
    }//_____________________________________________________________________________________________ VM_ProFileBank



    public void SendAccountNumber(String AccountNumber, String BankId) {//__________________________ SendAccountNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .SendAccountNumber(
                        BankId,
                        AccountNumber,
                        Authorization)
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            setResponseMessage(GetMessage(response));
                            MainActivity.complateprofile = true;
                            getPublishSubject().onNext(StaticValues.ML_EditProfile);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });
    }//_____________________________________________________________________________________________ SendAccountNumber




    public void GetUserAccountNumber() {//__________________________________________________________ GetUserAccountNumber

        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getUserAccountNumber(
                        Authorization)
                .enqueue(new Callback<ModelUserAccounts>() {
                    @Override
                    public void onResponse(Call<ModelUserAccounts> call, Response<ModelUserAccounts> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            if (response.body().getResult() == null)
                                getPublishSubject().onNext(StaticValues.ML_GetAccountNumberNull);
                            else {
                                accountNumbers = response.body().getResult();
                                getPublishSubject().onNext(StaticValues.ML_GetAccountNumbers);
                            }
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelUserAccounts> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetUserAccountNumber




    public void GetBanks() {//______________________________________________________________________ GetBanks
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .getBanks(
                        Authorization)
                .enqueue(new Callback<ModelSpinnerItems>() {
                    @Override
                    public void onResponse(Call<ModelSpinnerItems> call, Response<ModelSpinnerItems> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            banks = response.body().getResult();
                            getPublishSubject().onNext(StaticValues.ML_GetBanks);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
                    }
                });
    }//_____________________________________________________________________________________________ GetBanks



    public ModelUserAccounts.ModelUserAccountNumber getAccountNumbers() {//_________________________ getAccountNumbers
        return accountNumbers;
    }//_____________________________________________________________________________________________ getAccountNumbers


    public ArrayList<ModelSpinnerItem> getBanks() {//_______________________________________________ getBanks
        return banks;
    }//_____________________________________________________________________________________________ getBanks


}
