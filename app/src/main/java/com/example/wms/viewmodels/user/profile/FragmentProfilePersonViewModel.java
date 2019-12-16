/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.user.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelProvince;
import com.example.wms.models.ModelProvinces;
import com.example.wms.models.ModelResponsePrimery;
import com.example.wms.models.ModelToken;
import com.example.wms.views.application.ApplicationWMS;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetٍMessage;

public class FragmentProfilePersonViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private boolean isCancel;
    private ArrayList<ModelProvince> provinces;
    private ArrayList<ModelProvince> Citys;
    private ArrayList<ModelProvince> Plases;
    private String MessageResponcse;
    private String ProvinceId;
    private String CityId;
    private String PlaceId;
    private String FirstName;
    private String LastName;
    private int Gender;
    private Integer CitizenType;
    private Integer ReferenceCode;

    public FragmentProfilePersonViewModel(Context context) {//_____________________________________ Start FragmentProfilePersonViewModel
        this.context = context;
        Observables = PublishSubject.create();
        ObserverObservables();
    }//_____________________________________________________________________________________________ End FragmentProfilePersonViewModel


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        switch (s) {
                            case "CancelByUser":
                                isCancel = true;
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


    public void EditProfile() {//___________________________________________________________________ Start GetPlasesList
        isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = "Bearer ";
        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs != null) {
            String access_token = prefs.getString("accesstoken", null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }


        retrofitComponent
                .getRetrofitApiInterface()
                .EditProfile(
                        getFirstName(),
                        getLastName(),
                        getGender(),
                        getCitizenType(),
                        getCityId(),
                        getReferenceCode(),
                        getPlaceId(),
                        Authorization)
                .enqueue(new Callback<ModelResponsePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponsePrimery> call, Response<ModelResponsePrimery> response) {
                        if (isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            MessageResponcse = GetٍMessage(response);
                            Observables.onNext("SuccessfulEdit");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelResponsePrimery> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetPlasesList


    public void GetPlasesList() {//________________________________________________ Start GetPlasesList
        isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = "Bearer ";
        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs != null) {
            String access_token = prefs.getString("accesstoken", null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }


        retrofitComponent
                .getRetrofitApiInterface()
                .getPlaces(
                        getCityId(),
                        Authorization)
                .enqueue(new Callback<ModelProvinces>() {
                    @Override
                    public void onResponse(Call<ModelProvinces> call, Response<ModelProvinces> response) {
                        if (isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            Plases = response.body().getResult();
                            Observables.onNext("SuccessfulPlace");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelProvinces> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetPlasesList


    public void GetCitysList() {//__________________________________________________________________ Start GetCitysList
        isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = "Bearer ";
        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs != null) {
            String access_token = prefs.getString("accesstoken", null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }


        retrofitComponent
                .getRetrofitApiInterface()
                .getCitys(
                        getProvinceId(),
                        Authorization)
                .enqueue(new Callback<ModelProvinces>() {
                    @Override
                    public void onResponse(Call<ModelProvinces> call, Response<ModelProvinces> response) {
                        if (isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            Citys = response.body().getResult();
                            Observables.onNext("SuccessfulCity");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelProvinces> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetCitysList


    public void GetProvincesList() {//______________________________________________________________ Start GetProvincesList
        isCancel = false;

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(context)
                        .getRetrofitComponent();

        String Authorization = "Bearer ";
        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs != null) {
            String access_token = prefs.getString("accesstoken", null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }


        retrofitComponent
                .getRetrofitApiInterface()
                .getProvinces(
                        Authorization)
                .enqueue(new Callback<ModelProvinces>() {
                    @Override
                    public void onResponse(Call<ModelProvinces> call, Response<ModelProvinces> response) {
                        if (isCancel)
                            return;
                        MessageResponcse = CheckResponse(response, false);
                        if (MessageResponcse == null) {
                            provinces = response.body().getResult();
                            Observables.onNext("SuccessfulProvince");
                        } else
                            Observables.onNext("Error");
                    }

                    @Override
                    public void onFailure(Call<ModelProvinces> call, Throwable t) {
                        Observables.onNext("Failure");
                    }
                });

    }//_____________________________________________________________________________________________ End GetProvincesList


    public ArrayList<ModelProvince> getProvinces() {//______________________________________________ Start getProvinces
        return provinces;
    }//_____________________________________________________________________________________________ End getProvinces


    public String getMessageResponcse() {//_________________________________________________________ Start getMessageResponcse
        return MessageResponcse;
    }//_____________________________________________________________________________________________ End getMessageResponcse


    public ArrayList<ModelProvince> getCitys() {//__________________________________________________ Start getCitys
        return Citys;
    }//_____________________________________________________________________________________________ End getCitys


    public ArrayList<ModelProvince> getPlases() {//_________________________________________________ Start getPlases
        return Plases;
    }//_____________________________________________________________________________________________ End getPlases


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

    public String getPlaceId() {//__________________________________________________________________ Start getPlaceId
        return PlaceId;
    }//_____________________________________________________________________________________________ End getPlaceId

    public void setPlaceId(String placeId) {//______________________________________________________ Start setPlaceId
        PlaceId = placeId;
    }//_____________________________________________________________________________________________ End setPlaceId


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
}
