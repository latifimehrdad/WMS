package com.example.wms.views.fragments.user.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentSplashBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.login.VM_Splash;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_Splash vm_splash;
    private NavController navController;

    @BindView(R.id.ImgLogo)
    ImageView ImgLogo;

    @BindView(R.id.ButtonRefresh)
    Button ButtonRefresh;

    public Splash() {//_____________________________________________________________________________ Splash

    }//_____________________________________________________________________________________________ Splash


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentSplashBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_splash, container, false);
            vm_splash = new VM_Splash(getContext());
            binding.setVmSplash(vm_splash);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetOnclick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Splash.this,
                vm_splash.getPublishSubject(),
                vm_splash);
        navController = Navigation.findNavController(getView());
        CheckToken();
    }//_____________________________________________________________________________________________ onStart


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action == StaticValues.ML_GoToHome) {
            navController.navigate(R.id.action_splash_to_home);
            return;
        }

        if (action == StaticValues.ML_GotoLogin) {
            navController.navigate(R.id.action_splash_to_login);
            return;
        }

        if (action == StaticValues.ML_ResponseFailure
                || action == StaticValues.ML_ResponseError
                || action == StaticValues.ML_RequestCancel) {
            ImgLogo.setAnimation(null);
            ImgLogo.setVisibility(View.INVISIBLE);
            ButtonRefresh.setVisibility(View.VISIBLE);
            return;
        }


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void CheckToken() {//___________________________________________________________________ CheckToken

        ImgLogo.setVisibility(View.VISIBLE);
        ButtonRefresh.setVisibility(View.GONE);
        StartAnimationSplash();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vm_splash.CheckToken();
            }
        }, 1000);

    }//_____________________________________________________________________________________________ CheckToken


    private void StartAnimationSplash() {//_________________________________________________________ StartAnimationSplash
        ImgLogo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
    }//_____________________________________________________________________________________________ StartAnimationSplash


    private void SetOnclick() {//___________________________________________________________________ SetOnclick

        ButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckToken();
            }
        });

    }//_____________________________________________________________________________________________ SetOnclick


}
