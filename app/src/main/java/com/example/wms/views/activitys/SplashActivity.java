/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySplashBinding;
import com.example.wms.viewmodels.SplashActivityViewModel;


public class SplashActivity extends AppCompatActivity {

    private SplashActivityViewModel splashActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
        ConfigHandlerForExit();

    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        splashActivityViewModel = new SplashActivityViewModel(this);
        binding.setSpalsh(splashActivityViewModel);
    }//_____________________________________________________________________________________________ End SetBindingView



    private void ConfigHandlerForExit(){//__________________________________________________________ Start ConfigHandlerForExit

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.finish();
            }
        },5000);

    }//_____________________________________________________________________________________________ End ConfigHandlerForExit



}
