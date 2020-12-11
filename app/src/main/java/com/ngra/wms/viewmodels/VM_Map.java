package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_Address;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MD_GetBooth;
import com.ngra.wms.models.MD_Location;
import com.ngra.wms.models.MD_SuggestionAddress;
import com.ngra.wms.models.MR_BoothList;
import com.ngra.wms.models.ModelGetAddress;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Map extends VM_Primary {


    private List<MD_SuggestionAddress> suggestionAddresses;
    private ModelGetAddress currentAddress;
    private ModelGetAddress address;
    private String textAddress;
    private List<MD_Booth> md_boothList;

    //______________________________________________________________________________________________ VM_Map
    public VM_Map(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Map


    //______________________________________________________________________________________________ getSuggestionAddress
    public void getSuggestionAddress(String address, boolean LoadMore, int ErrorCount) {

        if (suggestionAddresses == null)
            suggestionAddresses = new ArrayList<>();

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        StringBuilder q = new StringBuilder(getCityOfCurrentAddress());
        String[] separated = address.split(" ");
        for (String word : separated)
            q.append("+").append(word);

        StringBuilder url = new StringBuilder("https://nominatim.openstreetmap.org/search?format=json&addressdetails=1&limit=50&q=");
        url.append(q);

        if (LoadMore) {
            url.append("&exclude_place_ids=");
            for (int i = 0; i < suggestionAddresses.size(); i++)
                if (i == suggestionAddresses.size() - 1)
                    url.append(suggestionAddresses.get(i).getPlace_id());
                else
                    url.append(suggestionAddresses.get(i).getPlace_id()).append(",");
        } else
            suggestionAddresses.clear();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSuggestionAddress(url.toString()));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall()
                .enqueue(new Callback<List<MD_SuggestionAddress>>() {
                    @Override
                    public void onResponse(Call<List<MD_SuggestionAddress>> call, Response<List<MD_SuggestionAddress>> response) {
                        if (response.body() == null) {
                            if (ErrorCount > 3)
                                sendActionToObservable(StaticValues.ML_ReTrySuggestion);
                            else
                                onFailureRequest();

                        } else {
                            if (response.body().size() == 0)
                                sendActionToObservable(StaticValues.ML_NotFoundSuggestion);
                            else {
                                suggestionAddresses.addAll(response.body());
                                sendActionToObservable(StaticValues.ML_GetSuggestion);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<MD_SuggestionAddress>> call, Throwable t) {
                        if (ErrorCount > 3)
                            sendActionToObservable(StaticValues.ML_ReTrySuggestion);
                        else
                            onFailureRequest();
                    }
                });

    }
    //______________________________________________________________________________________________ getSuggestionAddress


    //______________________________________________________________________________________________ getBoothList
    public void getBoothList(double lat, double lng) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        MD_Location location = new MD_Location(lat, lng);
        MD_GetBooth md_getBooth = new MD_GetBooth(location);

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getBoothList(md_getBooth, RetrofitApis.app_token,aToken, authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_BoothList>() {
            @Override
            public void onResponse(Call<MR_BoothList> call, Response<MR_BoothList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_boothList = response.body().getResult();
                    if (md_boothList.size() > 0)
                        sendActionToObservable(StaticValues.ML_GetBoothList);
                    else {
                        setResponseMessage(getContext().getResources().getString(R.string.BoothListIsEmpty));
                        sendActionToObservable(StaticValues.ML_GetBoothListEmpty);
                    }

                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }

            }

            @Override
            public void onFailure(Call<MR_BoothList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getBoothList


    //______________________________________________________________________________________________ getAddress
    public void getAddress(double lat, double lon, boolean current, int ErrorCount) {

        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=22&addressdetails=5";

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getAddress(url));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall()
                .enqueue(new Callback<ModelGetAddress>() {
                    @Override
                    public void onResponse(Call<ModelGetAddress> call, Response<ModelGetAddress> response) {
                        if (response.body() == null) {

                            if (ErrorCount > 2) {
                                if (current) {
                                    currentAddress = new ModelGetAddress();
                                    currentAddress.setLat(String.valueOf(lat));
                                    currentAddress.setLon(String.valueOf(lon));
                                    currentAddress.setAddress(new MD_Address());
                                    currentAddress.getAddress().setCity(getContext().getString(R.string.DefaultCity));
                                } else {
                                    address = new ModelGetAddress();
                                    address.setLat(String.valueOf(lat));
                                    address.setLon(String.valueOf(lon));
                                }
                                sendActionToObservable(StaticValues.ML_GetAddress);
                            }

                        } else {
                            if (current) {
                                if (response.body().getAddress() == null) {
                                    if (ErrorCount > 2) {
                                        currentAddress = response.body();
                                        currentAddress.getAddress().setCity(getContext().getString(R.string.DefaultCity));
                                    }
                                } else {
                                    currentAddress = response.body();
                                }
                                sendActionToObservable(StaticValues.ML_GetAddress);
                            } else {
                                address = response.body();
                                if (address.getAddress() == null) {
                                    address.setLat(String.valueOf(lat));
                                    address.setLon(String.valueOf(lon));
                                    sendActionToObservable(StaticValues.ML_GetAddress);
                                } else
                                    setAddress();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelGetAddress> call, Throwable t) {
                        if (ErrorCount > 2) {
                            if (current) {
                                currentAddress = new ModelGetAddress();
                                currentAddress.setLat(String.valueOf(lat));
                                currentAddress.setLon(String.valueOf(lon));
                                currentAddress.setAddress(new MD_Address());
                                currentAddress.getAddress().setCity(getContext().getString(R.string.DefaultCity));
                            } else {
                                address = new ModelGetAddress();
                                address.setLat(String.valueOf(lat));
                                address.setLon(String.valueOf(lon));
                            }
                            sendActionToObservable(StaticValues.ML_GetAddress);
                        }
                    }
                });

    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ setAddress
    private void setAddress() {

        ModelGetAddress GetAddress = address;
        if (GetAddress != null && GetAddress.getAddress() != null) {
            StringBuilder address = new StringBuilder();

            String country = GetAddress.getAddress().getCountry();
            if (country != null &&
                    !country.equalsIgnoreCase("null") &&
                    !country.equalsIgnoreCase("")) {
                address.append(country);
                address.append(" ");
            }

            String state = GetAddress.getAddress().getState();
            if (state != null &&
                    !state.equalsIgnoreCase("null") &&
                    !state.equalsIgnoreCase("")) {
                address.append(state);
                address.append(" ");
            }

            String county = GetAddress.getAddress().getCounty();
            if (county != null &&
                    !county.equalsIgnoreCase("null") &&
                    !county.equalsIgnoreCase("")) {
                address.append(county);
                address.append(" ");
            }

            String city = GetAddress.getAddress().getCity();
            if (city != null &&
                    !city.equalsIgnoreCase("null") &&
                    !city.equalsIgnoreCase("")) {
                address.append("شهر");
                address.append(" ");
                address.append(city);
                address.append(" ");
            }

            String neighbourhood = GetAddress.getAddress().getNeighbourhood();
            if (neighbourhood != null &&
                    !neighbourhood.equalsIgnoreCase("null") &&
                    !neighbourhood.equalsIgnoreCase("")) {
                address.append(neighbourhood);
                address.append(" ");
            }

            String suburb = GetAddress.getAddress().getSuburb();
            if (suburb != null &&
                    !suburb.equalsIgnoreCase("null") &&
                    !suburb.equalsIgnoreCase("") &&
                    !suburb.equalsIgnoreCase(neighbourhood)) {
                address.append(suburb);
                address.append(" ");
            }

            String road = GetAddress.getAddress().getRoad();
            if (road != null &&
                    !road.equalsIgnoreCase("null") &&
                    !road.equalsIgnoreCase("")) {
                address.append("خیابان");
                address.append(" ");
                address.append(road);
                address.append(" ");
            }

            textAddress = address.toString();
        } else {
            textAddress = "";
        }
        sendActionToObservable(StaticValues.ML_GetAddress);
    }
    //______________________________________________________________________________________________ setAddress


    //______________________________________________________________________________________________ getCityOfCurrentAddress
    public String getCityOfCurrentAddress() {

        if (currentAddress == null)
            return getContext().getString(R.string.DefaultCity);

        String city = currentAddress.getAddress().getCity();
        if (city == null || city.equalsIgnoreCase("null") || city.equalsIgnoreCase(""))
            city = getContext().getString(R.string.DefaultCity);

        city = city.replace("شهرستان ", "");

        return city;

    }
    //______________________________________________________________________________________________ getCityOfCurrentAddress


    //______________________________________________________________________________________________ getSuggestionAddresses
    public List<MD_SuggestionAddress> getSuggestionAddresses() {
        return suggestionAddresses;
    }
    //______________________________________________________________________________________________ getSuggestionAddresses


    //______________________________________________________________________________________________ getCurrentAddress
    public ModelGetAddress getCurrentAddress() {
        return currentAddress;
    }
    //______________________________________________________________________________________________ getCurrentAddress


    //______________________________________________________________________________________________ getAddress
    public ModelGetAddress getAddress() {
        return address;
    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ getTextAddress
    public String getTextAddress() {
        return textAddress;
    }
    //______________________________________________________________________________________________ getTextAddress


    //______________________________________________________________________________________________ getMd_boothList
    public List<MD_Booth> getMd_boothList() {
        return md_boothList;
    }
    //______________________________________________________________________________________________ getMd_boothList


}
