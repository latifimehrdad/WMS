package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.ModelBuildingTypes;
import com.ngra.wms.models.ModelGetAddress;
import com.ngra.wms.models.ModelHousingBuildings;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_PackageRequestAddress extends VM_Primary {

    private ModelBuildingTypes buildingTypes;
    private ModelGetAddress address;
    private String addressString;
    private Integer addressId;
    private Long buildingTypeId;
    private Long buildingUseId;
    private String plateNumber;
    private String buildingTypeCount;
    private String buildingUseCount;
    private String unitNumber;


    //______________________________________________________________________________________________ VM_PackageRequestAddress
    public VM_PackageRequestAddress(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_PackageRequestAddress


    //______________________________________________________________________________________________ saveAddress
    public void saveAddress() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        if (addressId == 0)
            addressId = null;

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .EditUserAddress(
                        getAddressString(),
                        Double.parseDouble(getAddress().getLat()),
                        Double.parseDouble(getAddress().getLon()),
                        getBuildingTypeId(),
                        Integer.valueOf(getBuildingTypeCount()),
                        getBuildingUseId(),
                        Integer.valueOf(getBuildingUseCount()),
                        getPlateNumber(),
                        getUnitNumber(),
                        getAddressId(),
                        RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    getLoginInformation();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ saveAddress


    //______________________________________________________________________________________________ getLoginInformation
    public void getLoginInformation() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        RetrofitApis.app_token,
                        aToken,
                        Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {

                String m = checkResponse(response, true);
                if (m == null) {
                    if (getUtility().saveProfile(getContext(), response.body().getResult()))
                        sendActionToObservable(StaticValues.ML_EditUserAddress);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getLoginInformation


    //______________________________________________________________________________________________ getTypeBuilding
    public void getTypeBuilding() {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getHousingBuildings(RetrofitApis.app_token,aToken,authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelHousingBuildings>() {
            @Override
            public void onResponse(Call<ModelHousingBuildings> call, Response<ModelHousingBuildings> response) {
                ;
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    buildingTypes = response.body().getResult();
                    notifyChange();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelHousingBuildings> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getTypeBuilding


    //______________________________________________________________________________________________ getAddress
    public void getAddress(double lat, double lon) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=22&addressdetails=5";

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAddress(url));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ModelGetAddress>() {
            @Override
            public void onResponse(Call<ModelGetAddress> call, Response<ModelGetAddress> response) {
                if (response.body() == null) {
                    address = new ModelGetAddress();
                    address.setLat(String.valueOf(lat));
                    address.setLon(String.valueOf(lon));
                } else {
                    address = response.body();
                    if (address.getAddress() == null) {
                        address.setLat(String.valueOf(lat));
                        address.setLon(String.valueOf(lon));
                    }
                }
                sendActionToObservable(StaticValues.ML_GetAddress);
            }

            @Override
            public void onFailure(Call<ModelGetAddress> call, Throwable t) {
                address = new ModelGetAddress();
                address.setLat(String.valueOf(lat));
                address.setLon(String.valueOf(lon));
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ setAddress
    public void setAddress() {

        if (address != null && address.getAddress() != null) {
            StringBuilder addressString = new StringBuilder();

            String country = address.getAddress().getCountry();


            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {
                addressString.append(country);
                addressString.append("، ");

                if (!country.equalsIgnoreCase("ایران")) {
                    setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                    sendActionToObservable(StaticValues.ML_ResponseError);
                    return;
                }

            } else {
                setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                sendActionToObservable(StaticValues.ML_ResponseError);
                return;
            }

            String state = address.getAddress().getState();
            if (state != null &&
                    !state.equalsIgnoreCase("null") &&
                    !state.equalsIgnoreCase("")) {
                addressString.append(state);
                addressString.append("، ");
            }

            String county = address.getAddress().getCounty();
            if (county != null &&
                    !county.equalsIgnoreCase("null") &&
                    !county.equalsIgnoreCase("")) {
                addressString.append(county);
                addressString.append("، ");
            }

            String city = address.getAddress().getCity();
            if (city != null &&
                    !city.equalsIgnoreCase("null") &&
                    !city.equalsIgnoreCase("")) {
                addressString.append(city);
                addressString.append("، ");
            }

            String neighbourhood = address.getAddress().getNeighbourhood();
            if (neighbourhood != null &&
                    !neighbourhood.equalsIgnoreCase("null") &&
                    !neighbourhood.equalsIgnoreCase("")) {
                addressString.append(neighbourhood);
                addressString.append("، ");
            }

            String suburb = address.getAddress().getSuburb();
            if (suburb != null &&
                    !suburb.equalsIgnoreCase("null") &&
                    !suburb.equalsIgnoreCase("") &&
                    !suburb.equalsIgnoreCase(neighbourhood)) {
                addressString.append(suburb);
                addressString.append("، ");
            }

            String road = address.getAddress().getRoad();
            if (road != null &&
                    !road.equalsIgnoreCase("null") &&
                    !road.equalsIgnoreCase("")) {
                addressString.append(road);
            }

            this.addressString = addressString.toString();
        } else {
            addressString = "";
        }

        sendActionToObservable(StaticValues.ML_SetAddress);
        notifyChange();

    }
    //______________________________________________________________________________________________ setAddress


    //______________________________________________________________________________________________ getUserAddress
    public void getUserAddress() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        retrofitComponent
                .getRetrofitApiInterface()
                .getContactAddresses(RetrofitApis.app_token,aToken,authorization)
                .enqueue(new Callback<MR_SpinnerItems>() {
                    @Override
                    public void onResponse(Call<MR_SpinnerItems> call, Response<MR_SpinnerItems> response) {
                        setResponseMessage(checkResponse(response, false));
                        if (getResponseMessage() == null) {
                            if (response.body().getResult() != null)
                                if (response.body().getResult() == null)
                                    sendActionToObservable(StaticValues.ML_ResponseError);
                                else {
                                    addressId = Integer.valueOf(response.body().getResult().get(0).getId());
                                    sendActionToObservable(StaticValues.ML_GetUserAddress);
                                }
                        } else
                            sendActionToObservable(StaticValues.ML_ResponseError);
                    }

                    @Override
                    public void onFailure(Call<MR_SpinnerItems> call, Throwable t) {
                        onFailureRequest();
                    }
                });

    }
    //______________________________________________________________________________________________ getUserAddress


    //______________________________________________________________________________________________ getAddressString
    public String getAddressString() {
        return addressString;
    }
    //______________________________________________________________________________________________ getAddressString


    //______________________________________________________________________________________________ setAddressString
    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }
    //______________________________________________________________________________________________ setAddressString


    //______________________________________________________________________________________________ getBuildingTypes
    public ModelBuildingTypes getBuildingTypes() {
        return buildingTypes;
    }
    //______________________________________________________________________________________________ getBuildingTypes


    //______________________________________________________________________________________________ getAddressId
    public Integer getAddressId() {
        if (addressId == null)
            addressId = 0;

        return addressId;
    }
    //______________________________________________________________________________________________ getAddressId


    //______________________________________________________________________________________________ getBuildingTypeId
    public Long getBuildingTypeId() {
        return buildingTypeId;
    }
    //______________________________________________________________________________________________ getBuildingTypeId


    //______________________________________________________________________________________________ setBuildingTypeId
    public void setBuildingTypeId(Long buildingTypeId) {
        this.buildingTypeId = buildingTypeId;
    }
    //______________________________________________________________________________________________ setBuildingTypeId


    //______________________________________________________________________________________________ getBuildingUseId
    public Long getBuildingUseId() {
        return buildingUseId;
    }
    //______________________________________________________________________________________________ getBuildingUseId


    //______________________________________________________________________________________________ setBuildingUseId
    public void setBuildingUseId(Long buildingUseId) {
        this.buildingUseId = buildingUseId;
    }
    //______________________________________________________________________________________________ setBuildingUseId


    //______________________________________________________________________________________________ getPlateNumber
    public String getPlateNumber() {
        return plateNumber;
    }
    //______________________________________________________________________________________________ getPlateNumber


    //______________________________________________________________________________________________ setPlateNumber
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    //______________________________________________________________________________________________ setPlateNumber


    //______________________________________________________________________________________________ getBuildingTypeCount
    public String getBuildingTypeCount() {
        return buildingTypeCount;
    }
    //______________________________________________________________________________________________ getBuildingTypeCount


    //______________________________________________________________________________________________ setBuildingTypeCount
    public void setBuildingTypeCount(String buildingTypeCount) {
        this.buildingTypeCount = buildingTypeCount;
    }
    //______________________________________________________________________________________________ setBuildingTypeCount


    //______________________________________________________________________________________________ getBuildingUseCount
    public String getBuildingUseCount() {
        return buildingUseCount;
    }
    //______________________________________________________________________________________________ getBuildingUseCount


    //______________________________________________________________________________________________ setBuildingUseCount
    public void setBuildingUseCount(String buildingUseCount) {
        this.buildingUseCount = buildingUseCount;
    }
    //______________________________________________________________________________________________ setBuildingUseCount


    //______________________________________________________________________________________________ getUnitNumber
    public String getUnitNumber() {
        return unitNumber;
    }
    //______________________________________________________________________________________________ getUnitNumber


    //______________________________________________________________________________________________ setUnitNumber
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }
    //______________________________________________________________________________________________ setUnitNumber


    //______________________________________________________________________________________________ getAddress
    public ModelGetAddress getAddress() {
        return address;
    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ setAddress
    public void setAddress(ModelGetAddress address) {
        this.address = address;
    }
    //______________________________________________________________________________________________ setAddress


    //______________________________________________________________________________________________ setAddressId
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    //______________________________________________________________________________________________ setAddressId
}
