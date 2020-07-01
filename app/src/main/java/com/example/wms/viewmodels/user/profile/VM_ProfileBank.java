package com.example.wms.viewmodels.user.profile;

import android.content.Context;

import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.models.ModelSpinnerItems;
import com.example.wms.models.ModelUserAccounts;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfileBank extends VM_Primary {

    private Context context;
    private ModelUserAccounts.ModelUserAccountNumber accountNumbers;
    private ArrayList<ModelSpinnerItem> banks;
    private String AccountNumber;
    private String BankId;


    public VM_ProfileBank(Context context) {//______________________________________________________ VM_ProFileBank
        this.context = context;
    }//_____________________________________________________________________________________________ VM_ProFileBank


    public void SendAccountNumber() {//_____________________________________________________________ SendAccountNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

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
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response));
                    MainActivity.complateprofile = true;
                    getPublishSubject().onNext(StaticValues.ML_EditProfile);
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest(context);
            }
        });
    }//_____________________________________________________________________________________________ SendAccountNumber


    public void GetUserAccountNumber() {//__________________________________________________________ GetUserAccountNumber

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getUserAccountNumber(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelUserAccounts>() {
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
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ GetUserAccountNumber


    public void GetBanks() {//______________________________________________________________________ GetBanks

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBanks(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelSpinnerItems>() {
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
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ GetBanks


    public ModelUserAccounts.ModelUserAccountNumber getAccountNumbers() {//_________________________ getAccountNumbers
        return accountNumbers;
    }//_____________________________________________________________________________________________ getAccountNumbers


    public ArrayList<ModelSpinnerItem> getBanks() {//_______________________________________________ getBanks
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
