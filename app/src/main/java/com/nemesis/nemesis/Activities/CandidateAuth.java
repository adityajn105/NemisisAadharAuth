package com.nemesis.nemesis.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ecs.pidgen.data.BiometricData;
import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.onClick;

public class CandidateAuth extends AppCompatActivity {

    @BindView(R.id.profile)
    CircleImageView profile;
    @BindView(R.id.rollno)
    TextView rollno;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.instruction)
    TextView instruction;

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

        Picasso.with(getApplicationContext()).load("http://35.154.117.178/uploads/883758165948.jpe").noFade().into(profile);

    }


    @OnClick(R.id.biometric)
    public void onBiometricAuth(){
        startActivity(new Intent(getApplicationContext(),CandidateKyc.class));
    }



    @Override
    public void onBackPressed() {}
}
