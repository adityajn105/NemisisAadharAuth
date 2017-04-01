package com.nemesis.nemesis.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 4/2/17.
 */

public class DefaultRequest {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("ikey")
    @Expose
    private String ikey;

    @SerializedName("rollno")
    @Expose
    private String rollno;


    public DefaultRequest(String id, String ikey, String rollno) {
        this.id = id;
        this.ikey = ikey;
        this.rollno = rollno;
    }
}
