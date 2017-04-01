package com.nemesis.nemesis.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by aditya on 4/1/17.
 */

public class HttpRequest {
    //public static API_URL="http://";

    public static Retrofit retrofit= new Retrofit.Builder()
  //          .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface RegistrationIterface{
       /* @POST("in")
        Call<UserResponse> getUserInfo(@Body UserRequest request);

        @Multipart
        @POST("")*/

    }
}
