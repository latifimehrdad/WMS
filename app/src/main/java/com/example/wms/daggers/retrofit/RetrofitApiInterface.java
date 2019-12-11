package com.example.wms.daggers.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApiInterface {

    @FormUrlEncoded
    @POST("api/AdvertiseList")
    Call<String> getAdvertise
            (
                    @Field("Lat") String Lat,
                    @Field("Long") String Long
            );

}
