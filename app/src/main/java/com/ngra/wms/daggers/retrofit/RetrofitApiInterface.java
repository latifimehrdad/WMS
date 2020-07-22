package com.ngra.wms.daggers.retrofit;

import com.ngra.wms.models.MR_BoothList;
import com.ngra.wms.models.MR_GiveScore;
import com.ngra.wms.models.MR_Hi;
import com.ngra.wms.models.MR_ItemLearn;
import com.ngra.wms.models.MR_ItemsWast;
import com.ngra.wms.models.MR_TicketList;
import com.ngra.wms.models.MR_WasteRequest;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelBuildingRenovationCode;
import com.ngra.wms.models.ModelGetAddress;
import com.ngra.wms.models.ModelHousingBuildings;
import com.ngra.wms.models.ModelProfileInfo;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.ModelTimeSheetTimes;
import com.ngra.wms.models.ModelToken;
import com.ngra.wms.models.ModelUserAccounts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
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
    @POST(Version + "/CitizenContact/Register")
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
    Call<MR_SpinnerItems> getProvinces
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/citiesbyprovince")
    Call<MR_SpinnerItems> getCitys
            (
                    @Query("Id") String Id,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/neighbourhoods")
    Call<MR_SpinnerItems> getRegions
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
    Call<MR_SpinnerItems> getBanks
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
    Call<MR_ItemsWast> getWasteList
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/contact/getboothlist")
    Call<MR_BoothList> getBoothList
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
    Call<MR_WasteRequest> getWasteRequests
            (
                    @Header("Authorization") String Authorization
            );


    @GET(Version + "/WasteNotice/GetSummaryWasteNotice")
    Call<MR_ItemLearn> getSummaryWasteNotice
            (
                    @Header("Authorization") String Authorization
            );


    @GET(Version + "/Score/GetScoreList")
    Call<MR_GiveScore> getScoreList
            (
                    @Header("Authorization") String Authorization
            );


    @GET(Version + "/UserScoreInfo/GetUserScoreInfoList")
    Call<MR_GiveScore> GetUserScore
            (
                    @Header("Authorization") String Authorization
            );

    @GET(Version + "/Ticketing/GetAllCategories")
    Call<MR_SpinnerItems> getAllCategories
            (
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/Ticketing/SubmitTicket")
    Call<ModelResponsePrimary> SubmitTicket
            (
                    @Field("DepartmentId") String DepartmentId,
                    @Field("Subject") String Subject,
                    @Field("Description") String Description,
                    @Field("CategoryId") String CategoryId,
                    @Header("Authorization") String Authorization
            );

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String urlString);


    @FormUrlEncoded
    @POST(Version + "/application/hi")
    Call<MR_Hi> getHi
            (
                    @Field("name") String name,
                    @Header("Authorization") String Authorization
            );



    @GET(Version + "/Ticketing/GetTicketList")
    Call<MR_TicketList> getTicketList
            (
                    @Header("Authorization") String Authorization
            );

}
