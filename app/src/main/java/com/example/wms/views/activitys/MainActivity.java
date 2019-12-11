/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wms.R;
import com.example.wms.databinding.ActivityMainBinding;
import com.example.wms.viewmodels.main.MainActivityViewModel;
import com.example.wms.views.fragments.aboutus.FragmentAbout;
import com.example.wms.views.fragments.callwithus.FragmentCallWithUs;
import com.example.wms.views.fragments.collectrequest.collectrequest.FragmentCollectRequestOrders;
import com.example.wms.views.fragments.home.FragmentHome;
import com.example.wms.views.fragments.collectrequest.collectrequest.FragmentCollectRequest;
import com.example.wms.views.fragments.collectrequest.boothreceive.FragmentBoothReceive;
import com.example.wms.views.fragments.collectrequest.recyclingcar.FragmentRecyclingCar;
import com.example.wms.views.fragments.learn.FragmentLearn;
import com.example.wms.views.fragments.lottery.FragmentLottery;
import com.example.wms.views.fragments.packrequest.FragmentPackRequest;
import com.example.wms.views.fragments.profile.FragmentProfile;
import com.example.wms.views.fragments.wallet.FragmentWallet;
import com.google.android.material.navigation.NavigationView;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {


    private MainActivityViewModel mainActivityViewModel;
    public static PublishSubject<String> FragmentMessage = null;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @BindView(R.id.MainFrameLayout)
    FrameLayout MainFrameLayout;

    @BindView(R.id.MainActivityChargeWallet)
    ImageView MainActivityChargeWallet;

    @BindView(R.id.MainActivityOrder)
    ImageView MainActivityOrder;

    @BindView(R.id.MainMenu)
    ImageView MainMenu;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nvView)
    NavigationView nvDrawer;

    @BindView(R.id.LayoutEdit)
    LinearLayout LayoutEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
        //SetDrawerMenu();
        //ShowSpalshActivity();
        //ShowFragmentRegister();
        //ShowFragmentAbout();
        //ShowFragmentCall();

    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new MainActivityViewModel(this);
        binding.setMain(mainActivityViewModel);
        ButterKnife.bind(this);
        FragmentMessage = PublishSubject.create();
        FragmentShowObserver();
        SetClicks();
        setupDrawerContent(nvDrawer);
        ShowSpalshActivity();


        //itemCycleMenuWidget.setMenuItems(Collection<CycleMenuItem> items);
    }//_____________________________________________________________________________________________ End SetBindingView


    private void ShowSpalshActivity() {//___________________________________________________________ Start ShowSpalshActivity
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
    }//_____________________________________________________________________________________________ End ShowSpalshActivity


    private void SetClicks() {//____________________________________________________________________ Start

        MainActivityChargeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentWallet();
            }
        });

        MainActivityOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentOrder();
            }
        });

        MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.RIGHT,true);
            }
        });

        LayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentRegister();
                mDrawer.closeDrawer(Gravity.RIGHT);
            }
        });

    }//_____________________________________________________________________________________________ End


    private void FragmentShowObserver() {//_________________________________________________________ Start FragmentShowObserver

        FragmentMessage
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (s) {
                                    case "Main":
                                        MainActivity.this.ShowFragmentHome();
                                        break;
                                    case "PckRequest":
                                        MainActivity.this.ShowFragmentPAckRequest();
                                        break;
                                    case "Lottery":
                                        MainActivity.this.ShowFragmentLottery();
                                        break;
                                    case "CollectRequest":
                                        MainActivity.this.ShowFragmentCollectRequest();
                                        break;
                                    case "Learn":
                                        MainActivity.this.ShowFragmentLearn();
                                        break;
                                    case "BoothReceive":
                                        MainActivity.this.ShowFragmentBoothReceive();
                                        break;
                                    case "RecyclingCar":
                                        MainActivity.this.ShowFragmentRecyclingCar();
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

    }//_____________________________________________________________________________________________ End FragmentShowObserver


    private void ShowFragmentRegister() {//___________________________________________________________ Start ShowFragmentRegister
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentProfile fragmentProfile = new FragmentProfile(this);
        ft.replace(R.id.MainFrameLayout, fragmentProfile);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentRegister


    public void ShowFragmentHome() {//______________________________________________________________ Start ShowFragmentHome
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentHome fragmentHome = new FragmentHome(this);
        ft.replace(R.id.MainFrameLayout, fragmentHome);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentHome


    public void ShowFragmentPAckRequest() {//_______________________________________________________ Start ShowFragmentPAckRequest
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentPackRequest requestPrimery = new FragmentPackRequest(this);
        ft.replace(R.id.MainFrameLayout, requestPrimery);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentPAckRequest


    private void ShowFragmentCollectRequest() {//____________________________________________________ Start ShowFragmentCollectRequest
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentCollectRequest collectRequest = new FragmentCollectRequest(this);
        ft.replace(R.id.MainFrameLayout, collectRequest);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentCollectRequest


    private void ShowFragmentBoothReceive() {//______________________________________________________ Start ShowFragmentBoothReceive
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentBoothReceive boothReceive = new FragmentBoothReceive(this);
        ft.replace(R.id.MainFrameLayout, boothReceive);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentBoothReceive


    private void ShowFragmentRecyclingCar() {//______________________________________________________ Start ShowFragmentRecyclingCar
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentRecyclingCar recyclingCar = new FragmentRecyclingCar(this);
        ft.replace(R.id.MainFrameLayout, recyclingCar);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentRecyclingCar


    private void ShowFragmentLearn() {//_____________________________________________________________ Start ShowFragmentLearn
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentLearn fragmentLearn = new FragmentLearn(this);
        ft.replace(R.id.MainFrameLayout, fragmentLearn);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentLearn


    private void ShowFragmentLottery() {//___________________________________________________________ Start ShowFragmentLottery
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentLottery fragmentLottery = new FragmentLottery(this);
        ft.replace(R.id.MainFrameLayout, fragmentLottery);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentLottery


    private void ShowFragmentWallet() {//____________________________________________________________ Start ShowFragmentWallet
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentWallet fragmentWallet = new FragmentWallet(this);
        ft.replace(R.id.MainFrameLayout, fragmentWallet);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentWallet


    private void ShowFragmentAbout() {//_____________________________________________________________ Start ShowFragmentAbout
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentAbout fragmentAbout = new FragmentAbout(this);
        ft.replace(R.id.MainFrameLayout, fragmentAbout);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentAbout


    private void ShowFragmentCall() {//______________________________________________________________ Start ShowFragmentCall
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentCallWithUs callWithUs = new FragmentCallWithUs(this);
        ft.replace(R.id.MainFrameLayout, callWithUs);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentCall


    private void ShowFragmentOrder() {//____________________________________________________________ Start ShowFragmentOrder
        fm = null;
        ft = null;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        FragmentCollectRequestOrders collectRequestOrders = new FragmentCollectRequestOrders(this);
        ft.replace(R.id.MainFrameLayout, collectRequestOrders);
        ft.commit();
    }//_____________________________________________________________________________________________ End ShowFragmentOrder


    private void setupDrawerContent(NavigationView navigationView) {//______________________________ Start setupDrawerContent
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }//_____________________________________________________________________________________________ End setupDrawerContent


    public void selectDrawerItem(MenuItem menuItem) {//_____________________________________________ Start selectDrawerItem
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_request_package:
                MainActivity.this.ShowFragmentPAckRequest();
                break;
            case R.id.nav_seperation:
                MainActivity.this.ShowFragmentLearn();
                break;
            case R.id.nav_collect:
                MainActivity.this.ShowFragmentCollectRequest();
                break;
            case R.id.nav_clottery:
                MainActivity.this.ShowFragmentLottery();
                break;
            case R.id.nav_support:
                MainActivity.this.ShowFragmentAbout();
                break;
            case R.id.nav_call:
                MainActivity.this.ShowFragmentCall();
                break;
            case R.id.nav_home:
                MainActivity.this.ShowFragmentHome();
                break;
        }

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }//_____________________________________________________________________________________________ End selectDrawerItem


    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext


}
