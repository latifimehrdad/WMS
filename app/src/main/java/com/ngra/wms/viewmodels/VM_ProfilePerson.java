package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelProfileInfo;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.activitys.MainActivity;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfilePerson extends VM_Primary {

    private ModelProfileInfo.ModelProfile profile;
    private ArrayList<MD_SpinnerItem> Regions;
    private ArrayList<MD_SpinnerItem> Cities;
    private ArrayList<MD_SpinnerItem> provinces;
    private String FirstName;
    private String LastName;
    private int Gender;
    private Integer CitizenType;
    private String CityId;
    private String ReferenceCode;
    private String RegionId;
    private String ProvinceId;


    //______________________________________________________________________________________________ VM_ProfilePerson
    public VM_ProfilePerson(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_ProfilePerson


    //______________________________________________________________________________________________ getProfileInfo
    public void getProfileInfo() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getProfileInfo(
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelProfileInfo>() {
            @Override
            public void onResponse(Call<ModelProfileInfo> call, Response<ModelProfileInfo> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    profile = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetProfileInfo);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelProfileInfo> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ getProfileInfo


    //______________________________________________________________________________________________ editProfile
    public void editProfile() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .EditProfile(
                        getFirstName(),
                        getLastName(),
                        getGender(),
                        getCitizenType(),
                        getCityId(),
                        getReferenceCode(),
                        getRegionId(),
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    saveProfile();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ editProfile


    //______________________________________________________________________________________________ getPlacesList
    public void getPlacesList() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();


        retrofitComponent
                .getRetrofitApiInterface()
                .getRegions(
                        getCityId(),
                        authorization)
                .enqueue(new Callback<MR_SpinnerItems>() {
                    @Override
                    public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            Regions = response.body().getResult();
                            sendActionToObservable(StaticValues.ML_GetRegion);
                        } else
                            sendActionToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                        onFailureRequest();
                    }
                });

    }
    //______________________________________________________________________________________________ getPlacesList


    //______________________________________________________________________________________________ getCitiesList
    public void getCitiesList() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();


        retrofitComponent
                .getRetrofitApiInterface()
                .getCitys(
                        getProvinceId(),
                        authorization)
                .enqueue(new Callback<MR_SpinnerItems>() {
                    @Override
                    public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            Cities = response.body().getResult();
                            sendActionToObservable(StaticValues.ML_GetCities);
                        } else
                            sendActionToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                        onFailureRequest();
                    }
                });

    }
    //______________________________________________________________________________________________ getCitiesList


    //______________________________________________________________________________________________ getProvincesList
    public void getProvincesList() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();


        Call<MR_SpinnerItems> call = retrofitComponent
                .getRetrofitApiInterface()
                .getProvinces(
                        authorization);

        call.enqueue(new Callback<MR_SpinnerItems>() {
            @Override
            public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    provinces = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetProvince);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getProvincesList


    //______________________________________________________________________________________________ saveProfile
    private void saveProfile() {

        SharedPreferences.Editor token =
                getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0).edit();
        token.putString(getContext().getString(R.string.ML_Name), getFirstName());
        token.putString(getContext().getString(R.string.ML_lastName), getLastName());
        token.putInt(getContext().getString(R.string.ML_Gender), getGender());
        token.putBoolean(getContext().getString(R.string.ML_CompleteProfile), true);
        token.apply();
        MainActivity.completeProfile = true;
        sendActionToObservable(StaticValues.ML_EditProfile);
    }
    //______________________________________________________________________________________________ saveProfile


    //______________________________________________________________________________________________ getPhoneNumber
    public String getPhoneNumber() {

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return "";
        } else {
            return prefs.getString(getContext().getString(R.string.ML_PhoneNumber), "");
        }

    }
    //______________________________________________________________________________________________ getPhoneNumber


    //______________________________________________________________________________________________ getFirstName
    public String getFirstName() {
        return FirstName;
    }
    //______________________________________________________________________________________________ getFirstName


    //______________________________________________________________________________________________ setFirstName
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    //______________________________________________________________________________________________ setFirstName


    //______________________________________________________________________________________________ getLastName
    public String getLastName() {
        return LastName;
    }
    //______________________________________________________________________________________________ getLastName


    //______________________________________________________________________________________________ setLastName
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    //______________________________________________________________________________________________ setLastName


    //______________________________________________________________________________________________ getGender
    public int getGender() {
        return Gender;
    }
    //______________________________________________________________________________________________ getGender


    //______________________________________________________________________________________________ setGender
    public void setGender(int gender) {
        Gender = gender;
    }
    //______________________________________________________________________________________________ setGender


    //______________________________________________________________________________________________ getCitizenType
    public Integer getCitizenType() {
        return CitizenType;
    }
    //______________________________________________________________________________________________ getCitizenType


    //______________________________________________________________________________________________ setCitizenType
    public void setCitizenType(Integer citizenType) {
        CitizenType = citizenType;
    }
    //______________________________________________________________________________________________ setCitizenType


    //______________________________________________________________________________________________ getCityId
    public String getCityId() {
        return CityId;
    }
    //______________________________________________________________________________________________ getCityId


    //______________________________________________________________________________________________ setCityId
    public void setCityId(String cityId) {
        CityId = cityId;
    }
    //______________________________________________________________________________________________ setCityId


    //______________________________________________________________________________________________ getReferenceCode
    public String getReferenceCode() {
        return ReferenceCode;
    }
    //______________________________________________________________________________________________ getReferenceCode


    //______________________________________________________________________________________________ setReferenceCode
    public void setReferenceCode(String referenceCode) {
        ReferenceCode = referenceCode;
    }
    //______________________________________________________________________________________________ setReferenceCode


    //______________________________________________________________________________________________ getRegionId
    public String getRegionId() {
        return RegionId;
    }
    //______________________________________________________________________________________________ getRegionId


    //______________________________________________________________________________________________ setRegionId
    public void setRegionId(String regionId) {
        RegionId = regionId;
    }
    //______________________________________________________________________________________________ setRegionId


    //______________________________________________________________________________________________ getRegions
    public ArrayList<MD_SpinnerItem> getRegions() {
        return Regions;
    }
    //______________________________________________________________________________________________ getRegions


    //______________________________________________________________________________________________ getProvinceId
    public String getProvinceId() {
        return ProvinceId;
    }
    //______________________________________________________________________________________________ getProvinceId


    //______________________________________________________________________________________________ setProvinceId
    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }
    //______________________________________________________________________________________________ setProvinceId


    //______________________________________________________________________________________________ getCities
    public ArrayList<MD_SpinnerItem> getCities() {
        return Cities;
    }
    //______________________________________________________________________________________________ getCities


    //______________________________________________________________________________________________ getProvinces
    public ArrayList<MD_SpinnerItem> getProvinces() {
        return provinces;
    }
    //______________________________________________________________________________________________ getProvinces


    //______________________________________________________________________________________________ getProfile
    public ModelProfileInfo.ModelProfile getProfile() {
        return profile;
    }
    //______________________________________________________________________________________________ getProfile


}
