package com.ngra.wms.viewmodels.user.profile;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelProfileInfo;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_RequestSpinnerItems;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
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

    public VM_ProfilePerson(Activity context) {//___________________________________________________ VM_ProfilePerson
        setContext(context);
    }//_____________________________________________________________________________________________ VM_ProfilePerson


    public void GetProfileInfo() {//________________________________________________________________ GetProfileInfo

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getProfileInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelProfileInfo>() {
            @Override
            public void onResponse(Call<ModelProfileInfo> call, Response<ModelProfileInfo> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    profile = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetProfileInfo);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelProfileInfo> call, Throwable t) {
                OnFailureRequest();
            }
        });
    }//_____________________________________________________________________________________________ GetProfileInfo


    public void EditProfile() {//___________________________________________________________________ EditProfile

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

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
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response));
                    SaveProfile();
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ EditProfile


    public void GetPlacesList() {//_________________________________________________________________ GetPlacesList

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        retrofitComponent
                .getRetrofitApiInterface()
                .getRegions(
                        getCityId(),
                        Authorization)
                .enqueue(new Callback<MD_RequestSpinnerItems>() {
                    @Override
                    public void onResponse(Call<MD_RequestSpinnerItems> call, Response<MD_RequestSpinnerItems> response) {
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            Regions = response.body().getResult();
                            SendMessageToObservable(StaticValues.ML_GetRegion);
                        } else
                            SendMessageToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MD_RequestSpinnerItems> call, Throwable t) {
                        OnFailureRequest();
                    }
                });

    }//_____________________________________________________________________________________________ GetPlacesList


    public void GetCitiesList() {//_________________________________________________________________ GetCitiesList

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        retrofitComponent
                .getRetrofitApiInterface()
                .getCitys(
                        getProvinceId(),
                        Authorization)
                .enqueue(new Callback<MD_RequestSpinnerItems>() {
                    @Override
                    public void onResponse(Call<MD_RequestSpinnerItems> call, Response<MD_RequestSpinnerItems> response) {
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            Cities = response.body().getResult();
                            SendMessageToObservable(StaticValues.ML_GetCities);
                        } else
                            SendMessageToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MD_RequestSpinnerItems> call, Throwable t) {
                        OnFailureRequest();
                    }
                });

    }//_____________________________________________________________________________________________ GetCitiesList


    public void GetProvincesList() {//______________________________________________________________ GetProvincesList

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        Call<MD_RequestSpinnerItems> call = retrofitComponent
                .getRetrofitApiInterface()
                .getProvinces(
                        Authorization);

        call.enqueue(new Callback<MD_RequestSpinnerItems>() {
            @Override
            public void onResponse(Call<MD_RequestSpinnerItems> call, Response<MD_RequestSpinnerItems> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    provinces = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetProvince);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_RequestSpinnerItems> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetProvincesList


    private void SaveProfile() {//__________________________________________________________________ SaveProfile

        SharedPreferences.Editor token =
                getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0).edit();
        token.putString(getContext().getString(R.string.ML_Name), getFirstName());
        token.putString(getContext().getString(R.string.ML_lastName), getLastName());
        token.putInt(getContext().getString(R.string.ML_Gender), getGender());
        token.putBoolean(getContext().getString(R.string.ML_CompleteProfile), true);
        token.apply();
        MainActivity.complateprofile = true;
        SendMessageToObservable(StaticValues.ML_EditProfile);
    }//_____________________________________________________________________________________________ SaveProfile


    public String GetPhoneNumber() {//______________________________________________________________ GetPhoneNumber

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return "";
        } else {
            return prefs.getString(getContext().getString(R.string.ML_PhoneNumber), "");
        }

    }//_____________________________________________________________________________________________ GetPhoneNumber


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

    public String getCityId() {//___________________________________________________________________ getCityId
        return CityId;
    }//_____________________________________________________________________________________________ getCityId

    public void setCityId(String cityId) {//________________________________________________________ setCityId
        CityId = cityId;
    }//_____________________________________________________________________________________________ setCityId


    public String getReferenceCode() {//____________________________________________________________ getReferenceCode
        return ReferenceCode;
    }//_____________________________________________________________________________________________ getReferenceCode

    public void setReferenceCode(String referenceCode) {//__________________________________________ setReferenceCode
        ReferenceCode = referenceCode;
    }//_____________________________________________________________________________________________ setReferenceCode


    public String getRegionId() {//_________________________________________________________________ getRegionId
        return RegionId;
    }//_____________________________________________________________________________________________ getRegionId

    public void setRegionId(String regionId) {//____________________________________________________ setRegionId
        RegionId = regionId;
    }//_____________________________________________________________________________________________ setRegionId

    public ArrayList<MD_SpinnerItem> getRegions() {//_____________________________________________ getRegions
        return Regions;
    }//_____________________________________________________________________________________________ getRegions


    public String getProvinceId() {//_______________________________________________________________ getProvinceId
        return ProvinceId;
    }//_____________________________________________________________________________________________ getProvinceId


    public void setProvinceId(String provinceId) {//________________________________________________ setProvinceId
        ProvinceId = provinceId;
    }//_____________________________________________________________________________________________ setProvinceId


    public ArrayList<MD_SpinnerItem> getCities() {//_______________________________________________ getCities
        return Cities;
    }//_____________________________________________________________________________________________ getCities


    public ArrayList<MD_SpinnerItem> getProvinces() {//___________________________________________ getProvinces
        return provinces;
    }//_____________________________________________________________________________________________ getProvinces

    public ModelProfileInfo.ModelProfile getProfile() {//___________________________________________ getProfile
        return profile;
    }//_____________________________________________________________________________________________ getProfile


}
