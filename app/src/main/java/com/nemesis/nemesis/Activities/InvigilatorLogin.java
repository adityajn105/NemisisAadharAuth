package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nemesis.nemesis.R;

public class InvigilatorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invigilator_login);

        startActivity(new Intent(getApplicationContext(),CandidateLogin.class));
    }
}
