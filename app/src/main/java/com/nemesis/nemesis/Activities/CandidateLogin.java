package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
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
import com.nemesis.nemesis.ApiResponseCodes;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.Http.HttpRequest;
import com.nemesis.nemesis.Pojos.CandidateInfo;
import com.nemesis.nemesis.Pojos.DefaultRequest;
import com.nemesis.nemesis.Pojos.InvigilatorDetails;
import com.nemesis.nemesis.Prefs.PrefUtils;
import com.nemesis.nemesis.Qr.BarcodeCaptureActivity;
import com.nemesis.nemesis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CandidateLogin extends AppCompatActivity {

    @BindView(R.id.topFrame) FrameLayout topfragment;
    @BindView(R.id.rollno) TextInputEditText enroll;
    ApiResponseCodes arc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_login);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN);
        arc=new ApiResponseCodes();
        getSupportFragmentManager().beginTransaction().add(R.id.topFrame,new TopFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.bottomframe,new BottomFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

    }

    @Override
    public void onBackPressed() {}

    @OnClick(R.id.qr)
    public void onClickQr(){
        Intent intent=new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent,ActivityIdentifiers.BARCODE_READER_CODE);
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
                            .setContentText("Enter Enrollment Number Manually")
                            .setTitleText("Something went wrong!")
                            .show();
                }
            } else {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setContentText("Enter Enrollment Number Manually")
                        .setTitleText("Something went wrong!")
                        .show();
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.authenticate)
    public void onAuthClicked(){
        rx.Observable.create(new rx.Observable.OnSubscribe<CandidateInfo>() {
            @Override
            public void call(final Subscriber<? super CandidateInfo> subscriber) {
                HttpRequest.ExamApiInterface examInterface = HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
                Call<CandidateInfo> responseCall = examInterface.getCandidateInfo(
                        new DefaultRequest(
                                PrefUtils.getInvigilatorId(getApplicationContext()),
                                PrefUtils.getInvigilatorKey(getApplicationContext()),
                                enroll.getText().toString()
                        )
                );
                responseCall.enqueue(new Callback<CandidateInfo>() {
                    @Override
                    public void onResponse(Call<CandidateInfo> call, Response<CandidateInfo> response) {
                        if(response.body().getStatuscode()==200){
                            subscriber.onNext(response.body());
                        }
                        else{
                            new SweetAlertDialog(CandidateLogin.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error : "+response.body().getStatuscode())
                                    .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                    .show();
                            enroll.setText("");
                            enroll.requestFocus();
                        }
                    }
                    @Override
                    public void onFailure(Call<CandidateInfo> call, Throwable t) {
                        new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Something Went Wrong")
                                .setContentText("Check Your Internet Connection")
                                .show();
                    }
                });
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CandidateInfo>() {
                    @Override
                    public void call(CandidateInfo candidateInfo) {
                        Intent intent=new Intent(getApplicationContext(),CandidateAuth.class);
                        intent.putExtra("name",candidateInfo.getFname()+" "+candidateInfo.getLname());
                        intent.putExtra("rollno",candidateInfo.getRollno());
                        intent.putExtra("profile",candidateInfo.getProfile());
                        intent.putExtra("aadhar",candidateInfo.getAadhar());
                        startActivity(intent);
                    }
                });

    }

}
