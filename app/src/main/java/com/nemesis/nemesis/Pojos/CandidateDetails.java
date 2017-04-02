package com.nemesis.nemesis.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 4/2/17.
 */

public class CandidateDetails {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;

    @SerializedName("fname")
    @Expose
    private String fname;

    @SerializedName("lname")
    @Expose
    private String lname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("rollno")
    @Expose
    private String rollno;

    @SerializedName("profile")
    @Expose
    private String profile;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("attempts")
    @Expose
    private String attempts;

    public int getStatuscode() {
        return statuscode;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getRollno() {
        return rollno;
    }

    public String getProfile() {
        return profile;
    }

    public String getStatus() {
        return status;
    }

    public String getAttempts() {
        return attempts;
    }
}
