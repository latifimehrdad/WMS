package com.example.wms.views.activitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.ActivityLoginBinding;
import com.example.wms.viewmodels.user.login.VM_ActivityLogin;


import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class LoginActivity extends AppCompatActivity {


    private VM_ActivityLogin vm_activityLogin;
    private NavController navController;
    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {//__________________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        onBindView();
    }//_____________________________________________________________________________________________ End onCreate


    private void onBindView() {//___________________________________________________________________ Start onBindView
        vm_activityLogin = new VM_ActivityLogin(this);
        ActivityLoginBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_login);
        binding.setLogin(vm_activityLogin);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }//_____________________________________________________________________________________________ End onBindView




    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext


    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed

        NavDestination navDestination = navController.getCurrentDestination();
        String fragment = navDestination.getLabel().toString();
        if (!fragment.equalsIgnoreCase("fragment_fragment_login")){
            super.onBackPressed();
            return;
        }


        if (doubleBackToExitPressedOnce) {
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج 2 بار کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }//_____________________________________________________________________________________________ End onBackPressed



}
