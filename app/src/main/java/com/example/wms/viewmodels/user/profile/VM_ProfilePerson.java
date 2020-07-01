package com.example.wms.viewmodels.user.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelProfileInfo;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.models.ModelSpinnerItems;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_ProfilePerson extends VM_Primary {

    private Context context;
    private ModelProfileInfo.ModelProfile profile;
    private ArrayList<ModelSpinnerItem> Regions;
    private ArrayList<ModelSpinnerItem> Cities;
    private ArrayList<ModelSpinnerItem> provinces;
    private String FirstName;
    private String LastName;
    private int Gender;
    private Integer CitizenType;
    private String CityId;
    private String ReferenceCode;
    private String RegionId;
    private String ProvinceId;

    public VM_ProfilePerson(Context context) {//____________________________________________________ VM_ProfilePerson
        this.context = context;
    }//_____________________________________________________________________________________________ VM_ProfilePerson


    public void GetProfileInfo() {//________________________________________________________________ GetProfileInfo

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

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
                    getPublishSubject().onNext(StaticValues.ML_GetProfileInfo);
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelProfileInfo> call, Throwable t) {
                OnFailureRequest(context);
            }
        });
    }//_____________________________________________________________________________________________ GetProfileInfo


    public void EditProfile() {//___________________________________________________________________ EditProfile

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

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
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ EditProfile


    public void GetPlacesList() {//_________________________________________________________________ GetPlacesList

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
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            Regions = response.body().getResult();
                            getPublishSubject().onNext(StaticValues.ML_GetRegion);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        OnFailureRequest(context);
                    }
                });

    }//_____________________________________________________________________________________________ GetPlacesList


    public void GetCitiesList() {//_________________________________________________________________ GetCitiesList

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
                        setResponseMessage(CheckResponse(response, false));
                        if (getResponseMessage() == null) {
                            Cities = response.body().getResult();
                            getPublishSubject().onNext(StaticValues.ML_GetCities);
                        } else
                            getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                        OnFailureRequest(context);
                    }
                });

    }//_____________________________________________________________________________________________ GetCitiesList


    public void GetProvincesList() {//______________________________________________________________ GetProvincesList

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = GetAuthorization(context);


        Call<ModelSpinnerItems> call = retrofitComponent
                .getRetrofitApiInterface()
                .getProvinces(
                        Authorization);

        call.enqueue(new Callback<ModelSpinnerItems>() {
            @Override
            public void onResponse(Call<ModelSpinnerItems> call, Response<ModelSpinnerItems> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    provinces = response.body().getResult();
                    getPublishSubject().onNext(StaticValues.ML_GetProvince);
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelSpinnerItems> call, Throwable t) {
                OnFailureRequest(context);
            }
        });

    }//_____________________________________________________________________________________________ GetProvincesList


    private void SaveProfile() {//__________________________________________________________________ SaveProfile

        SharedPreferences.Editor token =
                context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0).edit();
        token.putString(context.getString(R.string.ML_Name), getFirstName());
        token.putString(context.getString(R.string.ML_lastName), getLastName());
        token.putInt(context.getString(R.string.ML_Gender), getGender());
        token.putBoolean(context.getString(R.string.ML_CompleteProfile), true);
        token.apply();
        MainActivity.complateprofile = true;
        getPublishSubject().onNext(StaticValues.ML_EditProfile);
    }//_____________________________________________________________________________________________ SaveProfile


    public String GetPhoneNumber() {//______________________________________________________________ GetPhoneNumber

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return "";
        } else {
            String PhoneNumber = prefs.getString(context.getString(R.string.ML_PhoneNumber), "");
            return PhoneNumber;
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

    public ArrayList<ModelSpinnerItem> getRegions() {//_____________________________________________ getRegions
        return Regions;
    }//_____________________________________________________________________________________________ getRegions


    public String getProvinceId() {//_______________________________________________________________ getProvinceId
        return ProvinceId;
    }//_____________________________________________________________________________________________ getProvinceId


    public void setProvinceId(String provinceId) {//________________________________________________ setProvinceId
        ProvinceId = provinceId;
    }//_____________________________________________________________________________________________ setProvinceId


    public ArrayList<ModelSpinnerItem> getCities() {//_______________________________________________ getCities
        return Cities;
    }//_____________________________________________________________________________________________ getCities


    public ArrayList<ModelSpinnerItem> getProvinces() {//___________________________________________ getProvinces
        return provinces;
    }//_____________________________________________________________________________________________ getProvinces

    public ModelProfileInfo.ModelProfile getProfile() {//___________________________________________ getProfile
        return profile;
    }//_____________________________________________________________________________________________ getProfile


}
