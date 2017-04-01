package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.Qr.BarcodeCaptureActivity;
import com.nemesis.nemesis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CandidateLogin extends AppCompatActivity {

    @BindView(R.id.topFrame) FrameLayout topfragment;
    @BindView(R.id.rollno) TextInputEditText enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_login);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN);

        getSupportFragmentManager().beginTransaction().add(R.id.topFrame,new TopFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.bottomframe,new BottomFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

    }

    @OnClick(R.id.authenticate)
    public void onClickAuthenticate(){
        startActivity(new Intent(getApplicationContext(),CandidateAuth.class));
    }

    @Override
    public void onBackPressed() {}

    @OnClick(R.id.qr)
    public void scanQr(){
        startActivityForResult(new Intent(getApplicationContext(), BarcodeCaptureActivity.class),
                ActivityIdentifiers.BARCODE_READER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ActivityIdentifiers.BARCODE_READER_CODE){
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    enroll.setText(barcode.displayValue);
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Enter Enrollment Number Manually")
                            .setContentText("Something went wrong!")
                            .show();
                }
            } else {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Enter Enrollment Number Manually")
                        .setContentText("Something went wrong!")
                        .show();
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }
}
