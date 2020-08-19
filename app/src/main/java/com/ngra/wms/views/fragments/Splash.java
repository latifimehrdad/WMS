package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentSplashBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Splash;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Splash extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_Splash vm_splash;
    private NavController navController;

    @BindView(R.id.imageViewLogo)
    ImageView imageViewLogo;

    @BindView(R.id.buttonRefresh)
    Button buttonRefresh;


    //______________________________________________________________________________________________ Splash
    public Splash() {
    }
    //______________________________________________________________________________________________ Splash


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentSplashBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_splash, container, false);
            vm_splash = new VM_Splash(getContext());
            binding.setVmSplash(vm_splash);
            setView(binding.getRoot());
            setOnclick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Splash.this,
                vm_splash.getPublishSubject(),
                vm_splash);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        checkToken();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(StaticValues.ML_GoToHome)) {
            navController.navigate(R.id.action_splash_to_home);
            return;
        }

        if (action.equals(StaticValues.ML_GoToUpdate)) {
            if (getContext() != null) {
                Bundle bundle = new Bundle();
                bundle.putString(getContext().getResources().getString(R.string.ML_UpdateUrl), vm_splash.getMd_hi().getApplicationUrl());
                bundle.putString(getContext().getResources().getString(R.string.ML_UpdateFile), vm_splash.getMd_hi().getFileName());
                navController.navigate(R.id.action_splash_to_appUpdate, bundle);
            }
            return;
        }

        if (action.equals(StaticValues.ML_GotoLogin)) {
            navController.navigate(R.id.action_splash_to_login);
            return;
        }

        if (action.equals(StaticValues.ML_ResponseFailure)
                || action.equals(StaticValues.ML_ResponseError)
                || action.equals(StaticValues.ML_RequestCancel)) {
            imageViewLogo.setAnimation(null);
            imageViewLogo.setVisibility(View.INVISIBLE);
            buttonRefresh.setVisibility(View.VISIBLE);
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ checkToken
    private void checkToken() {
        imageViewLogo.setVisibility(View.VISIBLE);
        buttonRefresh.setVisibility(View.GONE);
        startAnimationSplash();
        vm_splash.callHI();
    }
    //______________________________________________________________________________________________ checkToken


    //______________________________________________________________________________________________ startAnimationSplash
    private void startAnimationSplash() {
        imageViewLogo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
    }
    //______________________________________________________________________________________________ startAnimationSplash


    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {
        buttonRefresh.setOnClickListener(v -> checkToken());
    }
    //______________________________________________________________________________________________ setOnclick


}
