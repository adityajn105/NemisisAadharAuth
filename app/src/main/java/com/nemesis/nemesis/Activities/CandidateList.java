package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.Adapters.CandidateListAdapter;
import com.nemesis.nemesis.ApiResponseCodes;
import com.nemesis.nemesis.Fragments.BottomFragment;
import com.nemesis.nemesis.Fragments.TopFragment;
import com.nemesis.nemesis.Http.HttpRequest;
import com.nemesis.nemesis.Pojos.MyCandidates;
import com.nemesis.nemesis.Prefs.PrefUtils;
import com.nemesis.nemesis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CandidateList extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    ApiResponseCodes arc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.CANDIDATE_LIST_SCREEN);
        arc=new ApiResponseCodes();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportFragmentManager().beginTransaction().add(R.id.topFrame,new TopFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.bottomframe,new BottomFragment()).addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_NONE).commit();
        getData();
    }

    public void getData(){
        rx.Observable.create(new rx.Observable.OnSubscribe<MyCandidates>() {
            @Override
            public void call(final Subscriber<? super MyCandidates> subscriber) {
                HttpRequest.ExamApiInterface examInterface = HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
                Call<MyCandidates> responseCall = examInterface.getAllCandidates(
                        RequestBody.create(MediaType.parse("text/string"),PrefUtils.getInvigilatorId(getApplicationContext())),
                        RequestBody.create(MediaType.parse("text/string"),PrefUtils.getInvigilatorKey(getApplicationContext()))
                );
                responseCall.enqueue(new Callback<MyCandidates>() {
                    @Override
                    public void onResponse(Call<MyCandidates> call, Response<MyCandidates> response) {
                        if(response.body().getStatuscode()==200){
                            subscriber.onNext(response.body());
                        }
                        else{
                            new SweetAlertDialog(CandidateList.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error : "+response.body().getStatuscode())
                                    .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(Call<MyCandidates> call, Throwable t) {
                        new SweetAlertDialog(CandidateList.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Something Went Wrong")
                                .setContentText("Check Your Internet Connection")
                                .show();
                    }
                });
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MyCandidates>() {
                    @Override
                    public void call(MyCandidates myCandidates) {
                        recyclerView.setAdapter(new CandidateListAdapter(getApplicationContext(),myCandidates.getList()));
                    }
                });
    }

    @Override
    public void onBackPressed() {}

    public void goBack(){
        startActivity(new Intent(getApplicationContext(),CandidateLogin.class));
    }


    public void logOut(){
        PrefUtils.logout(getApplicationContext());
        startActivity(new Intent(getApplicationContext(),InvigilatorLogin.class));
    }
}
