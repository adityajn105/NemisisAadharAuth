package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CandidateKyc extends AppCompatActivity {

    @BindView(R.id.profile) ImageView profile;
    @BindView(R.id.attempts) TextView attempts;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.rollno) TextView rollno;
    @BindView(R.id.center) TextView center;
    @BindView(R.id.name) TextView name;

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

        Picasso.with(getApplicationContext()).load("http://35.154.117.178/uploads/883758165948.jpe").noFade().into(profile);


    }

    public void goBack(){
        startActivity(new Intent(getApplicationContext(),CandidateLogin.class));
    }

    @Override
    public void onBackPressed() {}
}
