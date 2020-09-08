/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.ngra.wms.views.activitys;

import androidx.annotation.NonNull;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ngra.wms.R;
import com.ngra.wms.databinding.ActivityMainBinding;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.viewmodels.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {


    public static DrawerLayout drawer;
    public static boolean completeProfile = false;
    private NavController navController;
    private boolean MenuOpen = false;
    private boolean doubleBackToExitPressedOnce = false;
    public int MY_PERMISSIONS_REQUEST_LOCATION = 99;
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

    @BindView(R.id.LinearLayoutCopyRight)
    LinearLayout LinearLayoutCopyRight;

    @BindView(R.id.LinearLayoutAbout)
    LinearLayout LinearLayoutAbout;

    @BindView(R.id.ImageViewCopyRight)
    ImageView ImageViewCopyRight;

    @BindView(R.id.textViewVersion)
    TextView textViewVersion;


    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBindingView();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ setBindingView
    private void setBindingView() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(this);
        binding.setMain(mainActivityViewModel);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(mDrawer)
                .build();
        drawer = mDrawer;
        NavigationUI.setupWithNavController(BottomNav1, navController);
        NavigationUI.setupWithNavController(nvView, navController);
        setVersion();
        setClicks();
        setPermission();
        setListener();
        startAnimationSplash();

    }
    //______________________________________________________________________________________________ setBindingView


    //______________________________________________________________________________________________ setVersion
    private void setVersion() {
        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            textViewVersion.setText(getString(R.string.Version) + " : " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
    }
    //______________________________________________________________________________________________ setVersion


    //______________________________________________________________________________________________ startAnimationSplash
    private void startAnimationSplash() {
        ImageViewCopyRight.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
    }
    //______________________________________________________________________________________________ startAnimationSplash


    //______________________________________________________________________________________________ setPermission
    public void setPermission() {

        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionInstall = ContextCompat.checkSelfPermission(this, Manifest.permission.REQUEST_INSTALL_PACKAGES);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionRead != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionInstall != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);


        if (permissionWrite != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    0);
        }

    }
    //______________________________________________________________________________________________ setPermission


    //______________________________________________________________________________________________ setClicks
    @SuppressLint("RtlHardcoded")
    private void setClicks() {

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> mDrawer.closeDrawer(Gravity.RIGHT));

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                MenuOpen = true;
                ProfileName.setText(getUserNameProfile());
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
                MainActivity.completeProfile = false;

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


        LinearLayoutCopyRight.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://www.ngra.ir/"));
            startActivity(intent);
        });


        LinearLayoutAbout.setOnClickListener(v -> navController.navigate(R.id.action_goto_creator));

    }
    //______________________________________________________________________________________________ setClicks


    //______________________________________________________________________________________________ setListener
    @SuppressLint("RtlHardcoded")
    private void setListener() {

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            mDrawer.closeDrawer(Gravity.RIGHT);

            String fragment = "";
            if (destination.getLabel() != null)
                fragment = destination.getLabel().toString();


            if ((fragment.equalsIgnoreCase("Splash")) ||
                    (fragment.equalsIgnoreCase("Login")) ||
                    (fragment.equalsIgnoreCase("SignUp")) ||
                    (fragment.equalsIgnoreCase("VerifyCode")) ||
                    (fragment.equalsIgnoreCase("AppUpdate"))) {
                if (!preLogin) {
                    preLogin = true;
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph graph = navInflater.inflate(R.navigation.nav_host);
                    graph.setStartDestination(R.id.splash);
                    navController.setGraph(graph);
                    lockDrawer();
                    MainMenu.setVisibility(View.GONE);
                }
                RelativeLayoutLoginHeader.setVisibility(View.VISIBLE);
                RelativeLayoutMainFooter.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.BELOW, R.id.RelativeLayoutLoginHeader);
                LinearLayoutFragment.setLayoutParams(params);

            } else {
                if (preLogin) {
                    preLogin = false;
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph graph = navInflater.inflate(R.navigation.nav_host);
                    graph.setStartDestination(R.id.home);
                    navController.setGraph(graph);
                    MainMenu.setVisibility(View.VISIBLE);
                    unLockDrawer();
                }
                RelativeLayoutLoginHeader.setVisibility(View.GONE);
                RelativeLayoutMainFooter.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.BELOW, R.id.MainHeader);
                LinearLayoutFragment.setLayoutParams(params);
            }

        });
    }
    //______________________________________________________________________________________________ setListener


    //______________________________________________________________________________________________ getUserNameProfile
    private String getUserNameProfile() {

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

    }
    //______________________________________________________________________________________________ getUserNameProfile


    //______________________________________________________________________________________________ lockDrawer
    public static void lockDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    //______________________________________________________________________________________________ lockDrawer


    //______________________________________________________________________________________________ unLockDrawer
    public static void unLockDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    //______________________________________________________________________________________________ unLockDrawer


    //______________________________________________________________________________________________ attachBaseContext
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    //______________________________________________________________________________________________ attachBaseContext


    //______________________________________________________________________________________________ onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions,
                                           @NotNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

            }
        }
    }
    //______________________________________________________________________________________________ onRequestPermissionsResult


    //______________________________________________________________________________________________ onBackPressed
    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {

        if (MenuOpen) {
            mDrawer.closeDrawer(Gravity.RIGHT);
            return;
        }

        NavDestination navDestination = navController.getCurrentDestination();
        if (navDestination != null)
            if (navDestination.getLabel() != null) {
                String fragment = navDestination.getLabel().toString();
                if ((!fragment.equalsIgnoreCase("Home"))) {
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

    }
    //______________________________________________________________________________________________ onBackPressed


}
