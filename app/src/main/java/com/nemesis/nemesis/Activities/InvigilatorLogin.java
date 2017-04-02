package com.nemesis.nemesis.Activities;

import android.content.Intent;
import android.database.Observable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nemesis.nemesis.ActivityIdentifiers;
import com.nemesis.nemesis.ApiResponseCodes;
import com.nemesis.nemesis.Http.HttpRequest;
import com.nemesis.nemesis.Pojos.InvigilatorDetails;
import com.nemesis.nemesis.Prefs.PrefUtils;
import com.nemesis.nemesis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.nemesis.nemesis.ActivityIdentifiers.AUTH_RESULT;
import static com.nemesis.nemesis.ActivityIdentifiers.BIO_FAILURE;
import static com.nemesis.nemesis.ActivityIdentifiers.BIO_SUCCESS;
import static com.nemesis.nemesis.ActivityIdentifiers.FINGERPRINT_ERROR;
import static com.nemesis.nemesis.ActivityIdentifiers.FINGERPRINT_SCAN_CODE;
import static com.nemesis.nemesis.ActivityIdentifiers.UID;

public class InvigilatorLogin extends AppCompatActivity {

    @BindView(R.id.id)
    TextInputEditText id;
    @BindView(R.id.key)
    TextInputEditText key;
    @BindView(R.id.mainLayout)
    CoordinatorLayout mainLayout;
    ApiResponseCodes arc;
    private InvigilatorDetails loginDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invigilator_login);
        ButterKnife.bind(this);
        ActivityIdentifiers.setCurrentScreen(getApplicationContext(),ActivityIdentifiers.INVIGILATOR_LOGIN_SCREEN);
        arc=new ApiResponseCodes();
        key.setText("123456");
        id.setText("34500");
    }

    @OnClick(R.id.login)
    public void onInvigilatorLogin(){
        rx.Observable.create(new rx.Observable.OnSubscribe<InvigilatorDetails>() {
         @Override
         public void call(final Subscriber<? super InvigilatorDetails> subscriber) {
             HttpRequest.ExamApiInterface examInterface=HttpRequest.retrofit.create(HttpRequest.ExamApiInterface.class);
             Call<InvigilatorDetails> responseCall=examInterface.getInvigilatorDetails(
                     RequestBody.create(MediaType.parse("text/plain"),id.getText().toString()),
                     RequestBody.create(MediaType.parse("text/plain"),key.getText().toString())
             );
             responseCall.enqueue(new Callback<InvigilatorDetails>() {
                 @Override
                 public void onResponse(Call<InvigilatorDetails> call, Response<InvigilatorDetails> response) {
                        if(response.body().getStatuscode()==200){
                            subscriber.onNext(response.body());
                        }
                        else{
                            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error : "+response.body().getStatuscode())
                                    .setContentText(arc.getResponsePhrase(response.body().getStatuscode()))
                                    .show();
                        }
                 }
                 @Override
                 public void onFailure(Call<InvigilatorDetails> call, Throwable t) {
                     new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                             .setTitleText("Something Went Wrong")
                             .setContentText("Check Your Internet Connection")
                             .show();
                 }
             });
         }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<InvigilatorDetails>() {
                    @Override
                    public void call(InvigilatorDetails invigilatorDetails) {
                        loginDetails=invigilatorDetails;
                        showSuccess(invigilatorDetails);
                    }
                });
    }

    public void showSuccess(final InvigilatorDetails invigilatorDetails){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Welcome "+invigilatorDetails.getName())
                .setContentText("Perform Biometric Authentication to Continue")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        performBiometric(invigilatorDetails.getAadhar());
                        sweetAlertDialog.cancel();
                    }
                })
                .setCancelText("Cancel")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .show();
    }

    public void performBiometric(String aadhar){
        Intent intent=new Intent(getApplicationContext(),ScanningScreen.class);
        intent.putExtra(UID,aadhar);
        startActivityForResult(intent, FINGERPRINT_SCAN_CODE);
       /* PrefUtils.newLogIn(getApplicationContext(), invigilator);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FINGERPRINT_SCAN_CODE) {
            if(resultCode == BIO_SUCCESS) {
                PrefUtils.login(getApplicationContext(), loginDetails);
                new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Biometric Auth Successful")
                        .setContentText("You are being redirected to Candidate Login.")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(getApplicationContext(), CandidateLogin.class));
                            }
                        })
                        .show();
            }else{
                new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(data.getStringExtra(AUTH_RESULT))
                        .setContentText("Try Again or Contact Admin.")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                               sweetAlertDialog.cancel();
                            }
                        })
                        .show();
            }

        }
    }

    @Override
    public void onBackPressed() {}



}
