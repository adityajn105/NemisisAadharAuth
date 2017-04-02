package com.nemesis.nemesis.Aadhar.util;

import com.nemesis.nemesis.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

public class Global {

	public static String Url = "http://35.154.117.178:8080/AuthAPIExSampleServer/AuthAPIServiceSample";

    public static int SELECTED_AUTH = 0;

    public static void showInfoDialog(Context ctx, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        // Setting Dialog Title
		alertDialog.setTitle("Information");
 
        // Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.info);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				// Write your code here to execute after dialog closed
				//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}

	public static void showErrorDialog(Context ctx, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        // Setting Dialog Title
		alertDialog.setTitle("Error");
 
        // Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting Icon to Dialog
		//alertDialog.setIcon(R.drawable.error_icon);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				// Write your code here to execute after dialog closed
				//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}

	public static void showInfoDialogWithFinish(final Activity activity,String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        // Setting Dialog Title
		alertDialog.setTitle("Information");
 
        // Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.info);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				
				activity.finish();
				// Write your code here to execute after dialog closed
				//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}

	public static void showErrorDialogWithFinish(final Activity activity,String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        // Setting Dialog Title
		alertDialog.setTitle("Error");
 
        // Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting Icon to Dialog
		//alertDialog.setIcon(R.drawable.error_icon);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				
				activity.finish();
				// Write your code here to execute after dialog closed
				//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}

	public static boolean isNetworkAvailable(Activity activity) 
    {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
	
	public static String getAndroidVersion()
	{
		String[] mapper = new String[] {
			    "ANDROID BASE", "ANDROID BASE 1.1", "ANDROID CUPCAKE", "ANDROID DONUT",
			    "ANDROID ECLAIR", "ANDROID ECLAIR_0_1", "ANDROID ECLAIR_MR1", "ANDROID FROYO", "ANDROID GINGERBREAD",
			    "ANDROID GINGERBREAD_MR1", "ANDROID HONEYCOMB", "ANDROID HONEYCOMB_MR1", "ANDROID HONEYCOMB_MR2",
			    "ANDROID ICE_CREAM_SANDWICH", "ANDROID ICE_CREAM_SANDWICH_MR1", "ANDROID JELLY_BEAN", "ANDROID JELLY BEAN MR1", 
			    "ANDROID JELLY BEAN MR2", "ANDROID KITKAT"
			};
			int index = Build.VERSION.SDK_INT - 1;
			String versionName = index < mapper.length? mapper[index] : "UNKNOWN_VERSION (v" + Build.VERSION.SDK_INT +")" ; //
			return versionName;
	}
}
