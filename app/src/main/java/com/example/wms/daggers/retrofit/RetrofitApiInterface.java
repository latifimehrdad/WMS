package com.example.wms.daggers.retrofit;

import com.example.wms.models.MD_RequestBoothList;
import com.example.wms.models.MD_RequestItemsWast;
import com.example.wms.models.MD_RequestWasteRequest;
import com.example.wms.models.MD_WasteAmountRequests;
import com.example.wms.models.ModelBuildingRenovationCode;
import com.example.wms.models.ModelGetAddress;
import com.example.wms.models.ModelHousingBuildings;
import com.example.wms.models.ModelProfileInfo;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.models.ModelSpinnerItems;
import com.example.wms.models.ModelTimeSheetTimes;
import com.example.wms.models.ModelToken;
import com.example.wms.models.ModelUserAccounts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitApiInterface {

    String Version = "/api/v1";

    @FormUrlEncoded
    @POST("/token")
    Call<ModelToken> getToken
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type
            );

    @FormUrlEncoded
    @POST(Version + "/account/registercitizen")
    Call<ModelResponsePrimary> SendPhoneNumber
            (
                    @Field("Mobile") String PhoneNumber,
                    @Field("Password") String Password,
                    @Field("ConfirmPassword") String ConfirmPassword,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/account/confirmmobile")
    Call<ModelResponsePrimary> SendVerifyCode
            (
                    @Field("Mobile") String PhoneNumber,
                    @Field("Code") String Password,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST("/token")
    Call<ModelToken> Login
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type,
                    @Field("username") String PhoneNumber,
                    @Field("Password") String Password,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/provinces")
    Call<ModelSpinnerItems> getProvinces
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/citiesbyprovince")
    Call<ModelSpinnerItems> getCitys
            (
                    @Query("Id") String Id,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/neighbourhoods")
    Call<ModelSpinnerItems> getRegions
            (
                    @Query("Id") String Id,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/citizen/editprofile")
    Call<ModelResponsePrimary> EditProfile
            (
                    @Field("FirstName") String FirstName,
                    @Field("LastName") String LastName,
                    @Field("Gender") int Gender,
                    @Field("CitizenType") Integer CitizenType,
                    @Field("City.Id") String CityId,
                    @Field("ReferenceCode") String ReferenceCode,
                    @Field("Neighbourhood.Id") String NeighbourhoodId,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/citizen/info")
    Call<ModelProfileInfo> getProfileInfo
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/citizen/citizenbank")
    Call<ModelUserAccounts> getUserAccountNumber
            (
                    @Header("Authorization") String Authorization

            );



    @GET(Version + "/bank/banks")
    Call<ModelSpinnerItems> getBanks
            (
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/citizen/editcitizenbank")
    Call<ModelResponsePrimary> SendAccountNumber
            (
                    @Field("Bank.Id") String FirstName,
                    @Field("AccountNumber") String LastName,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/citizen/editbuildingrenovationcode")
    Call<ModelResponsePrimary> SendBuildingRenovationCode
            (
                    @Field("BuildingRenovationCode") String BuildingRenovationCode,
                    @Header("Authorization") String Authorization

            );

    @GET(Version + "/citizen/buildingrenovationcode")
    Call<ModelBuildingRenovationCode> getBuildingRenovationCode
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/citizen/settinginfo")
    Call<ModelSettingInfo> getSettingInfo
            (
                    @Header("Authorization") String Authorization

            );

    @GET()
    Call<ModelGetAddress> getAddress(
            @Url String url
    );


    @GET(Version + "/housing/buildings")
    Call<ModelHousingBuildings> getHousingBuildings
            (
                    @Header("Authorization") String Authorization
            );

    @GET(Version + "/timesheet/times")
    Call<ModelTimeSheetTimes> getTimes
            (
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/packagerequest/packagerequest")
    Call<ModelResponsePrimary> SendPackageRequest
            (
                    @Field("TimeId") Integer TimeId,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/citizen/edituseraddress")
    Call<ModelResponsePrimary> EditUserAddress
            (
                    @Field("Address") String Address,
                    @Field("Latitude") double Latitude,
                    @Field("Longitude") double Longitude,
                    @Field("BuildingTypeId") Long BuildingTypeId,
                    @Field("BuildingTypeCount") Integer BuildingTypeCount,
                    @Field("BuildingUseId") Long BuildingUseId,
                    @Field("BuildingUseCount") Integer BuildingUseCount,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/Waste/WasteList")
    Call<MD_RequestItemsWast> getWasteList
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/contact/getboothlist")
    Call<MD_RequestBoothList> getBoothList
            (
                    @Header("Authorization") String Authorization

            );


    @POST(Version + "/RequestWasteCollection/RequestCollection")
    Call<ModelResponsePrimary> RequestCollection
            (
                    @Body MD_WasteAmountRequests WasteAmountRequests,
                    @Header("Authorization") String Authorization

            );

    @GET(Version + "/RequestWasteCollection/WasteRequests")
    Call<MD_RequestWasteRequest> getWasteRequests
            (
                    @Header("Authorization") String Authorization
            );

}
