/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySplashBinding;
import com.example.wms.models.TokenModel;
import com.example.wms.viewmodels.main.SplashActivityViewModel;
import com.example.wms.views.activitys.user.login.ActivityBeforLogin;
import com.example.wms.views.activitys.user.register.ActivitySendPhoneNumber;
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
            if((access_token == null) || (expires == null))
                GetTokenFromServer();
            else
                ConfigHandlerForExit();
        }

    }//_____________________________________________________________________________________________ End CheckToken


    private void SaveToken() {//____________________________________________________________________ Start SaveToken

        TokenModel tokenModel = splashActivityViewModel.getTokenModel();

        SharedPreferences.Editor token =
                this
                        .getApplicationContext()
                        .getSharedPreferences("wmstoken", 0)
                        .edit();

        token.putString("accesstoken",tokenModel.getAccess_token());
        token.putString("tokentype",tokenModel.getToken_type());
        token.putInt("expiresin",tokenModel.getExpires_in());
        token.putString("clientid",tokenModel.getClient_id());
        token.putString("issued",tokenModel.getIssued());
        token.putString("expires",tokenModel.getExpires());
        token.apply();
        ConfigHandlerForExit();

    }//_____________________________________________________________________________________________ End SaveToken


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
                                        SaveToken();
                                        break;

                                    case "Failure":

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
                ShowBeforLoginActivity();
                SplashActivity.this.finish();
            }
        }, 2000);

    }//_____________________________________________________________________________________________ End ConfigHandlerForExit


    private void ShowBeforLoginActivity() {//_______________________________________________________ Start ShowBeforLoginActivity
        Intent intent = new Intent(SplashActivity.this, ActivityBeforLogin.class);
        startActivity(intent);
    }//_____________________________________________________________________________________________ End ShowBeforLoginActivity


    private void ShowProgressDialog() {//___________________________________________________________ Start ShowProgressDialog
        progress = new DialogProgress(SplashActivity.this,
                null, splashActivityViewModel);

        progress.setCancelable(false);
        progress.show(getSupportFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog


}
