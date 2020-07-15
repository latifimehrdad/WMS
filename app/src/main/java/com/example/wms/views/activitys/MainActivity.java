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
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wms.R;
import com.example.wms.databinding.ActivityMainBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.viewmodels.main.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {


    public static boolean complateprofile = false;
    private NavController navController;
    //    private AppBarConfiguration appBarConfiguration;
    private boolean MenuOpen = false;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean preLogin = false;

    @BindView(R.id.MainMenu)
    ImageView MainMenu;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.ExitProfile)
    LinearLayout ExitProfile;

    @BindView(R.id.ProfileName)
    TextView ProfileName;

    @BindView(R.id.nvView)
    NavigationView nvView;

    @BindView(R.id.BottomNav1)
    BottomNavigationView BottomNav1;

    @BindView(R.id.RelativeLayoutLoginHeader)
    RelativeLayout RelativeLayoutLoginHeader;

    @BindView(R.id.RelativeLayoutMainFooter)
    RelativeLayout RelativeLayoutMainFooter;

    @BindView(R.id.LinearLayoutFragment)
    LinearLayout LinearLayoutFragment;

    @BindView(R.id.TextViewNewCopyRight)
    TextView TextViewNewCopyRight;

    @BindView(R.id.LinearLayoutAbout)
    LinearLayout LinearLayoutAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        SetBindingView();
    }//_____________________________________________________________________________________________ End onCreate


    private void SetBindingView() {//_______________________________________________________________ Start SetBindingView
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(this);
        binding.setMain(mainActivityViewModel);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(mDrawer)
                .build();
        NavigationUI.setupWithNavController(BottomNav1, navController);
        NavigationUI.setupWithNavController(nvView, navController);
        SetClicks();
        //setupDrawerContent(nvView);
        checkLocationPermission();
        SetListener();
    }//_____________________________________________________________________________________________ End SetBindingView


    @SuppressLint("RtlHardcoded")
    private void SetClicks() {//____________________________________________________________________ Start

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> mDrawer.closeDrawer(Gravity.RIGHT));

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                MenuOpen = true;
                ProfileName.setText(GetUserNameProfile());
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                MenuOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        ExitProfile.setOnClickListener(v -> {
            if (StaticFunctions.LogOut(MainActivity.this)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
                MainActivity.complateprofile = false;

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    Intent mStartActivity = new Intent(MainActivity.this, MainActivity.class);
                    mStartActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int mPendingIntentId = 7126;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    if (mgr != null)
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    System.exit(0);
                }, 1000);
            }

            //ShowSpalshActivity();
        });


        MainMenu.setOnClickListener(v -> mDrawer.openDrawer(Gravity.RIGHT, true));


        TextViewNewCopyRight.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://www.ngra.ir/"));
            startActivity(intent);
        });


        LinearLayoutAbout.setOnClickListener(v -> navController.navigate(R.id.action_goto_creator));

    }//_____________________________________________________________________________________________ End


    @SuppressLint("RtlHardcoded")
    private void SetListener() {//__________________________________________________________________ Start onCreate

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            mDrawer.closeDrawer(Gravity.RIGHT);

            String fragment = "";
            if (destination.getLabel() != null)
                fragment = destination.getLabel().toString();


            if ((fragment.equalsIgnoreCase("Splash")) ||
                            (fragment.equalsIgnoreCase("Login")) ||
                            (fragment.equalsIgnoreCase("SignUp")) ||
                            (fragment.equalsIgnoreCase("VerifyCode"))) {
                if (!preLogin) {
                    preLogin = true;
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph graph = navInflater.inflate(R.navigation.nav_host);
                    graph.setStartDestination(R.id.splash);
                    navController.setGraph(graph);
                    RelativeLayoutLoginHeader.setVisibility(View.VISIBLE);
                    RelativeLayoutMainFooter.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.addRule(RelativeLayout.BELOW, R.id.RelativeLayoutLoginHeader);
                    LinearLayoutFragment.setLayoutParams(params);
                }

            } else {
                if (preLogin) {
                    preLogin = false;
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph graph = navInflater.inflate(R.navigation.nav_host);
                    graph.setStartDestination(R.id.home);
                    navController.setGraph(graph);
                    RelativeLayoutLoginHeader.setVisibility(View.GONE);
                    RelativeLayoutMainFooter.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.addRule(RelativeLayout.BELOW, R.id.MainHeader);
                    LinearLayoutFragment.setLayoutParams(params);
                }
            }

        });
    }//_____________________________________________________________________________________________ End onCreate


    private String GetUserNameProfile() {//_________________________________________________________ Start GetUserNameProfile

        SharedPreferences prefs = this.getSharedPreferences(getResources().getString(R.string.ML_SharePreferences), 0);

        if (prefs == null) {
            return "نام کاربر";
        } else {
            String name = prefs.getString(getString(R.string.ML_Name), "");
            String lastName = prefs.getString(getString(R.string.ML_lastName), "");
            if ((name.equalsIgnoreCase("")) && (lastName.equalsIgnoreCase("")))
                return "نام کاربر";
            else
                return name + " " + lastName;
        }

    }//_____________________________________________________________________________________________ End GetUserNameProfile


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
                        .setPositiveButton("تایید", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
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
                                           @NotNull String[] permissions,
                                           @NotNull int[] grantResults) {//_________________________ Start onRequestPermissionsResult
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

            }
        }
    }//_____________________________________________________________________________________________ End onRequestPermissionsResult


    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed

        if (MenuOpen) {
            mDrawer.closeDrawer(Gravity.RIGHT);
            return;
        }

        NavDestination navDestination = navController.getCurrentDestination();
        if (navDestination != null)
            if (navDestination.getLabel() != null) {
                String fragment = navDestination.getLabel().toString();
                if ((!fragment.equalsIgnoreCase("Login")) &&
                        (!fragment.equalsIgnoreCase("Home"))) {
                    super.onBackPressed();
                    return;
                }
            }


        if (doubleBackToExitPressedOnce) {
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج 2 بار کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }//_____________________________________________________________________________________________ End onBackPressed


}
