package com.nemesis.nemesis.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 4/2/17.
 */

public class DefaultResponse {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;

    public int getStatuscode() {
        return statuscode;
    }
}
