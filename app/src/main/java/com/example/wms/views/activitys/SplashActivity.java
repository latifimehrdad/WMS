/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySplashBinding;
import com.example.wms.viewmodels.main.SplashActivityViewModel;
import com.example.wms.views.activitys.user.login.ActivityBeforLogin;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogMessage;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    private SplashActivityViewModel splashActivityViewModel;
    private DialogProgress progress;

    @BindView(R.id.ImgLogo)
    ImageView ImgLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        splashActivityViewModel = new SplashActivityViewModel(this);
        binding.setSpalsh(splashActivityViewModel);
        ButterKnife.bind(this);
        SetAnimation();
        ObserverObservables();
        CheckToken();
    }//_____________________________________________________________________________________________ End SetBindingView


    private void CheckToken() {//_______________________________ ___________________________________ Start CheckToken
        SharedPreferences prefs = this.getSharedPreferences("wmstoken", 0);
        if (prefs == null) {
            GetTokenFromServer();
        } else {
            String access_token = prefs.getString("accesstoken", null);
            String expires = prefs.getString("expires", null);
            String PhoneNumber = prefs.getString("phonenumber", null);
            if ((access_token == null) || (expires == null))
                GetTokenFromServer();
            else
                ConfigHandlerForExit();
        }

    }//_____________________________________________________________________________________________ End CheckToken


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables

        splashActivityViewModel
                .Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (s) {
                                    case "CancelByUser":
                                        if (progress != null)
                                            progress.dismiss();
                                        break;

                                    case "Successful":
                                        ConfigHandlerForExit();
                                        break;

                                    case "Failure":
                                        ShowMessage(getResources().getString(R.string.NetworkError),
                                                getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        break;

                                    case "Error":
                                        ShowMessage(splashActivityViewModel.getMessageResponcse()
                                                , getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


    private void GetTokenFromServer() {//___________________________________________________________ Start GetTokenFromServer
        //ShowProgressDialog();
        splashActivityViewModel.GetTokenFromServer();
    }//_____________________________________________________________________________________________ End GetTokenFromServer


    private void SetAnimation() {//_________________________________________________________________ Start SetAnimation
        ImgLogo.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.bounce));
    }//_____________________________________________________________________________________________ End SetAnimation


    private void ConfigHandlerForExit() {//__________________________________________________________ Start ConfigHandlerForExit

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckProfile();
                SplashActivity.this.finish();
            }
        }, 2000);

    }//_____________________________________________________________________________________________ End ConfigHandlerForExit


    private void CheckProfile() {//_________________________________________________________________ Start CheckProfile

        SharedPreferences prefs = this.getSharedPreferences("wmstoken", 0);
        if (prefs == null) {

        } else {
            String PhoneNumber = prefs.getString("phonenumber", null);
            if (PhoneNumber == null)
                ShowBeforLoginActivity();
            else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.FragmentMessage.onNext("CheckProfile");
                    }
                },100);

            }


        }

    }//_____________________________________________________________________________________________ End CheckProfile


    private void ShowBeforLoginActivity() {//_______________________________________________________ Start ShowBeforLoginActivity
        Intent intent = new Intent(SplashActivity.this, ActivityBeforLogin.class);
        startActivity(intent);
    }//_____________________________________________________________________________________________ End ShowBeforLoginActivity



    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        Context context = SplashActivity.this;
        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context,message,color,icon,getSupportFragmentManager());

    }//_____________________________________________________________________________________________ End ShowMessage


}
