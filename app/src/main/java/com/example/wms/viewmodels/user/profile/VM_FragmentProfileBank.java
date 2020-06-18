/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.user.profile;

        import android.content.Context;

        import com.example.wms.daggers.retrofit.RetrofitComponent;

        import com.example.wms.models.ModelResponcePrimery;
        import com.example.wms.models.ModelSpinnerItem;
        import com.example.wms.models.ModelSpinnerItems;
        import com.example.wms.models.ModelUserAccounts;
        import com.example.wms.utility.StaticFunctions;
        import com.example.wms.utility.StaticValues;
        import com.example.wms.views.activitys.MainActivity;
        import com.example.wms.views.application.ApplicationWMS;

        import java.util.ArrayList;

        import io.reactivex.subjects.PublishSubject;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.example.wms.utility.StaticFunctions.CheckResponse;
        import static com.example.wms.utility.StaticFunctions.GetAuthorization;
        import static com.example.wms.utility.StaticFunctions.GetMessage;

public class VM_FragmentProfileBank {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private String MessageResponse;
    private ArrayList<ModelSpinnerItem> banks;
    private ModelUserAccounts.ModelUserAccountNumber accountNumbers;
    private String AccountNumber;
    private String BankId;


    public VM_FragmentProfileBank(Context context) {//______________________________________________ VM_FragmentProfileBank
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentProfileBank


    public void SendAccountNumber() {//_____________________________________________________________ SendAccountNumber
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .SendAccountNumber(
                        getBankId(),
                        getAccountNumber(),
                        Authorization)
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            MessageResponse = GetMessage(response);
                            MainActivity.complateprofile = true;
                            Observables.onNext(StaticValues.ML_EditProfile);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
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
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            if (response.body().getResult() == null)
                                Observables.onNext(StaticValues.ML_GetAccountNumberNull);
                            else {
                                accountNumbers = response.body().getResult();
                                Observables.onNext(StaticValues.ML_GetAccountNumbers);
                            }
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelUserAccounts> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
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
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            banks = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetBanks);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });
    }//_____________________________________________________________________________________________ GetBanks


    public ArrayList<ModelSpinnerItem> getBanks() {//_______________________________________________ getBanks
        return banks;
    }//_____________________________________________________________________________________________ getBanks


    public String getAccountNumber() {//____________________________________________________________ getAccountNumber
        return AccountNumber;
    }//_____________________________________________________________________________________________ getAccountNumber


    public void setAccountNumber(String accountNumber) {//__________________________________________ setAccountNumber
        AccountNumber = accountNumber;
    }//_____________________________________________________________________________________________ setAccountNumber


    public String getBankId() {//___________________________________________________________________ getBankId
        return BankId;
    }//_____________________________________________________________________________________________ getBankId

    public void setBankId(String bankId) {//________________________________________________________ setBankId
        BankId = bankId;
    }//_____________________________________________________________________________________________ setBankId


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse

    public ModelUserAccounts.ModelUserAccountNumber getAccountNumbers() {//_________________________ getAccountNumbers
        return accountNumbers;
    }//_____________________________________________________________________________________________ getAccountNumbers


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables
}
