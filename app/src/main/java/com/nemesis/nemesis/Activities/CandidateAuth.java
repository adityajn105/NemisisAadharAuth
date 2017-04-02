package com.nemesis.nemesis.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ecs.pidgen.data.BiometricData;
import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.ApiResponseCodes;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.Http.HttpRequest;
import com.nemesis.nemesis.Pojos.CandidateInfo;
import com.nemesis.nemesis.Pojos.DefaultRequest;
import com.nemesis.nemesis.Pojos.DefaultResponse;
import com.nemesis.nemesis.Prefs.PrefUtils;
import com.nemesis.nemesis.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static android.R.attr.onClick;
import static com.nemesis.nemesis.ActivityIdentifiers.AUTH_RESULT;
import static com.nemesis.nemesis.ActivityIdentifiers.BIO_SUCCESS;
import static com.nemesis.nemesis.ActivityIdentifiers.FINGERPRINT_SCAN_CODE;
import static com.nemesis.nemesis.ActivityIdentifiers.UID;

public class CandidateAuth extends AppCompatActivity {

    @BindView(R.id.profile)
    CircleImageView profilephoto;
    @BindView(R.id.rollno)
    TextView rollno;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.instruction)
    TextView instruction;
    private String nam;
    private String enroll;

    @BindView(R.id.biometric)
    Button biometric;
    private String profile;

    ApiResponseCodes arc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_auth);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.CANDIDATE_AUTH_SCREEN);

        getSupportFragmentManager().beginTransaction().add(R.id.topFrame,new TopFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.bottomframe,new BottomFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        Intent intent=getIntent();
        nam=intent.getStringExtra("name");
        enroll=intent.getStringExtra("rollno");
        profile=intent.getStringExtra("profile");
        final String aadhar=intent.getStringExtra("aadhar");
        arc=new ApiResponseCodes();

        Picasso.with(getApplicationContext()).load("http://35.154.117.178/"+profile).noFade().into(profilephoto);
        rollno.setText("Enrollment No : "+enroll);
        name.setText(nam);

        biometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ScanningScreen.class);
                intent.putExtra(UID,aadhar);
                startActivityForResult(intent, FINGERPRINT_SCAN_CODE);
            }
        });



    }

/*

    @OnClick(R.id.biometric)
    public void performBiometric(String aadhar){
        Intent intent=new Intent(getApplicationContext(),ScanningScreen.class);
        intent.putExtra(UID,aadhar);
        startActivityForResult(intent, FINGERPRINT_SCAN_CODE);
       */
/* PrefUtils.newLogIn(getApplicationContext(), invigilator);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));*//*

    }
*/




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FINGERPRINT_SCAN_CODE) {

            rx.Observable.create(new rx.Observable.OnSubscribe<DefaultResponse>() {
                @Override
                public void call(final Subscriber<? super DefaultResponse> subscriber) {
                    HttpRequest.ExamApiInterface examInterface = HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
                    Call<DefaultResponse> responseCall = examInterface.bioAttempt(
                            new DefaultRequest(
                                    PrefUtils.getInvigilatorId(getApplicationContext()),
                                    PrefUtils.getInvigilatorKey(getApplicationContext()),
                                    enroll
                            )
                    );
                    responseCall.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if(response.body().getStatuscode()==200){
                                subscriber.onNext(response.body());
                            }
                            else{
                                new SweetAlertDialog(CandidateAuth.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error : "+response.body().getStatuscode())
                                        .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                        .show();
                            }
                        }
                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            new SweetAlertDialog(CandidateAuth.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Something Went Wrong")
                                    .setContentText("Check Your Internet Connection")
                                    .show();
                        }
                    });
                }
            })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<DefaultResponse>() {
                        @Override
                        public void call(DefaultResponse defaultResponse) {}});

            if(resultCode == BIO_SUCCESS) {

                rx.Observable.create(new rx.Observable.OnSubscribe<DefaultResponse>() {
                    @Override
                    public void call(final Subscriber<? super DefaultResponse> subscriber) {
                        HttpRequest.ExamApiInterface examInterface = HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
                        Call<DefaultResponse> responseCall = examInterface.authSuccess(
                                new DefaultRequest(
                                        PrefUtils.getInvigilatorId(getApplicationContext()),
                                        PrefUtils.getInvigilatorKey(getApplicationContext()),
                                        enroll
                                )
                        );
                        responseCall.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if(response.body().getStatuscode()==200){
                                    subscriber.onNext(response.body());
                                }
                                else{
                                    new SweetAlertDialog(CandidateAuth.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Error : "+response.body().getStatuscode())
                                            .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                            .show();
                                }
                            }
                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                new SweetAlertDialog(CandidateAuth.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Something Went Wrong")
                                        .setContentText("Check Your Internet Connection")
                                        .show();
                            }
                        });
                    }
                })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<DefaultResponse>() {
                            @Override
                            public void call(DefaultResponse defaultResponse) {
                                new SweetAlertDialog(CandidateAuth.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Authentication Successful")
                                        .setContentText("Click OK to view KYC")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent intent=new Intent(getApplicationContext(),CandidateKyc.class);
                                                intent.putExtra("rollno",enroll);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        });

                new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Candidate Verified!!")
                        .setContentText("You are being redirected to Candidate Detail Screen")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent=new Intent(getApplicationContext(),CandidateKyc.class);
                                intent.putExtra("rollno",enroll);
                                startActivity(intent);
                            }
                        })
                        .show();
            }else{
                new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(data.getStringExtra(AUTH_RESULT))
                        .setContentText("Retry or Report Impersonation")
                        .setConfirmText("Retry")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .setCancelText("Report Impersonation")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                new SweetAlertDialog(CandidateAuth.this,SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Are you Sure?")
                                        .setContentText("Student cant be verified again if you click yes.")
                                        .setConfirmText("Yes")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                reportImpersonation();
                                            }
                                        })
                                        .setCancelText("Retry")
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.cancel();
                                            }
                                        }).show();
                            }
                        })
                        .show();
            }

        }
    }


    public void reportImpersonation(){
        rx.Observable.create(new rx.Observable.OnSubscribe<DefaultResponse>() {
            @Override
            public void call(final Subscriber<? super DefaultResponse> subscriber) {
                HttpRequest.ExamApiInterface examInterface = HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
                Call<DefaultResponse> responseCall = examInterface.reportImpersonation(
                        new DefaultRequest(
                                PrefUtils.getInvigilatorId(getApplicationContext()),
                                PrefUtils.getInvigilatorKey(getApplicationContext()),
                                enroll
                        )
                );
                responseCall.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if(response.body().getStatuscode()==200){
                            subscriber.onNext(response.body());
                        }
                        else{
                            new SweetAlertDialog(CandidateAuth.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error : "+response.body().getStatuscode())
                                    .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        new SweetAlertDialog(CandidateAuth.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Something Went Wrong")
                                .setContentText("Check Your Internet Connection")
                                .show();
                    }
                });
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DefaultResponse>() {
                    @Override
                    public void call(DefaultResponse defaultResponse) {
                        new SweetAlertDialog(CandidateAuth.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Impersonation Successfully reported!")
                                .setContentText("Redirecting to Home!!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent=new Intent(getApplicationContext(),CandidateLogin.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                });

    }





    @Override
    public void onBackPressed() {}
}
