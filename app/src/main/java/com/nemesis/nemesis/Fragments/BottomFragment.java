package com.nemesis.nemesis.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nemesis.nemesis.Activities.CandidateAuth;
import com.nemesis.nemesis.Activities.CandidateKyc;
import com.nemesis.nemesis.Activities.CandidateList;
import com.nemesis.nemesis.Activities.CandidateLogin;
import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.Prefs.PrefUtils;
import com.nemesis.nemesis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomFragment extends Fragment {

    @BindView(R.id.home) LinearLayout home;
    @BindView(R.id.list) LinearLayout list;
    @BindView(R.id.instruct) LinearLayout instructions;
    @BindView(R.id.logout) LinearLayout logout;
    @BindView(R.id.more) LinearLayout more;

    private int act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_bottom, container, false);
        ButterKnife.bind(this,v);
        act= ActivityIdentifiers.getCurrentScreen(getContext());


        return v;
    }


    @OnClick(R.id.home)
    public void homeClicked() {
        switch (act) {
            case ActivityIdentifiers.CANDIDATE_LIST_SCREEN:
                ((CandidateList)getContext()).goBack();
                break;
            case ActivityIdentifiers.CANDIDATE_KYC_SCREEN:
                ((CandidateKyc)getContext()).goBack();
                break;
        }
    }

    @OnClick(R.id.list)
    public void listClicked(){
        switch (act){
            case ActivityIdentifiers.CANDIDATE_LIST_SCREEN:
                break;
            case ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN:
                ((CandidateLogin)getContext()).listClicked();
                break;
            case ActivityIdentifiers.CANDIDATE_KYC_SCREEN:
                ((CandidateKyc)getContext()).listClicked();
        }
    }

    @OnClick(R.id.more)
    public void moreClicked(){
        switch(act){
            case ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN:
            //    ((CandidateLogin)getContext()).
                break;
        }
    }

    @OnClick(R.id.logout)
    public void logoutClicked(){
        switch (act){
            case ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN:
                ((CandidateLogin)getContext()).logOut();
                break;
            case ActivityIdentifiers.CANDIDATE_LIST_SCREEN:
                ((CandidateList)getContext()).logOut();
                break;
            case ActivityIdentifiers.CANDIDATE_KYC_SCREEN:
                ((CandidateKyc)getContext()).logOut();
                break;
        }
    }

    @OnClick(R.id.instruct)
    public void instructionClicked(){
        switch(act){
            case ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN:
                ((CandidateLogin)getContext()).instruct();
        }
    }

}
