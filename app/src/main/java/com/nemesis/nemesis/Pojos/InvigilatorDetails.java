package com.nemesis.nemesis.Pojos;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 4/1/17.
 */

public class InvigilatorDetails {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("aadhar")
    @Expose
    private String aadhar;

    @SerializedName("center")
    @Expose
    private String center;

    @SerializedName("ikey")
    @Expose
    private String ikey;

    @SerializedName("profile")
    @Expose
    private String profile;


    public int getStatuscode() {
        return statuscode;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getCenter() {
        return center;
    }

    public String getIkey() {
        return ikey;
    }

    public String getProfile() {
        return profile;
    }
}
