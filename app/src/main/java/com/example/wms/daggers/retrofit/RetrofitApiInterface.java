package com.example.wms.daggers.retrofit;

import com.example.wms.models.ModelProvinces;
import com.example.wms.models.ModelRegisterCitizen;
import com.example.wms.models.ModelResponsePrimery;
import com.example.wms.models.ModelToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    Call<ModelRegisterCitizen> SendPhoneNumber
            (
                    @Field("Mobile") String PhoneNumber,
                    @Field("Password") String Password,
                    @Field("ConfirmPassword") String ConfirmPassword,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/account/confirmmobile")
    Call<ModelRegisterCitizen> SendVerifyCode
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
    Call<ModelProvinces> getProvinces
            (
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/cities")
    Call<ModelProvinces> getCitys
            (
                    @Query("Id") String Id,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/region/neighbourhoods")
    Call<ModelProvinces> getPlaces
            (
                    @Query("Id") String Id,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/citizen/editprofile")
    Call<ModelResponsePrimery> EditProfile
            (
                    @Field("FirstName") String FirstName,
                    @Field("LastName") String LastName,
                    @Field("Gender") int Gender,
                    @Field("CitizenType") Integer CitizenType,
                    @Field("City.Id") String CityId,
                    @Field("ReferenceCode") Integer ReferenceCode,
                    @Field("Neighbourhood.Id") String NeighbourhoodId,
                    @Header("Authorization") String Authorization

            );

}
