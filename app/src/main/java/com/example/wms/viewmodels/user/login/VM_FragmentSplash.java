/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
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

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;

public class VM_FragmentSplash {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private ModelToken modelToken;
    private String MessageResponse;
    private ModelSettingInfo.ModelProfileSetting profile;

    public VM_FragmentSplash(Context context) {//_____________________________________________ Start VM_FragmentSplash
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End VM_FragmentSplash


    public void CheckToken() {//________________________________ ___________________________________ Start CheckToken
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            GetTokenFromServer();
        } else {
            String access_token = prefs.getString(context.getString(R.string.ML_AccessToken), null);
            String expires = prefs.getString(context.getString(R.string.ML_Expires), null);
            String PhoneNumber = prefs.getString(context.getString(R.string.ML_PhoneNumber), null);
            if ((access_token == null) || (expires == null))
                GetTokenFromServer();
            else {
                if (PhoneNumber != null) {
                    GetLoginInformation();
                } else
                    GetTokenFromServer();
            }

        }

    }//_____________________________________________________________________________________________ End CheckToken



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



    public void GetTokenFromServer() {//____________________________________________________________ GetTokenFromServer

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        retrofitComponent
                .getRetrofitApiInterface()
                .getToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value)
                .enqueue(new Callback<ModelToken>() {
                    @Override
                    public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                        MessageResponse = CheckResponse(response, true);
                        if (MessageResponse == null) {
                            modelToken = response.body();
                            if (StaticFunctions.SaveToken(context, modelToken))
                                Observables.onNext(StaticValues.ML_GotoLogin);
//                            SaveToken();
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelToken> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetTokenFromServer


    public ModelToken getModelToken() {//___________________________________________________________ getModelToken
        return modelToken;
    }//_____________________________________________________________________________________________ getModelToken


//    private void SaveToken() {//____________________________________________________________________ SaveToken
//
//        SharedPreferences.Editor token = context
//                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
//                .edit();
//
//        token.putString(context.getString(R.string.ML_AccessToken), modelToken.getAccess_token());
//        token.putString(context.getString(R.string.ML_TokenType), modelToken.getToken_type());
//        token.putInt(context.getString(R.string.ML_ExpireSin), modelToken.getExpires_in());
//        token.putString(context.getString(R.string.ML_ClientId), modelToken.getClient_id());
//        token.putString(context.getString(R.string.ML_Issued), modelToken.getIssued());
//        token.putString(context.getString(R.string.ML_Expires), modelToken.getExpires());
//        token.apply();
//        Observables.onNext(StaticValues.ML_GotoLogin);
//
//    }//_____________________________________________________________________________________________ SaveToken


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables

}
