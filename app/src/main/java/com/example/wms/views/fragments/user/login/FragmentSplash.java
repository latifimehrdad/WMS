package com.example.wms.views.fragments.user.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wms.R;
import com.example.wms.databinding.FragmentSplashBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.login.VM_FragmentSplash;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSplash extends Fragment {

    private Context context;
    private VM_FragmentSplash vm_fragmentSplash;
    private View view;
    private DisposableObserver<Byte> observer;
    private NavController navController;

    @BindView(R.id.ImgLogo)
    ImageView ImgLogo;

    @BindView(R.id.ButtonRefresh)
    Button ButtonRefresh;

    public FragmentSplash() {//_____________________________________________________________________ FragmentSplash
        // Required empty public constructor
    }//_____________________________________________________________________________________________ FragmentSplash


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (view == null) {
            StaticFunctions.isCancel = true;
            this.context = getActivity();
            vm_fragmentSplash = new VM_FragmentSplash(context);
            FragmentSplashBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_splash, container, false
            );
            binding.setSpalsh(vm_fragmentSplash);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            SetOnclick();
        }
        return view;
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        if (observer != null)
            observer.dispose();
        observer = null;
        ObserverObservables();
        CheckToken();
    }//_____________________________________________________________________________________________ End onStart


    private void SetOnclick() {//___________________________________________________________________ SetOnclick

        ButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticFunctions.isCancel)
                    CheckToken();
            }
        });

    }//_____________________________________________________________________________________________ SetOnclick


    private void CheckToken() {//___________________________________________________________________ CheckToken

        ImgLogo.setVisibility(View.VISIBLE);
        ButtonRefresh.setVisibility(View.GONE);
        SetAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vm_fragmentSplash.CheckToken();
            }
        }, 1000);

    }//_____________________________________________________________________________________________ CheckToken


    private void SetAnimation() {//_________________________________________________________________ SetAnimation
        ImgLogo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
    }//_____________________________________________________________________________________________ SetAnimation


    private void ObserverObservables() {//__________________________________________________________ ObserverObservables

        observer = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HandleAction(s);
                    }

                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        vm_fragmentSplash
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ ObserverObservables


    private void HandleAction(Byte action) {//______________________________________________________ HandleAction
        StaticFunctions.isCancel = true;
        if (action == StaticValues.ML_GoToHome) {
            navController.navigate(R.id.action_Splash_to_Home);
        } else if (action == StaticValues.ML_GotoLogin) {
            navController.navigate(R.id.action_fragmentSplash_to_fragmentLogin);
        } else if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
            ImgLogo.setVisibility(View.GONE);
            ButtonRefresh.setVisibility(View.VISIBLE);
        } else if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_fragmentSplash.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
            ImgLogo.setVisibility(View.GONE);
            ButtonRefresh.setVisibility(View.VISIBLE);
        }
    }//_____________________________________________________________________________________________ HandleAction


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getFragmentManager());

    }//_____________________________________________________________________________________________ ShowMessage


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        if (observer != null)
            observer.dispose();
        observer = null;
        StaticFunctions.isCancel = true;
    }//_____________________________________________________________________________________________ onDestroy


    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ onStop


}
