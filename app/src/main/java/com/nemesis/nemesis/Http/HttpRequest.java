package com.nemesis.nemesis.Http;

import com.nemesis.nemesis.Activities.CandidateKyc;
import com.nemesis.nemesis.Pojos.MyCandidates;
import com.nemesis.nemesis.Pojos.CandidateDetails;
import com.nemesis.nemesis.Pojos.CandidateInfo;
import com.nemesis.nemesis.Pojos.DefaultRequest;
import com.nemesis.nemesis.Pojos.DefaultResponse;
import com.nemesis.nemesis.Pojos.InvigilatorDetails;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by aditya on 4/1/17.
 */

public class HttpRequest {
    public static String API_URL="http://35.154.117.178/http/";

    public static Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface ExamApiInterface{
        @POST("slogin.php")
        Call<CandidateInfo> getCandidateInfo(@Body DefaultRequest request);

        @Multipart
        @POST("ilogin.php")
        Call<InvigilatorDetails> getInvigilatorDetails(
               @Part("id") RequestBody id,
                @Part("ikey") RequestBody ikey
        );

        @POST("sdetails.php")
        Call<CandidateDetails> getCandidatesDetails(
                @Body DefaultRequest request
        );

        @POST("bio.php")
        Call<DefaultResponse> bioAttempt(
                @Body DefaultRequest request
        );

        @POST("impersonation.php")
        Call<DefaultResponse> reportImpersonation(
                    @Body DefaultRequest request
        );

        @Multipart
        @POST("mycandidates.php")
        Call<MyCandidates> getAllCandidates(
            @Part("id") RequestBody id,
             @Part("ikey") RequestBody key
        );

        @POST("auth.php")
        Call<DefaultResponse> authSuccess(
             @Body   DefaultRequest request
        );
    }
}
