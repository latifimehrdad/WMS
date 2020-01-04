/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {


    private MainActivityViewModel mainActivityViewModel;
    public static boolean complateprofile = false;
    private NavController navController;

    @BindView(R.id.MainActivityChargeWallet)
    ImageView MainActivityChargeWallet;

    @BindView(R.id.MainActivityOrder)
    ImageView MainActivityOrder;

    @BindView(R.id.footerLogo)
    ImageView footerLogo;

    @BindView(R.id.textOrder)
    TextView textOrder;

    @BindView(R.id.textWallet)
    TextView textWallet;

    @BindView(R.id.MainMenu)
    ImageView MainMenu;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nvView)
    NavigationView nvDrawer;

    @BindView(R.id.LayoutEdit)
    LinearLayout LayoutEdit;

    @BindView(R.id.ExitProfile)
    LinearLayout ExitProfile;

    @BindView(R.id.ProfileName)
    TextView ProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new MainActivityViewModel(this);
        binding.setMain(mainActivityViewModel);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        SetClicks();
        setupDrawerContent(nvDrawer);
        checkLocationPermission();
    }//_____________________________________________________________________________________________ End SetBindingView



    private void SetClicks() {//____________________________________________________________________ Start

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                ProfileName.setText(GetUserNameProfile());
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        ExitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor token =
                        MainActivity.this.getSharedPreferences("wmstoken", 0).edit();

                token.putBoolean("complateprofile", false);
                token.putString("accesstoken", null);
                token.putString("tokentype", null);
                token.putInt("expiresin", 0);
                token.putString("phonenumber", null);
                token.putString("clientid", null);
                token.putString("issued", null);
                token.putString("expires", null);
                token.apply();
                mDrawer.closeDrawer(Gravity.RIGHT);
                MainActivity.complateprofile = false;
                //ShowSpalshActivity();
            }
        });

        MainActivityChargeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (complateprofile)
                //ShowFragmentWallet();
            }
        });

        MainActivityOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (complateprofile)
                //ShowFragmentOrder();
            }
        });

        MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.RIGHT, true);
            }
        });

        LayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShowFragmentRegister();
                mDrawer.closeDrawer(Gravity.RIGHT);
            }
        });

        footerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (complateprofile)
                //ShowFragmentHome();
            }
        });

        textOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (complateprofile)
                //ShowFragmentOrder();
            }
        });

        textWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (complateprofile)
                //ShowFragmentWallet();
            }
        });

    }//_____________________________________________________________________________________________ End


    private String GetUserNameProfile() {//_________________________________________________________ Start GetUserNameProfile

        SharedPreferences prefs = this.getSharedPreferences("wmstoken", 0);

        if (prefs == null) {
            return "نام کاربر";
        } else {
            String name = prefs.getString("name", "");
            String lastName = prefs.getString("lastName", "");
            if ((name.equalsIgnoreCase("")) && (lastName.equalsIgnoreCase("")))
                return "نام کاربر";
            else
                return name + " " + lastName;
        }

    }//_____________________________________________________________________________________________ End GetUserNameProfile


    private void FragmentShowObserver() {//_________________________________________________________ Start FragmentShowObserver

//        FragmentMessage
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                switch (s) {
//                                    case "CheckProfile":
//                                        MainActivity.this.CheckProfile();
//                                        break;
//                                    case "Main":
//                                        MainActivity.this.ShowFragmentHome();
//                                        break;
//                                    case "PckRequest":
//                                        MainActivity.this.ShowFragmentPAckRequest();
//                                        break;
//                                    case "Lottery":
//                                        MainActivity.this.ShowFragmentLottery();
//                                        break;
//                                    case "CollectRequest":
//                                        MainActivity.this.ShowFragmentCollectRequest();
//                                        break;
//                                    case "Learn":
//                                        MainActivity.this.ShowFragmentLearn();
//                                        break;
//                                    case "BoothReceive":
//                                        MainActivity.this.ShowFragmentBoothReceive();
//                                        break;
//                                    case "RecyclingCar":
//                                        MainActivity.this.ShowFragmentRecyclingCar();
//                                        break;
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }//_____________________________________________________________________________________________ End FragmentShowObserver


//    private void ShowFragmentRegister() {//___________________________________________________________ Start ShowFragmentRegister
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentProfile fragmentProfile = new FragmentProfile(this);
//        ft.replace(R.id.MainFrameLayout, fragmentProfile);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentRegister
//
//
//    public void ShowFragmentHome() {//______________________________________________________________ Start ShowFragmentHome
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentHome fragmentHome = new FragmentHome(this);
//        ft.replace(R.id.MainFrameLayout, fragmentHome);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentHome
//
//
//    public void ShowFragmentPAckRequest() {//_______________________________________________________ Start ShowFragmentPAckRequest
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentPackRequest requestPrimery = new FragmentPackRequest(this);
//        ft.replace(R.id.MainFrameLayout, requestPrimery);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentPAckRequest
//
//
//    private void ShowFragmentCollectRequest() {//____________________________________________________ Start ShowFragmentCollectRequest
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentCollectRequest collectRequest = new FragmentCollectRequest(this);
//        ft.replace(R.id.MainFrameLayout, collectRequest);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentCollectRequest
//
//
//    private void ShowFragmentBoothReceive() {//______________________________________________________ Start ShowFragmentBoothReceive
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentBoothReceive boothReceive = new FragmentBoothReceive(this);
//        ft.replace(R.id.MainFrameLayout, boothReceive);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentBoothReceive
//
//
//    private void ShowFragmentRecyclingCar() {//______________________________________________________ Start ShowFragmentRecyclingCar
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentRecyclingCar recyclingCar = new FragmentRecyclingCar(this);
//        ft.replace(R.id.MainFrameLayout, recyclingCar);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentRecyclingCar
//
//
//    private void ShowFragmentLearn() {//_____________________________________________________________ Start ShowFragmentLearn
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentLearn fragmentLearn = new FragmentLearn(this);
//        ft.replace(R.id.MainFrameLayout, fragmentLearn);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentLearn
//
//
//    private void ShowFragmentLottery() {//___________________________________________________________ Start ShowFragmentLottery
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentLottery fragmentLottery = new FragmentLottery(this);
//        ft.replace(R.id.MainFrameLayout, fragmentLottery);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentLottery
//
//
//    private void ShowFragmentWallet() {//____________________________________________________________ Start ShowFragmentWallet
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentWallet fragmentWallet = new FragmentWallet(this);
//        ft.replace(R.id.MainFrameLayout, fragmentWallet);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentWallet
//
//
//    private void ShowFragmentAbout() {//_____________________________________________________________ Start ShowFragmentAbout
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentAbout fragmentAbout = new FragmentAbout(this);
//        ft.replace(R.id.MainFrameLayout, fragmentAbout);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentAbout
//
//
//    private void ShowFragmentCall() {//______________________________________________________________ Start ShowFragmentCall
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentCallWithUs callWithUs = new FragmentCallWithUs(this);
//        ft.replace(R.id.MainFrameLayout, callWithUs);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentCall
//
//
//    private void ShowFragmentOrder() {//____________________________________________________________ Start ShowFragmentOrder
//        fm = null;
//        ft = null;
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        FragmentCollectRequestOrders collectRequestOrders = new FragmentCollectRequestOrders(this);
//        ft.replace(R.id.MainFrameLayout, collectRequestOrders);
//        ft.commit();
//    }//_____________________________________________________________________________________________ End ShowFragmentOrder


    private void setupDrawerContent(NavigationView navigationView) {//______________________________ Start setupDrawerContent
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }//_____________________________________________________________________________________________ End setupDrawerContent


//    public void selectDrawerItem(MenuItem menuItem) {//_____________________________________________ Start selectDrawerItem
//        // Create a new fragment and specify the fragment to show based on nav item clicked
//        if (complateprofile)
//            switch (menuItem.getItemId()) {
//                case R.id.nav_request_package:
//                    MainActivity.this.ShowFragmentPAckRequest();
//                    break;
//                case R.id.nav_seperation:
//                    MainActivity.this.ShowFragmentLearn();
//                    break;
//                case R.id.nav_collect:
//                    MainActivity.this.ShowFragmentCollectRequest();
//                    break;
//                case R.id.nav_clottery:
//                    MainActivity.this.ShowFragmentLottery();
//                    break;
//                case R.id.nav_about:
//                    MainActivity.this.ShowFragmentAbout();
//                    break;
//                case R.id.nav_support:
//                    MainActivity.this.ShowFragmentCall();
//                    break;
//                case R.id.nav_home:
//                    MainActivity.this.ShowFragmentHome();
//                    break;
//            }
//
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(false);
//        // Set action bar title
//        setTitle(menuItem.getTitle());
//        // Close the navigation drawer
//        mDrawer.closeDrawers();
//    }//_____________________________________________________________________________________________ End selectDrawerItem


    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    public void checkLocationPermission() {//_____________________________________________________________________________________________ Start checkLocationPermission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("دسترسی به موقعیت")
                        .setMessage("برای نمایش مکان شما به موقعیت دسترسی بدهید")
                        .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }//_____________________________________________________________________________________________ End checkLocationPermission


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {//_____________________________________________________________________________________________ Start onRequestPermissionsResult
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }//_____________________________________________________________________________________________ End onRequestPermissionsResult


}
