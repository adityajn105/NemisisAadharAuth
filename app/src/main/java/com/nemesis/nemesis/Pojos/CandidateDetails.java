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
    private int fname;

    @SerializedName("lname")
    @Expose
    private int lname;

    @SerializedName("email")
    @Expose
    private int email;

    @SerializedName("dob")
    @Expose
    private int dob;

    @SerializedName("rollno")
    @Expose
    private int rollno;

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

    public int getFname() {
        return fname;
    }

    public int getLname() {
        return lname;
    }

    public int getEmail() {
        return email;
    }

    public int getDob() {
        return dob;
    }

    public int getRollno() {
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
