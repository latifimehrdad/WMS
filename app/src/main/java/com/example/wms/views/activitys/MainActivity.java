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
import com.example.wms.viewmodels.main.MainActivityViewModel;
import com.example.wms.views.fragments.aboutus.FragmentAbout;
import com.example.wms.views.fragments.callwithus.FragmentCallWithUs;
import com.example.wms.views.fragments.home.FragmentHome;
import com.example.wms.views.fragments.collectrequest.collectrequest.FragmentCollectRequest;
import com.example.wms.views.fragments.collectrequest.boothreceive.FragmentBoothReceive;
import com.example.wms.views.fragments.collectrequest.recyclingcar.FragmentRecyclingCar;
import com.example.wms.views.fragments.learn.FragmentLearn;
import com.example.wms.views.fragments.lottery.FragmentLottery;
import com.example.wms.views.fragments.packrequest.FragmentPackRequest;
import com.example.wms.views.fragments.register.FragmentRegister;
import com.example.wms.views.fragments.wallet.FragmentWallet;

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
        //ShowFragmentCollectRequest();
        //ShowFragmentBoothReceive();
        //ShowFragmentRecyclingCar();
        //ShowFragmentLearn();
        //ShowFragmentLottery();
        //ShowFragmentWallet();
        //ShowFragmentAbout();
        ShowFragmentCall();

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



    private void ShowFragmentCollectRequest(){//____________________________________________________ Start ShowFragmentCollectRequest
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentCollectRequest collectRequest = new FragmentCollectRequest(this);
        ft.replace(R.id.MainFrameLayout, collectRequest);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentCollectRequest



    private void ShowFragmentBoothReceive(){//______________________________________________________ Start ShowFragmentBoothReceive
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentBoothReceive boothReceive = new FragmentBoothReceive(this);
        ft.replace(R.id.MainFrameLayout, boothReceive);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentBoothReceive



    private void ShowFragmentRecyclingCar(){//______________________________________________________ Start ShowFragmentRecyclingCar
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentRecyclingCar recyclingCar = new FragmentRecyclingCar(this);
        ft.replace(R.id.MainFrameLayout, recyclingCar);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentRecyclingCar



    private void ShowFragmentLearn(){//_____________________________________________________________ Start ShowFragmentLearn
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentLearn fragmentLearn = new FragmentLearn(this);
        ft.replace(R.id.MainFrameLayout, fragmentLearn);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentLearn



    private void ShowFragmentLottery(){//___________________________________________________________ Start ShowFragmentLottery
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentLottery fragmentLottery = new FragmentLottery(this);
        ft.replace(R.id.MainFrameLayout, fragmentLottery);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentLottery




    private void ShowFragmentWallet(){//____________________________________________________________ Start ShowFragmentWallet
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentWallet fragmentWallet = new FragmentWallet(this);
        ft.replace(R.id.MainFrameLayout, fragmentWallet);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentWallet


    private void ShowFragmentAbout(){//_____________________________________________________________ Start ShowFragmentAbout
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentAbout fragmentAbout = new FragmentAbout(this);
        ft.replace(R.id.MainFrameLayout, fragmentAbout);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentAbout



    private void ShowFragmentCall(){//______________________________________________________________ Start ShowFragmentCall
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentCallWithUs callWithUs = new FragmentCallWithUs(this);
        ft.replace(R.id.MainFrameLayout, callWithUs);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentCall




    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext



}
