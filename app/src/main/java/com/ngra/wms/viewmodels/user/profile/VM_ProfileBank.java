package com.ngra.wms.viewmodels.user.profile;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.ModelUserAccounts;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.activitys.MainActivity;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfileBank extends VM_Primary {

    private ModelUserAccounts.ModelUserAccountNumber accountNumbers;
    private ArrayList<MD_SpinnerItem> banks;
    private String AccountNumber;
    private String BankId;


    public VM_ProfileBank(Activity context) {//_____________________________________________________ VM_ProFileBank
        setContext(context);
    }//_____________________________________________________________________________________________ VM_ProFileBank


    public void SendAccountNumber() {//_____________________________________________________________ SendAccountNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SendAccountNumber(
                        getBankId(),
                        getAccountNumber(),
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    MainActivity.complateprofile = true;
                    sendActionToObservable(StaticValues.ML_EditProfile);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });
    }//_____________________________________________________________________________________________ SendAccountNumber


    public void GetUserAccountNumber() {//__________________________________________________________ GetUserAccountNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserAccountNumber(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelUserAccounts>() {
            @Override
            public void onResponse(Call<ModelUserAccounts> call, Response<ModelUserAccounts> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    if (response.body().getResult() == null)
                        sendActionToObservable(StaticValues.ML_GetAccountNumberNull);
                    else {
                        accountNumbers = response.body().getResult();
                        sendActionToObservable(StaticValues.ML_GetAccountNumbers);
                    }
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelUserAccounts> call, Throwable t) {
                onFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetUserAccountNumber


    public void GetBanks() {//______________________________________________________________________ GetBanks

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBanks(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_SpinnerItems>() {
            @Override
            public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    banks = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetBanks);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                onFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetBanks


    public ModelUserAccounts.ModelUserAccountNumber getAccountNumbers() {//_________________________ getAccountNumbers
        return accountNumbers;
    }//_____________________________________________________________________________________________ getAccountNumbers


    public ArrayList<MD_SpinnerItem> getBanks() {//_______________________________________________ getBanks
        return banks;
    }//_____________________________________________________________________________________________ getBanks

    public String getAccountNumber() {//____________________________________________________________ getAccountNumber
        return AccountNumber;
    }//_____________________________________________________________________________________________ getAccountNumber

    public void setAccountNumber(String accountNumber) {//_______________________________________________ setAccountNumber
        AccountNumber = accountNumber;
    }//_____________________________________________________________________________________________ setAccountNumber

    public String getBankId() {//___________________________________________________________________ getBankId
        return BankId;
    }//_____________________________________________________________________________________________ getBankId

    public void setBankId(String bankId) {//________________________________________________________ setBankId
        BankId = bankId;
    }//_____________________________________________________________________________________________ setBankId
}
