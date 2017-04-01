package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.R;

import butterknife.ButterKnife;

public class InvigilatorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invigilator_login);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.INVIGILATOR_LOGIN_SCREEN);

        onInvigilatorLogin();
    }


    public void onInvigilatorLogin(){
        startActivity(new Intent(getApplicationContext(),CandidateLogin.class));
    }

    @Override
    public void onBackPressed() {}
}
