package com.example.wms.viewmodels.packrequest;

import android.content.Context;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelBuildingTypes;
import com.example.wms.models.ModelGetAddress;
import com.example.wms.models.ModelHousingBuildings;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.views.application.ApplicationWMS;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wms.utility.StaticFunctions.CheckResponse;
import static com.example.wms.utility.StaticFunctions.GetAuthorization;
import static com.example.wms.utility.StaticFunctions.GetMessage;

public class VM_FragmentPackRequestAddress {

    private Context context;
    private PublishSubject<Byte> Observables;
    private ModelGetAddress address;
    private String AddressString;
    private String MessageResponse;
    private ModelBuildingTypes buildingTypes;

    public VM_FragmentPackRequestAddress(Context context) {//_______________________________________ VM_FragmentPackRequestAddress
        this.context = context;
        Observables = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_FragmentPackRequestAddress



    public void SaveAddress(
            String Address,
            double lat,
            double lng,
            Long BuildingTypeId,
            Integer BuildingTypeCount,
            Long BuildingUseId,
            Integer BuildingUseCount) {//___________________________________________________________ SaveAddress

        StaticFunctions.isCancel = false;

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .EditUserAddress(
                        Address,
                        lat,
                        lng,
                        BuildingTypeId,
                        BuildingTypeCount,
                        BuildingUseId,
                        BuildingUseCount,
                        Authorization
                )
                .enqueue(new Callback<ModelResponcePrimery>() {
                    @Override
                    public void onResponse(Call<ModelResponcePrimery> call, Response<ModelResponcePrimery> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            MessageResponse = GetMessage(response);
                            GetLoginInformation();

                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);

                    }

                    @Override
                    public void onFailure(Call<ModelResponcePrimery> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ SaveAddress



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
                        String m = CheckResponse(response, true);
                        if (m == null) {
                            if (StaticFunctions.SaveProfile(context,response.body().getResult()))
                                Observables.onNext(StaticValues.ML_EditUserAddress);
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



    public void GetTypeBuilding() {//_______________________________________________________________ GetTypeBuilding

        StaticFunctions.isCancel = false;
        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        String Authorization = GetAuthorization(context);

        retrofitComponent
                .getRetrofitApiInterface()
                .getHousingBuildings(Authorization)
                .enqueue(new Callback<ModelHousingBuildings>() {
                    @Override
                    public void onResponse(Call<ModelHousingBuildings> call, Response<ModelHousingBuildings> response) {
                        if (StaticFunctions.isCancel)
                            return;
                        MessageResponse = CheckResponse(response, false);
                        if (MessageResponse == null) {
                            buildingTypes = response.body().getResult();
                            Observables.onNext(StaticValues.ML_GetHousingBuildings);
                        } else
                            Observables.onNext(StaticValues.ML_ResponseError);

                    }

                    @Override
                    public void onFailure(Call<ModelHousingBuildings> call, Throwable t) {
                        Observables.onNext(StaticValues.ML_ResponseFailure);
                    }
                });

    }//_____________________________________________________________________________________________ GetTypeBuilding


    public void GetAddress(double lat, double lon) {//______________________________________________ GetAddress

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(context)
                .getRetrofitComponent();

        String url = "http://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=22&addressdetails=5";

        retrofitComponent
                .getRetrofitApiInterface()
                .getAddress(url)
                .enqueue(new Callback<ModelGetAddress>() {
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
                        Observables.onNext(StaticValues.ML_GetAddress);
                    }

                    @Override
                    public void onFailure(Call<ModelGetAddress> call, Throwable t) {
                        address = new ModelGetAddress();
                        address.setLat(String.valueOf(lat));
                        address.setLon(String.valueOf(lon));
                        Observables.onNext(StaticValues.ML_ResponseFailure);
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
                    MessageResponse = context.getResources().getString(R.string.OutOfIran);
                    Observables.onNext(StaticValues.ML_ResponseError);
                    return;
                }

            } else {
                MessageResponse = context.getResources().getString(R.string.OutOfIran);
                Observables.onNext(StaticValues.ML_ResponseError);
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

        Observables.onNext(StaticValues.ML_SetAddress);

    }//_____________________________________________________________________________________________ End SetAddress



    public PublishSubject<Byte> getObservables() {//________________________________________________ getObservables
        return Observables;
    }//_____________________________________________________________________________________________ getObservables



    public ModelGetAddress getAddress() {//_________________________________________________________ getAddress
        return address;
    }//_____________________________________________________________________________________________ getAddress



    public String getAddressString() {//____________________________________________________________ getAddressString
        return AddressString;
    }//_____________________________________________________________________________________________ getAddressString


    public String getMessageResponse() {//__________________________________________________________ getMessageResponse
        return MessageResponse;
    }//_____________________________________________________________________________________________ getMessageResponse

    public ModelBuildingTypes getBuildingTypes() {//________________________________________________ getBuildingTypes
        return buildingTypes;
    }//_____________________________________________________________________________________________ getBuildingTypes
}
