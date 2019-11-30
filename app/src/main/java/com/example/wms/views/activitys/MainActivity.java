/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.example.wms.R;
import com.example.wms.databinding.ActivityMainBinding;
import com.example.wms.viewmodels.MainActivityViewModel;
import com.example.wms.views.fragments.FragmentRegister;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {


    private MainActivityViewModel mainActivityViewModel;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @BindView(R.id.MainFrameLayout)
    FrameLayout MainFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
        //ShowSpalshActivity();
        ShowFragmentRegister();

    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new MainActivityViewModel(this);
        binding.setMain(mainActivityViewModel);
        ButterKnife.bind(this);

    }//_____________________________________________________________________________________________ End SetBindingView


    private void ShowSpalshActivity() {//___________________________________________________________ Start ShowSpalshActivity
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
    }//_____________________________________________________________________________________________ End ShowSpalshActivity



    private void ShowFragmentRegister(){//___________________________________________________________ Start ShowFragmentRegister
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentRegister fragmentRegister = new FragmentRegister(this);
        ft.replace(R.id.MainFrameLayout, fragmentRegister);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentRegister




    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext



}
