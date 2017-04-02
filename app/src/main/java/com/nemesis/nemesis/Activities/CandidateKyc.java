package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.ApiResponseCodes;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.Http.HttpRequest;
import com.nemesis.nemesis.Pojos.CandidateDetails;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CandidateKyc extends AppCompatActivity {

    @BindView(R.id.profile) ImageView profile;
    @BindView(R.id.attempts) TextView attempts;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.rollno) TextView rollno;
    @BindView(R.id.center) TextView center;
    @BindView(R.id.dob) TextView dob;
    @BindView(R.id.name) TextView name;

    ApiResponseCodes arc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_kyc);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.CANDIDATE_KYC_SCREEN);

        getSupportFragmentManager().beginTransaction().add(R.id.topFrame,new TopFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.bottomframe,new BottomFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        Intent intent=getIntent();
        String rollno=intent.getStringExtra("rollno");
        viewKyc(rollno);

        arc=new ApiResponseCodes();
    }

    public void viewKyc(final String enroll){
        rx.Observable.create(new rx.Observable.OnSubscribe<CandidateDetails>() {
            @Override
            public void call(final Subscriber<? super CandidateDetails> subscriber) {
                HttpRequest.ExamApiInterface examInterface = HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
                Call<CandidateDetails> responseCall = examInterface.getCandidatesDetails(
                        new DefaultRequest(
                                PrefUtils.getInvigilatorId(getApplicationContext()),
                                PrefUtils.getInvigilatorKey(getApplicationContext()),
                                enroll
                        )
                );
                responseCall.enqueue(new Callback<CandidateDetails>() {
                    @Override
                    public void onResponse(Call<CandidateDetails> call, Response<CandidateDetails> response) {
                        if(response.body().getStatuscode()==200){
                            subscriber.onNext(response.body());
                        }
                        else{
                            new SweetAlertDialog(CandidateKyc.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error : "+response.body().getStatuscode())
                                    .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CandidateDetails> call, Throwable t) {
                        new SweetAlertDialog(CandidateKyc.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Something Went Wrong")
                                .setContentText("Check Your Internet Connection")
                                .show();
                    }
                });
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CandidateDetails>() {
                    @Override
                    public void call(CandidateDetails candidateDetails) {
                        Picasso.with(getApplicationContext()).load("http://35.154.117.178/"+candidateDetails.getProfile()).noFade().into(profile);
                        rollno.setText("Enrollment No : "+candidateDetails.getRollno());
                        center.setText("Center : "+PrefUtils.getInvigilatorCenter(getApplicationContext()));
                        name.setText(candidateDetails.getFname()+" "+candidateDetails.getLname());
                        status.setText("Status : "+candidateDetails.getStatus() );
                        attempts.setText("Attempts : "+candidateDetails.getAttempts());
                        dob.setText("DOB : "+candidateDetails.getDob());
                    }
                });

    }

    public void goBack(){
        startActivity(new Intent(getApplicationContext(),CandidateLogin.class));
    }

    @Override
    public void onBackPressed() {}

    public void listClicked(){
        startActivity(new Intent(getApplicationContext(),CandidateList.class));
    }


    public void logOut(){
        PrefUtils.logout(getApplicationContext());
        startActivity(new Intent(getApplicationContext(),InvigilatorLogin.class));
    }
}
