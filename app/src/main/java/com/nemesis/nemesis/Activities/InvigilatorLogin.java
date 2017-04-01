package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvigilatorLogin extends AppCompatActivity {

    @BindView(R.id.id)
    TextInputEditText id;
    @BindView(R.id.key)
    TextInputEditText key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invigilator_login);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.INVIGILATOR_LOGIN_SCREEN);


    }

    @OnClick(R.id.login)
    public void onInvigilatorLogin(){
        startActivity(new Intent(getApplicationContext(),CandidateLogin.class));
    }

    @Override
    public void onBackPressed() {}





}
