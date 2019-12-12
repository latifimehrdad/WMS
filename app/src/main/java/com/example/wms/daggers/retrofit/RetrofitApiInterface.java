package com.example.wms.daggers.retrofit;

import com.example.wms.models.RegisterCitizenModel;
import com.example.wms.models.TokenModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitApiInterface {

    @FormUrlEncoded
    @POST("/token")
    Call<TokenModel> getToken
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type
            );

    @FormUrlEncoded
    @POST("api/v1/account/registercitizen")
    Call<RegisterCitizenModel> SendPhoneNumber
            (
                    @Field("PhoneNumber") String PhoneNumber,
                    @Field("Password") String Password,
                    @Field("ConfirmPassword") String ConfirmPassword,
                    @Header("Authorization") String Authorization
            );

}
