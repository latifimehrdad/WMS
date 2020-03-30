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
    private DisposableObserver<String> observer;
    private NavController navController;

    @BindView(R.id.ImgLogo)
    ImageView ImgLogo;

    @BindView(R.id.ButtonRefresh)
    Button ButtonRefresh;

    public FragmentSplash() {//_____________________________________________________________________ Start FragmentSplash
        // Required empty public constructor
    }//_____________________________________________________________________________________________ End FragmentSplash


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getContext();
        vm_fragmentSplash = new VM_FragmentSplash(context);
        FragmentSplashBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_splash,container,false
        );
        binding.setSpalsh(vm_fragmentSplash);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        ButtonRefresh.setVisibility(View.GONE);

        ButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckToken();
            }
        });

        SetAnimation();
        ObserverObservables();
        CheckToken();
    }//_____________________________________________________________________________________________ End onStart



    private void CheckToken() {//_______________________________ ___________________________________ Start CheckToken
        ImgLogo.setVisibility(View.VISIBLE);
        ButtonRefresh.setVisibility(View.GONE);
        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs == null) {
            GetTokenFromServer();
        } else {
            String access_token = prefs.getString("accesstoken", null);
            String expires = prefs.getString("expires", null);
            if ((access_token == null) || (expires == null))
                GetTokenFromServer();
            else
                ConfigHandlerForExit();
        }

    }//_____________________________________________________________________________________________ End CheckToken


    private void GetTokenFromServer() {//___________________________________________________________ Start GetTokenFromServer
        vm_fragmentSplash.GetTokenFromServer();
    }//_____________________________________________________________________________________________ End GetTokenFromServer


    private void SetAnimation() {//_________________________________________________________________ Start SetAnimation
        ImgLogo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
    }//_____________________________________________________________________________________________ End SetAnimation



    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (s) {
                            case "CancelByUser":
                                break;

                            case "Successful":
                                ConfigHandlerForExit();
                                break;

                            case "Failure":
                                ShowMessage(getResources().getString(R.string.NetworkError),
                                        getResources().getColor(R.color.mlWhite),
                                        getResources().getDrawable(R.drawable.ic_error));
                                ImgLogo.setVisibility(View.GONE);
                                ButtonRefresh.setVisibility(View.VISIBLE);
                                break;

                            case "Error":
                                ShowMessage(vm_fragmentSplash.getMessageResponcse()
                                        , getResources().getColor(R.color.mlWhite),
                                        getResources().getDrawable(R.drawable.ic_error));
                                ImgLogo.setVisibility(View.GONE);
                                ButtonRefresh.setVisibility(View.VISIBLE);
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
        };

        vm_fragmentSplash
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservables



    private void ConfigHandlerForExit() {//__________________________________________________________ Start ConfigHandlerForExit

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navController.navigate(R.id.action_fragmentSplash_to_fragmentLogin);
            }
        }, 2000);

    }//_____________________________________________________________________________________________ End ConfigHandlerForExit



    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context,message,color,icon,getFragmentManager());

    }//_____________________________________________________________________________________________ End ShowMessage



    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        observer.dispose();
    }//_____________________________________________________________________________________________ End onDestroy



}
