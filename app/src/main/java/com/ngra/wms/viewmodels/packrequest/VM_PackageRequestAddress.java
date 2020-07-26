package com.ngra.wms.viewmodels.packrequest;

import android.app.Activity;
import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelBuildingTypes;
import com.ngra.wms.models.ModelGetAddress;
import com.ngra.wms.models.ModelHousingBuildings;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_PackageRequestAddress extends VM_Primary {

    private ModelBuildingTypes buildingTypes;
    private ModelGetAddress address;
    private String AddressString;

    public VM_PackageRequestAddress(Activity context) {//___________________________________________ VM_PackageRequestAddress
        setContext(context);
    }//_____________________________________________________________________________________________ VM_PackageRequestAddress


    public void SaveAddress(
            String Address,
            double lat,
            double lng,
            Long BuildingTypeId,
            Integer BuildingTypeCount,
            Long BuildingUseId,
            Integer BuildingUseCount,
            String plateNumber,
            String UnitNumber) {//__________________________________________________________________ SaveAddress

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .EditUserAddress(
                        Address,
                        lat,
                        lng,
                        BuildingTypeId,
                        BuildingTypeCount,
                        BuildingUseId,
                        BuildingUseCount,
                        plateNumber,
                        UnitNumber,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelResponsePrimary>() {
            @Override
            public void onResponse(Call<ModelResponsePrimary> call, Response<ModelResponsePrimary> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(GetMessage(response));
                    GetLoginInformation();
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelResponsePrimary> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SaveAddress


    public void GetLoginInformation() {//___________________________________________________________ GetLoginInformation

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<ModelSettingInfo>() {
            @Override
            public void onResponse(Call<ModelSettingInfo> call, Response<ModelSettingInfo> response) {

                String m = CheckResponse(response, true);
                if (m == null) {
                    if (StaticFunctions.SaveProfile(getContext(), response.body().getResult()))
                        SendMessageToObservable(StaticValues.ML_EditUserAddress);
                } else {
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<ModelSettingInfo> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetLoginInformation


    public void GetTypeBuilding() {//_______________________________________________________________ GetTypeBuilding

        StaticFunctions.isCancel = false;
        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String Authorization = GetAuthorization();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getHousingBuildings(Authorization));

        getPrimaryCall().enqueue(new Callback<ModelHousingBuildings>() {
            @Override
            public void onResponse(Call<ModelHousingBuildings> call, Response<ModelHousingBuildings> response) {
                if (StaticFunctions.isCancel)
                    return;
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    buildingTypes = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetHousingBuildings);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<ModelHousingBuildings> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetTypeBuilding


    public void GetAddress(double lat, double lon) {//______________________________________________ GetAddress

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String url = "http://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=22&addressdetails=5";

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAddress(url));

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
                SendMessageToObservable(StaticValues.ML_GetAddress);
            }

            @Override
            public void onFailure(Call<ModelGetAddress> call, Throwable t) {
                address = new ModelGetAddress();
                address.setLat(String.valueOf(lat));
                address.setLon(String.valueOf(lon));
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetAddress




    public void SetAddress() {//____________________________________________________________________ Start SetAddress

        if (address != null && address.getAddress() != null) {
            StringBuilder addressString = new StringBuilder();

            String country = address.getAddress().getCountry();


            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {
                addressString.append(country);
                addressString.append("، ");

                if(!country.equalsIgnoreCase("ایران")) {
                    setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                    SendMessageToObservable(StaticValues.ML_ResponseError);
                    return;
                }

            } else {
                setResponseMessage(getContext().getResources().getString(R.string.OutOfIran));
                SendMessageToObservable(StaticValues.ML_ResponseError);
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

            AddressString = addressString.toString();
        } else {
            AddressString = "";
        }

        SendMessageToObservable(StaticValues.ML_SetAddress);

    }//_____________________________________________________________________________________________ End SetAddress



    public String getAddressString() {//____________________________________________________________ getAddressString
        return AddressString;
    }//_____________________________________________________________________________________________ getAddressString


    public ModelBuildingTypes getBuildingTypes() {//________________________________________________ getBuildingTypes
        return buildingTypes;
    }//_____________________________________________________________________________________________ getBuildingTypes


}
