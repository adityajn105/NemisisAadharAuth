package com.nemesis.nemesis.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemesis.nemesis.Activities.CandidateKyc;
import com.nemesis.nemesis.Activities.CandidateList;
import com.nemesis.nemesis.Activities.CandidateLogin;
import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.Prefs.PrefUtils;
import com.nemesis.nemesis.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TopFragment extends Fragment {

    @BindView(R.id.invigilatorprofile) CircleImageView invigilatorProfile;
    @BindView(R.id.backbutton) ImageView back;
    @BindView(R.id.invigilatorname) TextView name;
    @BindView(R.id.center) TextView center;

    private int act;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_top, container, false);
        ButterKnife.bind(this,v);
        act= ActivityIdentifiers.getCurrentScreen(getContext());
        switch (act){
            case ActivityIdentifiers.CANDIDATE_AUTH_SCREEN:
                back.setVisibility(View.GONE);
                break;
            case ActivityIdentifiers.CANDIDATE_KYC_SCREEN:
                back.setVisibility(View.VISIBLE);
                break;
            case ActivityIdentifiers.CANDIDATE_LOGIN_SCREEN:
                back.setVisibility(View.GONE);
                break;
            case ActivityIdentifiers.CANDIDATE_LIST_SCREEN:
                back.setVisibility(View.VISIBLE);
                break;
            case ActivityIdentifiers.INVIGILATOR_LOGIN_SCREEN:
                back.setVisibility(View.GONE);
                break;
        }
        Picasso.with(getContext()).load("http://35.154.117.178/"+ PrefUtils.getInvigilatorProfile(getContext())).into(invigilatorProfile);
        name.setText(PrefUtils.getInvigilatorName(getContext()));
        center.setText("Center : "+PrefUtils.getInvigilatorCenter(getContext()));

        return v;
    }

    @OnClick(R.id.backbutton)
    public void onBackClicked(){
        switch (act){
            case ActivityIdentifiers.CANDIDATE_LIST_SCREEN:
                ((CandidateList)getContext()).goBack();
                break;
            case ActivityIdentifiers.CANDIDATE_KYC_SCREEN:
                ((CandidateKyc)getContext()).goBack();
                break;
        }
    }




}
