package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ecs.pidgen.PIDGenerator;
import com.ecs.pidgen.PidData;
import com.ecs.pidgen.data.BioMetricType;
import com.ecs.pidgen.data.BiometricData;
import com.ecs.pidgen.data.BiometricPosition;
import com.nemesis.nemesis.Aadhar.apiex.server.xsd.ECSAuthRequestEx;
import com.nemesis.nemesis.Aadhar.apiex.server.xsd.ECSAuthResponseEx;
import com.nemesis.nemesis.Aadhar.util.Global;
import com.nemesis.nemesis.Aadhar.util.HttpConnector;
import com.nemesis.nemesis.Aadhar.util.XMLUtilities;
import com.nemesis.nemesis.R;


import java.util.ArrayList;
import java.util.List;

import static com.nemesis.nemesis.ActivityIdentifiers.AUTH_RESULT;
import static com.nemesis.nemesis.ActivityIdentifiers.BIO_SUCCESS;
import static com.nemesis.nemesis.ActivityIdentifiers.FINGERPRINT_ERROR;

public class ProcessingActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    private byte[]isoTemplate;
    private String uid;
    private String authType;
    private String manufacturerId;
    private String modelId;
    private String serialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // End of Full Screen


        Bundle bundle = this.getIntent().getExtras();
        isoTemplate = (byte[]) bundle.getSerializable("ISO_TEMPLATE");
        uid = (String) bundle.getSerializable("UID");
        authType = (String) bundle.getSerializable("AUTH_TYPE");
        manufacturerId = (String) bundle.getSerializable("MANUFACTURER_ID");
        modelId = (String) bundle.getSerializable("MODEL_ID");
        serialNumber = (String) bundle.getSerializable("SERIAL_NUMBER");


        setContentView(R.layout.activity_processing);

        new Thread()
        {
            public void run()
            {
                try
                {
                    String action = "";
                    if(Global.SELECTED_AUTH == 0)
                        action = "AUTH";
                    else
                        action = "KYC";

                    BiometricData fmrAuthBio = new BiometricData(BiometricPosition.UNKNOWN, BioMetricType.FMR, isoTemplate);
                    List<BiometricData> bioList = new ArrayList<BiometricData>();
                    bioList.add(fmrAuthBio);


                    // generate PID Block (PID Block should be generated in the system capturing biometrics)
                    PIDGenerator pidGenerator = new PIDGenerator();
                    PidData pidData = pidGenerator.generateBiometricPIDBlock("1.0", bioList, getResources().openRawResource(R.raw.uidai_auth_encrypt_preprod));


                    ECSAuthRequestEx req = new ECSAuthRequestEx();
                    req.setAadhaarNumber(uid);
                    req.setCi(pidData.getCertificateIdentifier());
                    req.setFdc("NC");
                    req.setIdc("NA");
                    req.sethMac(pidData.getEncryptedHmac());
                    req.setKey(pidData.getEncryptedSessionKey());
                    req.setPid(pidData.getEncPIDData());
                    req.setPidTs(pidData.getPidTimestamp());
                    req.setPidType(pidData.getPidDataType());
                    req.setSubType(pidData.getPidType());
                    req.setTerminalId("public");

                    final String data = XMLUtilities.getXML(req);

                    final String resString =  HttpConnector.getInstance().postData(data);

                    if(resString.startsWith("<Error>"))
                    {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                //pd.dismiss();
                                String errorMessage = getErrorMessage(resString);
                                showErrorActivity(errorMessage);
                            }
                        });
                        return ;
                    }

                    /************** AUTH ***************************/
                    if(!resString.contains("<ECSAuthResponseEx "))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                //pd.dismiss();
                                String errorMessage = getErrorMessage(resString);
                                showErrorActivity(errorMessage);
                            }
                        });
                        return;
                    }

                    final ECSAuthResponseEx res =  (ECSAuthResponseEx) XMLUtilities.parseXML(ECSAuthResponseEx.class, resString);

                    if(res.isErr()== true)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {

                                String errorMessage = res.getErrCode() + "-" + res.getErrMsg();
                                showErrorActivity(errorMessage);
                                ProcessingActivity.this.finish();
                            }
                        });
                        return ;
                    }

                    Intent intent = new Intent();
                    intent.putExtra("AUTH_RESULT",true);
                    setResult(BIO_SUCCESS,intent);
                    finish();
                    return;
                    /************** END OF AUTH ***************************/


                }catch (final Exception e){
                    e.printStackTrace();
                    showErrorActivity("Error sending Authentication request!\n" + e.getMessage());
                }
            } // run()
        }.start();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

    public String getErrorMessage(String error)
    {
        int startIndex = "<Error>".length();
        int endIndex = error.indexOf("</Error>");

        if(endIndex != -1) {
            return error.substring(startIndex, endIndex);
        }
        return "";
    }

    private void showErrorActivity(final String message)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent();
                intent.putExtra(AUTH_RESULT, message);
                setResult(FINGERPRINT_ERROR,intent);
                finish();
            }
        });
    }
}
