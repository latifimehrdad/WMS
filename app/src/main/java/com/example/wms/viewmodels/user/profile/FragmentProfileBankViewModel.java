/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.user.profile;

        import android.content.Context;
        import android.content.SharedPreferences;

        import com.example.wms.daggers.retrofit.RetrofitComponent;
        import com.example.wms.models.ModelProfileInfo;

        import com.example.wms.models.ModelResponcePrimery;
        import com.example.wms.models.ModelSpinnerItem;
        import com.example.wms.models.ModelSpinnerItems;
        import com.example.wms.models.ModelUserAccounts;
        import com.example.wms.utility.StaticFunctions;
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

public class FragmentProfileBankViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private String MessageResponcse;
    private ArrayList<ModelSpinnerItem> banks;
    private ModelUserAccounts.ModelUserAccountNumber accountNumbers;
    private String AccountNumber;
    private String BankId;


    public FragmentProfileBankViewModel(Context context) {//_______________________________________ Start FragmentProfileBankViewModel
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End FragmentProfileBankViewModel


    public void SendAccountNumber() {//_____________________________________________________________ Start SendAccountNumber
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            MessageResponcse = GetMessage(response);
                            MainActivity.complateprofile = true;
                            Observables.onNext("SuccessfulEdit");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });
    }//_____________________________________________________________________________________________ End SendAccountNumber


    public void GetUserAccountNumber() {//__________________________________________________________ Start GetUserAccountNumber

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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            if (response.body().getResult() == null)
                                Observables.onNext("SuccessfulNull");
                            else {
                                accountNumbers = response.body().getResult();
                                Observables.onNext("SuccessfulGetAccount");
                            }
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelUserAccounts> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetUserAccountNumber


    public void GetBanks() {//______________________________________________________________________ Start GetBanks
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            banks = response.body().getResult();
                            Observables.onNext("SuccessfulBank");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });
    }//_____________________________________________________________________________________________ End GetBanks


    public ArrayList<ModelSpinnerItem> getBanks() {//_______________________________________________ Start getBanks
        return banks;
    }//_____________________________________________________________________________________________ End getBanks


    public String getAccountNumber() {//____________________________________________________________ Start getAccountNumber
        return AccountNumber;
    }//_____________________________________________________________________________________________ End getAccountNumber


    public void setAccountNumber(String accountNumber) {//__________________________________________ Start setAccountNumber
        AccountNumber = accountNumber;
    }//_____________________________________________________________________________________________ End setAccountNumber


    public String getBankId() {//___________________________________________________________________ Start getBankId
        return BankId;
    }//_____________________________________________________________________________________________ End getBankId

    public void setBankId(String bankId) {//________________________________________________________ Start setBankId
        BankId = bankId;
    }//_____________________________________________________________________________________________ End setBankId


    public String getMessageResponcse() {//_________________________________________________________ Start getMessageResponcse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponcse

    public ModelUserAccounts.ModelUserAccountNumber getAccountNumbers() {//_________________________ Start getAccountNumbers
        return accountNumbers;
    }//_____________________________________________________________________________________________ End getAccountNumbers
}
