package com.nemesis.nemesis.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 4/2/17.
 */

public class CandidateInfo {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;

    @SerializedName("rollno")
    @Expose
    private String rollno;

    @SerializedName("fname")
    @Expose
    private String fname;

    @SerializedName("lname")
    @Expose
    private String lname;

    @SerializedName("aadhar")
    @Expose
    private String aadhar;

    @SerializedName("profile")
    @Expose
    private String profile;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getRollno() {
        return rollno;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getProfile() {
        return profile;
    }
}
