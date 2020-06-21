package com.example.wms.viewmodels.user.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.models.ModelToken;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_FragmentLogin {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private ModelToken modelToken;
    private String MessageResponse;
    private ModelSettingInfo.ModelProfileSetting profile;
    private boolean CompleteProfile = false;

    public VM_FragmentLogin(Context context) {//____________________________________________________ VM_FragmentLogin
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentLogin


    public void GetLoginToken(String PhoneNumber, String Password) {//______________________________ GetLoginToken
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_password,
                        PhoneNumber,
                        Password,
                        Authorization)
                .enqueue(new Callback<ModelToken>() {
                    @Override
                    public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, true);
                        if (MessageResponse == null) {
                            modelToken = response.body();
                            if (StaticFunctions.SaveToken(context, modelToken))
                                GetLoginInformation();
//                            SaveToken();
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelToken> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetLoginToken


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization)
                .enqueue(new Callback<ModelSettingInfo>() {
                    @Override
                    public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, true);
                        if (MessageResponse == null) {
                            profile = response.body().getResult();
                            if (StaticFunctions.SaveProfile(context,profile))
                                Observables.onNext(StaticValues.ML_GoToHome);
//                            SaveProfile();
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetLoginInformation


//    private void SaveProfile() {//__________________________________________________________________ SaveProfile
//        SharedPreferences.Editor token = context
//                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
//                .edit();
//        token.putString(context.getString(R.string.ML_Name),profile.getName());
//        token.putString(context.getString(R.string.ML_lastName),profile.getLastName());
//        token.putInt(context.getString(R.string.ML_Gender),profile.getGender());
//        token.putBoolean(context.getString(R.string.ML_CompleteProfile),profile.getProfileCompleted());
//        token.putBoolean(context.getString(R.string.ML_AddressCompleted),profile.getAddressCompleted());
//        token.putBoolean(context.getString(R.string.ML_IsPackageState),profile.getIsPackageState());
//        token.putInt(context.getString(R.string.ML_PackageRequest),profile.getPackageRequest());
//        token.apply();
//        Observables.onNext(StaticValues.ML_GoToHome);
//    }//_____________________________________________________________________________________________ SaveProfile


//    private void SaveToken() {//____________________________________________________________________ SaveToken
//
//        SharedPreferences.Editor token = context
//                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
//                .edit();
//
//        token.putString(context.getString(R.string.ML_AccessToken), modelToken.getAccess_token());
//        token.putString(context.getString(R.string.ML_TokenType), modelToken.getToken_type());
//        token.putInt(context.getString(R.string.ML_ExpireSin), modelToken.getExpires_in());
//        token.putString(context.getString(R.string.ML_PhoneNumber), modelToken.getPhoneNumber());
//        token.putString(context.getString(R.string.ML_ClientId), modelToken.getClient_id());
//        token.putString(context.getString(R.string.ML_Issued), modelToken.getIssued());
//        token.putString(context.getString(R.string.ML_Expires), modelToken.getExpires());
//        token.apply();
//        GetLoginInformation();
//
//    }//_____________________________________________________________________________________________ SaveToken


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables


    public boolean isCompleteProfile() {//__________________________________________________________ isCompleteProfile
        return CompleteProfile;
    }//_____________________________________________________________________________________________ isCompleteProfile

}
