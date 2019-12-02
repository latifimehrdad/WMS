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
import android.widget.FrameLayout;

import com.example.wms.R;
import com.example.wms.databinding.ActivityMainBinding;
import com.example.wms.viewmodels.MainActivityViewModel;
import com.example.wms.views.fragments.FragmentHome;
import com.example.wms.views.fragments.packrequest.FragmentPackRequest;
import com.example.wms.views.fragments.register.FragmentRegister;

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
        //ShowFragmentPAckRequest();
        //ShowSpalshActivity();
        //ShowFragmentRegister();
        //ShowFragmentHome();

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




    private void ShowFragmentHome(){//______________________________________________________________ Start ShowFragmentHome
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentHome fragmentHome = new FragmentHome(this);
        ft.replace(R.id.MainFrameLayout, fragmentHome);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentHome




    private void ShowFragmentPAckRequest(){//_______________________________________________________ Start ShowFragmentPAckRequest
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentPackRequest requestPrimery = new FragmentPackRequest(this);
        ft.replace(R.id.MainFrameLayout, requestPrimery);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentPAckRequest




    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext



}
