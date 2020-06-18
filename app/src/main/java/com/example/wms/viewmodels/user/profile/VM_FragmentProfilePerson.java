/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.user.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelProfileInfo;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.models.ModelSpinnerItems;
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

public class VM_FragmentProfilePerson {

    private Context context;
    private PublishSubject<Byte> Observables = null;
    private ArrayList<ModelSpinnerItem> provinces;
    private ArrayList<ModelSpinnerItem> Cities;
    private ArrayList<ModelSpinnerItem> Regions;
    private ModelProfileInfo.ModelProfile profile;
    private String MessageResponse;
    private String ProvinceId;
    private String CityId;
    private String RegionId;
    private String FirstName;
    private String LastName;
    private int Gender;
    private Integer CitizenType;
    private String ReferenceCode;

    public VM_FragmentProfilePerson(Context context) {//____________________________________________ VM_FragmentProfilePerson
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentProfilePerson



    public void GetProfileInfo() {//________________________________________________________________ GetProfileInfo
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getProfileInfo(
                        Authorization)
                .enqueue(new Callback<ModelProfileInfo>() {
                    @Override
                    public void onResponse(Call<ModelProfileInfo> call, Response<ModelProfileInfo> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            profile = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetProfileInfo);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelProfileInfo> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });
    }//_____________________________________________________________________________________________ GetProfileInfo


    public void EditProfile() {//___________________________________________________________________ EditProfile
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .EditProfile(
                        getFirstName(),
                        getLastName(),
                        getGender(),
                        getCitizenType(),
                        getCityId(),
                        getReferenceCode(),
                        getRegionId(),
                        Authorization)
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            MessageResponse = GetMessage(response);
                            SaveProfile();
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ EditProfile


    private void SaveProfile() {//__________________________________________________________________ SaveProfile
        SharedPreferences.Editor token =
                context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0).edit();
        token.putString(context.getString(R.string.ML_Name),getFirstName());
        token.putString(context.getString(R.string.ML_lastName),getLastName());
        token.putInt(context.getString(R.string.ML_Gender),getGender());
        token.putBoolean(context.getString(R.string.ML_CompleteProfile),true);
        token.apply();
        MainActivity.complateprofile = true;
        Observables.onNext(StaticValues.ML_EditProfile);
    }//_____________________________________________________________________________________________ SaveProfile



    public void GetPlacesList() {//_________________________________________________________________ GetPlacesList
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .getRegions(
                        getCityId(),
                        Authorization)
                .enqueue(new Callback<ModelSpinnerItems>() {
                    @Override
                    public void onResponse(Call<ModelSpinnerItems> call, Response<ModelSpinnerItems> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            Regions = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetRegion);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetPlacesList


    public void GetCitiesList() {//_________________________________________________________________ GetCitiesList
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .getCitys(
                        getProvinceId(),
                        Authorization)
                .enqueue(new Callback<ModelSpinnerItems>() {
                    @Override
                    public void onResponse(Call<ModelSpinnerItems> call, Response<ModelSpinnerItems> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            Cities = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetCities);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetCitiesList


    public void GetProvincesList() {//______________________________________________________________ GetProvincesList
        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        retrofitComponent
                .getRetrofitApiInterface()
                .getProvinces(
                        Authorization)
                .enqueue(new Callback<ModelSpinnerItems>() {
                    @Override
                    public void onResponse(Call<ModelSpinnerItems> call, Response<ModelSpinnerItems> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            provinces = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetProvince);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetProvincesList



    public String GetPhoneNumber() {//______________________________________________________________ GetPhoneNumber

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return "";
        } else {
            String PhoneNumber = prefs.getString(context.getString(R.string.ML_PhoneNumber), "");
            return PhoneNumber;
        }

    }//_____________________________________________________________________________________________ GetPhoneNumber




    public ArrayList<ModelSpinnerItem> getProvinces() {//___________________________________________ getProvinces
        return provinces;
    }//_____________________________________________________________________________________________ getProvinces


    public String getMessageResponse() {//_________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse


    public ArrayList<ModelSpinnerItem> getCities() {//_______________________________________________ getCities
        return Cities;
    }//_____________________________________________________________________________________________ getCities


    public ArrayList<ModelSpinnerItem> getRegions() {//_____________________________________________ getRegions
        return Regions;
    }//_____________________________________________________________________________________________ getRegions


    public String getProvinceId() {//_______________________________________________________________ getProvinceId
        return ProvinceId;
    }//_____________________________________________________________________________________________ getProvinceId


    public void setProvinceId(String provinceId) {//________________________________________________ setProvinceId
        ProvinceId = provinceId;
    }//_____________________________________________________________________________________________ setProvinceId


    public String getCityId() {//___________________________________________________________________ getCityId
        return CityId;
    }//_____________________________________________________________________________________________ getCityId

    public void setCityId(String cityId) {//________________________________________________________ setCityId
        CityId = cityId;
    }//_____________________________________________________________________________________________ setCityId

    public String getRegionId() {//_________________________________________________________________ getRegionId
        return RegionId;
    }//_____________________________________________________________________________________________ getRegionId

    public void setRegionId(String regionId) {//____________________________________________________ setRegionId
        RegionId = regionId;
    }//_____________________________________________________________________________________________ setRegionId


    public String getFirstName() {//________________________________________________________________ getFirstName
        return FirstName;
    }//_____________________________________________________________________________________________ getFirstName

    public void setFirstName(String firstName) {//__________________________________________________ setFirstName
        FirstName = firstName;
    }//_____________________________________________________________________________________________ setFirstName

    public String getLastName() {//_________________________________________________________________ getLastName
        return LastName;
    }//_____________________________________________________________________________________________ getLastName

    public void setLastName(String lastName) {//____________________________________________________ setLastName
        LastName = lastName;
    }//_____________________________________________________________________________________________ setLastName

    public int getGender() {//______________________________________________________________________ getGender
        return Gender;
    }//_____________________________________________________________________________________________ getGender

    public void setGender(int gender) {//___________________________________________________________ setGender
        Gender = gender;
    }//_____________________________________________________________________________________________ setGender

    public Integer getCitizenType() {//_____________________________________________________________ getCitizenType
        return CitizenType;
    }//_____________________________________________________________________________________________ getCitizenType

    public void setCitizenType(Integer citizenType) {//_____________________________________________ setCitizenType
        CitizenType = citizenType;
    }//_____________________________________________________________________________________________ setCitizenType

    public String getReferenceCode() {//____________________________________________________________ getReferenceCode
        return ReferenceCode;
    }//_____________________________________________________________________________________________ getReferenceCode

    public void setReferenceCode(String referenceCode) {//__________________________________________ setReferenceCode
        ReferenceCode = referenceCode;
    }//_____________________________________________________________________________________________ setReferenceCode

    public ModelProfileInfo.ModelProfile getProfile() {//___________________________________________ getProfile
        return profile;
    }//_____________________________________________________________________________________________ getProfile

    public void setProfile(ModelProfileInfo.ModelProfile profile) {//_______________________________ setProfile
        this.profile = profile;
    }//_____________________________________________________________________________________________ setProfile

    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables
}
