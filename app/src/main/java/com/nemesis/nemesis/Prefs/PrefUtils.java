package com.nemesis.nemesis.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nemesis.nemesis.Pojos.InvigilatorDetails;

/**
 * Created by aditya on 4/1/17.
 */

public class PrefUtils {

    private static final String INVIGILATOR_ID="INVIGILATOR_ID";
    private static final String INVIGILATOR_KEY="INVIGILATOR_KEY";
    private static final String INVIGILATOR_NAME="INVIGILATOR_NAME";
    private static final String INVIGILATOR_CENTER="INVIGILATOR_CENTER";
    private static final String INVIGILATOR_LOGIN_STATUS="INVIGILATOR_LOGIN_STATUS";
    private static final String INVIGILATOR_PROFILE="INVIGILATOR_PROFILE";


    public static String getInvigilatorId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(INVIGILATOR_ID,"");
    }
    public static String getInvigilatorKey(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(INVIGILATOR_KEY,"");
    }
    public static String getInvigilatorCenter(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(INVIGILATOR_CENTER,"");
    }
    public static String getInvigilatorName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(INVIGILATOR_NAME,"");
    }
    public static String getInvigilatorProfile(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(INVIGILATOR_PROFILE,"");
    }
    public static boolean getLoginStatus(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(INVIGILATOR_LOGIN_STATUS,false);
    }

    public static void logout(Context context){
        SharedPreferences.Editor edit=PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(INVIGILATOR_KEY,"");
        edit.putString(INVIGILATOR_CENTER,"");
        edit.putBoolean(INVIGILATOR_LOGIN_STATUS,false);
        edit.putString(INVIGILATOR_ID,"");
        edit.putString(INVIGILATOR_NAME,"");
        edit.putString(INVIGILATOR_PROFILE,"");
        edit.apply();
    }

    public static void login(Context context,InvigilatorDetails invigilatorDetails){
        SharedPreferences.Editor edit=PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(INVIGILATOR_KEY,invigilatorDetails.getIkey());
        edit.putString(INVIGILATOR_ID,invigilatorDetails.getId());
        edit.putString(INVIGILATOR_NAME,invigilatorDetails.getName());
        edit.putString(INVIGILATOR_CENTER,invigilatorDetails.getCenter());
        edit.putString(INVIGILATOR_PROFILE,invigilatorDetails.getProfile());
        edit.putBoolean(INVIGILATOR_LOGIN_STATUS,true);
        edit.apply();
    }

}
