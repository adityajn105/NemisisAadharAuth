package com.nemesis.nemesis.Activities;

import static com.nemesis.nemesis.ActivityIdentifiers.AUTH_RESULT;
import static com.nemesis.nemesis.ActivityIdentifiers.FINGERPRINT_ERROR;
import static com.nemesis.nemesis.ActivityIdentifiers.FINGERPRINT_SCAN_CODE;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nemesis.nemesis.Aadhar.fm220.FM220Interface;
import com.nemesis.nemesis.Aadhar.fm220.IFM220;
import com.nemesis.nemesis.R;


import static com.nemesis.nemesis.ActivityIdentifiers.UID;

public class ScanningScreen extends AppCompatActivity implements IFM220 {
    private Context context;
    private TextView tvStatus;
    private Thread mRunningOp = null;
    private final Object mCond = new Object();
    private String uid;

    FM220Interface fm220;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // End of Full Screen




        setContentView(R.layout.activity_scanning_screen);
        tvStatus = (TextView) findViewById(R.id.textViewStatus);
        context = this;

        Intent inte = getIntent();
        uid = inte.getStringExtra(UID);
        fm220 = new FM220Interface();

        try
        {
            fm220.initialize(this, this);
        }catch(Exception ex)
        {
            Intent intent = new Intent();
            intent.putExtra(AUTH_RESULT,ex.getMessage());
            setResult(FINGERPRINT_ERROR,intent);
            finish();
        }

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startGrab();
            }
        });*/
    }


    @Override
    public void onBackPressed()
    {
        CloseDevice();
        super.onBackPressed();
    }

    @Override
    public void onDestroy()
    {
        CloseDevice();
        super.onDestroy();
    }
    private void CloseDevice()
    {
        try
        {
            fm220.unInitialize();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void startGrab()
    {
        fm220.captureFMR();
    }



    @Override
    public void onFM220DeviceConnectError(final String error) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(ScanningScreen.this, error,Toast.LENGTH_LONG).show();
                ScanningScreen.this.finish();
            }
        });
    }


    @Override
    public void onFM220PermissionDevice() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Permission denied!",Toast.LENGTH_LONG).show();
        ScanningScreen.this.finish();

    }


    @Override
    public void onFM220DeviceConnected() {
        // TODO Auto-generated method stub

        startGrab();
    }

    @Override
    public void onFM220CaptureError(final String error) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(ScanningScreen.this, error,Toast.LENGTH_LONG).show();
                ScanningScreen.this.finish();
            }
        });
    }


    @Override
    public void onFM220CaptureCompleted(final byte[] isoTemplate) {
        // TODO Auto-generated method stub

        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent(ScanningScreen.this, ProcessingActivity.class);
                intent.putExtra("UID", uid);
                intent.putExtra("ISO_TEMPLATE", isoTemplate);
                intent.putExtra("AUTH_TYPE", "FMR");
                intent.putExtra("MANUFACTURER_ID", "Startek");
                intent.putExtra("MODEL_ID", "FM220");
                intent.putExtra("SERIAL_NUMBER", "000000");
                intent.putExtra("BIO_TYPE", "FMR");
                startActivityForResult(intent,FINGERPRINT_SCAN_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FINGERPRINT_SCAN_CODE){
            Intent intent = new Intent();
            intent.putExtra(AUTH_RESULT,data.getStringExtra(AUTH_RESULT));
            setResult(resultCode,intent);
            finish();
        }
    }
}
