/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySplashBinding;
import com.example.wms.viewmodels.main.SplashActivityViewModel;
import com.example.wms.views.activitys.user.login.ActivityBeforLogin;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

    private SplashActivityViewModel splashActivityViewModel;

    @BindView(R.id.ImgLogo)
    ImageView ImgLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
        ConfigHandlerForExit();
        SetAnimation();
    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        splashActivityViewModel = new SplashActivityViewModel(this);
        binding.setSpalsh(splashActivityViewModel);
        ButterKnife.bind(this);
    }//_____________________________________________________________________________________________ End SetBindingView


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
        }, 5000);

    }//_____________________________________________________________________________________________ End ConfigHandlerForExit


    private void ShowBeforLoginActivity() {//_______________________________________________________ Start ShowBeforLoginActivity
        Intent intent = new Intent(SplashActivity.this, ActivityBeforLogin.class);
        startActivity(intent);
    }//_____________________________________________________________________________________________ End ShowBeforLoginActivity


}
