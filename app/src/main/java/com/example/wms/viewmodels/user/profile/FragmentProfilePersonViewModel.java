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

public class FragmentProfilePersonViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private ArrayList<ModelSpinnerItem> provinces;
    private ArrayList<ModelSpinnerItem> Citys;
    private ArrayList<ModelSpinnerItem> Regions;
    private ModelProfileInfo.ModelProfile profile;
    private String MessageResponcse;
    private String ProvinceId;
    private String CityId;
    private String RegionId;
    private String FirstName;
    private String LastName;
    private int Gender;
    private Integer CitizenType;
    private Integer ReferenceCode;

    public FragmentProfilePersonViewModel(Context context) {//_____________________________________ Start FragmentProfilePersonViewModel
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ End FragmentProfilePersonViewModel



    public void GetProfileInfo() {//________________________________________________________________ Start GetProfileInfo
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            profile = response.body().getResult();
                            Observables.onNext("SuccessfulProfile");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelProfileInfo> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });
    }//_____________________________________________________________________________________________ End GetProfileInfo


    public void EditProfile() {//___________________________________________________________________ Start GetPlasesList
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            MessageResponcse = GetMessage(response);
                            SharedPreferences.Editor token =
                                    context.getSharedPreferences("wmstoken", 0).edit();

                            token.putBoolean("complateprofile", true);
                            token.apply();

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

    }//_____________________________________________________________________________________________ End GetPlasesList


    public void GetPlasesList() {//________________________________________________ Start GetPlasesList
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            Regions = response.body().getResult();
                            Observables.onNext("SuccessfulRegion");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetPlasesList


    public void GetCitysList() {//__________________________________________________________________ Start GetCitysList
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            Citys = response.body().getResult();
                            Observables.onNext("SuccessfulCity");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetCitysList


    public void GetProvincesList() {//______________________________________________________________ Start GetProvincesList
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
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            provinces = response.body().getResult();
                            Observables.onNext("SuccessfulProvince");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetProvincesList



    public ArrayList<ModelSpinnerItem> getProvinces() {//______________________________________________ Start getProvinces
        return provinces;
    }//_____________________________________________________________________________________________ End getProvinces


    public String getMessageResponcse() {//_________________________________________________________ Start getMessageResponcse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponcse


    public ArrayList<ModelSpinnerItem> getCitys() {//__________________________________________________ Start getCitys
        return Citys;
    }//_____________________________________________________________________________________________ End getCitys


    public ArrayList<ModelSpinnerItem> getRegions() {//_________________________________________________ Start getRegions
        return Regions;
    }//_____________________________________________________________________________________________ End getRegions


    public String getProvinceId() {//_______________________________________________________________ Start getProvinceId
        return ProvinceId;
    }//_____________________________________________________________________________________________ End getProvinceId


    public void setProvinceId(String provinceId) {//________________________________________________ Start setProvinceId
        ProvinceId = provinceId;
    }//_____________________________________________________________________________________________ End setProvinceId


    public String getCityId() {//___________________________________________________________________ Start getCityId
        return CityId;
    }//_____________________________________________________________________________________________ End getCityId

    public void setCityId(String cityId) {//________________________________________________________ Start setCityId
        CityId = cityId;
    }//_____________________________________________________________________________________________ End setCityId

    public String getRegionId() {//__________________________________________________________________ Start getRegionId
        return RegionId;
    }//_____________________________________________________________________________________________ End getRegionId

    public void setRegionId(String regionId) {//______________________________________________________ Start setRegionId
        RegionId = regionId;
    }//_____________________________________________________________________________________________ End setRegionId


    public String getFirstName() {//________________________________________________________________ Start getFirstName
        return FirstName;
    }//_____________________________________________________________________________________________ End getFirstName

    public void setFirstName(String firstName) {//__________________________________________________ Start setFirstName
        FirstName = firstName;
    }//_____________________________________________________________________________________________ End setFirstName

    public String getLastName() {//_________________________________________________________________ Start getLastName
        return LastName;
    }//_____________________________________________________________________________________________ End getLastName

    public void setLastName(String lastName) {//____________________________________________________ Start setLastName
        LastName = lastName;
    }//_____________________________________________________________________________________________ End setLastName

    public int getGender() {//______________________________________________________________________ Start getGender
        return Gender;
    }//_____________________________________________________________________________________________ End getGender

    public void setGender(int gender) {//___________________________________________________________ Start setGender
        Gender = gender;
    }//_____________________________________________________________________________________________ End setGender

    public Integer getCitizenType() {//_____________________________________________________________ Start getCitizenType
        return CitizenType;
    }//_____________________________________________________________________________________________ End getCitizenType

    public void setCitizenType(Integer citizenType) {//_____________________________________________ Start setCitizenType
        CitizenType = citizenType;
    }//_____________________________________________________________________________________________ End setCitizenType

    public Integer getReferenceCode() {//___________________________________________________________ Start getReferenceCode
        return ReferenceCode;
    }//_____________________________________________________________________________________________ End getReferenceCode

    public void setReferenceCode(Integer referenceCode) {//_________________________________________ Start setReferenceCode
        ReferenceCode = referenceCode;
    }//_____________________________________________________________________________________________ End setReferenceCode

    public ModelProfileInfo.ModelProfile getProfile() {//___________________________________________ Start getProfile
        return profile;
    }//_____________________________________________________________________________________________ End getProfile

    public void setProfile(ModelProfileInfo.ModelProfile profile) {//_______________________________ Start setProfile
        this.profile = profile;
    }//_____________________________________________________________________________________________ End setProfile
}
