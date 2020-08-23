package com.ngra.wms.daggers.retrofit;

import com.ngra.wms.models.MR_BestScore;
import com.ngra.wms.models.MR_BoothList;
import com.ngra.wms.models.MR_ChartReport;
import com.ngra.wms.models.MR_GiveScore;
import com.ngra.wms.models.MR_Hi;
import com.ngra.wms.models.MR_ItemLearn;
import com.ngra.wms.models.MR_ItemsWast;
import com.ngra.wms.models.MR_Register;
import com.ngra.wms.models.MR_ScoreList;
import com.ngra.wms.models.MR_ScoreList2;
import com.ngra.wms.models.MR_ScoreReport;
import com.ngra.wms.models.MR_TicketList;
import com.ngra.wms.models.MR_TicketReplyList;
import com.ngra.wms.models.MR_TimeSheet;
import com.ngra.wms.models.MR_UserScoreInfoList;
import com.ngra.wms.models.MR_UserScorePriceReport;
import com.ngra.wms.models.MR_WasteRequest;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelBuildingRenovationCode;
import com.ngra.wms.models.ModelGetAddress;
import com.ngra.wms.models.ModelHousingBuildings;
import com.ngra.wms.models.ModelProfileInfo;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MR_SpinnerItems;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.models.MD_Token;
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
    Call<MD_Token> getToken
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type
            );

    //______________________________________________________________________________________________ getToken
    @FormUrlEncoded
    @POST("/token")
    Call<MD_Token> getRefreshToken
    (
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token
    );

    @FormUrlEncoded
    @POST(Version + "/CitizenContact/Register")
    Call<MR_Register> SendPhoneNumber
            (
                    @Field("Mobile") String PhoneNumber,
                    @Header("Authorization") String Authorization
            );



    @FormUrlEncoded
    @POST(Version + "/CitizenContact/SendVerificationSms")
    Call<ModelResponsePrimary> SendVerificationSms
            (
                    @Field("Mobile") String PhoneNumber,
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
    @POST(Version + "/account/authrequest")
    Call<ModelResponsePrimary> LoginCode
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("mobile") String PhoneNumber,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST("/token")
    Call<MD_Token> Login
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type,
                    @Field("username") String PhoneNumber,
                    @Field("code") String code,
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

    @GET(Version + "/timesheet/boothtimes")
    Call<MD_TimeSheet> getTimes
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
                    @Field("PlateNumber") String plateNumber,
                    @Field("UnitNumber") String UnitNumber,
                    @Field("Id") Integer Id,
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


//    @GET(Version + "/Score/GetScoreList")
//    Call<MR_GiveScore> getScoreList
//            (
//                    @Header("Authorization") String Authorization
//            );


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
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("name") String name
            );



    @GET(Version + "/Ticketing/GetTicketList")
    Call<MR_TicketList> getTicketList
            (
                    @Header("Authorization") String Authorization
            );


    @GET(Version + "/Ticketing/GetReplyList")
    Call<MR_TicketReplyList> getReplyList
            (
                    @Query("Id") Integer Id,
                    @Header("Authorization") String Authorization
            );



    @FormUrlEncoded
    @POST(Version + "/Ticketing/SubmitClientReply")
    Call<ModelResponsePrimary> SubmitClientReply
            (
                    @Field("Id") Integer Id,
                    @Field("ReplyText") String ReplyText,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/Ticketing/CloseTicket")
    Call<ModelResponsePrimary> CloseTicket
            (
                    @Field("Id") Integer Id,
                    @Header("Authorization") String Authorization
            );


    @GET(Version + "/WasteEstimate/WasteEstimateList")
    Call<MR_SpinnerItems> getWasteEstimateList
            (
                    @Header("Authorization") String Authorization

            );



    @GET(Version + "/TimeSheet/BoothTimes")
    Call<MR_TimeSheet> getBoothTimes
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/TimeSheet/VehicleTimes")
    Call<MR_TimeSheet> getVehicleTimes
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/citizen/GetContactAddresses")
    Call<MR_SpinnerItems> getContactAddresses
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/Score/GetScoreList")
    Call<MR_ScoreList> getScoreList
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/score/topscorelist")
    Call<MR_BestScore> gettopscorelist
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/UserScoreInfo/GetUserScoreInfoList")
    Call<MR_UserScoreInfoList> getUserScoreInfoList
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/UserScoreInfo/ScoreReport")
    Call<MR_ScoreReport> getScoreReport
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/Score/GetScoreList2")
    Call<MR_ScoreList2> getScoreList2
            (
                    @Header("Authorization") String Authorization

            );

    @GET(Version + "/WarehouseInventoryInfo/GetUserScoreChartReport")
    Call<MR_ChartReport> getChartReport
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/WarehouseInventoryInfo/GetUserScorePriceReport")
    Call<MR_UserScorePriceReport> getUserScorePriceReport
            (
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/requestwastecollection/wastecollectioncanceled")
    Call<ModelResponsePrimary> WasteCollectionCanceled
            (
                    @Field("RequestCode") String RequestCode,
                    @Header("Authorization") String Authorization
            );


}
