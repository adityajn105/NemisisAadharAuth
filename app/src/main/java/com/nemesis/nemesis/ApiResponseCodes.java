package com.nemesis.nemesis;

import java.util.HashMap;

/**
 * Created by aditya on 4/1/17.
 */

public class ApiResponseCodes {

    private HashMap<Integer,String> codes;

    public ApiResponseCodes(){
        codes=new HashMap<Integer,String>();
        codes.put(200,"Success");
        codes.put(404,"Invigilator or Student not Found.");
        codes.put(401,"Invalid Login, Wrong secret Code.");
        codes.put(406,"Not Accepted, Student and Invigilator Center not same.");
        codes.put(500,"Something went Wrong!! Server Error.");
        codes.put(208,"Candidate Already Authenticated");
    }

    public String getResponsePhrase(int code){
        return codes.get(code);
    }

}
