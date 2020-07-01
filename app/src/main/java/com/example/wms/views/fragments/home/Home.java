package com.example.wms.views.fragments.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentHomeBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.main.VM_Home;
import com.example.wms.views.custom.textview.VerticalTextView;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.user.login.Login;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_Home vm_home;
    public static boolean requestPackage = false;


    @BindView(R.id.FooterPrimary)
    RelativeLayout FooterPrimary;

    @BindView(R.id.footer)
    RelativeLayout footer;

    @BindView(R.id.footerup)
    TextView footerup;

    @BindView(R.id.footerdown)
    TextView footerdown;

    @BindView(R.id.footerright)
    VerticalTextView footerright;

    @BindView(R.id.footerleft)
    VerticalTextView footerleft;

    @BindView(R.id.youScorelayout)
    LinearLayout youScorelayout;

    @BindView(R.id.scoreLayout)
    LinearLayout scoreLayout;

    @BindView(R.id.scoreLayoutChart)
    LinearLayout scoreLayoutChart;


    public Home() {//_______________________________________________________________________________ Home

    }//_____________________________________________________________________________________________ Home

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_home, container, false);
            vm_home = new VM_Home(getContext());
            binding.setVmHome(vm_home);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Home.this,
                vm_home.getPublishSubject(),
                vm_home);
        navController = Navigation.findNavController(getView());
        CheckProfile();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        StaticFunctions.hideKeyboard(getActivity());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SetLayout();
            }
        }, 500);
        SetClick();
    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        setAccessClick(true);
        if (action == StaticValues.ML_GotoProfile) {
            navController.navigate(R.id.action_home_to_profile);
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void CheckProfile() {//_________________________________________________________________ CheckToken
        vm_home.CheckProfile();
    }//_____________________________________________________________________________________________ CheckToken



    private void SetClick() {//_____________________________________________________________________ SetClick


        footerup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_home_to_profile);
//                if (vm_home.IsPackageState()) {
//                    requestPackage = false;
//                    navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequestPrimary);
//                } else {
//                    if (vm_home.IsAddressCompleted()) {
//                        requestPackage = false;
//                        navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequestPrimary);
//                    } else {
//                        requestPackage = true;
//                        navController.navigate(R.id.action_fragmentHome_to_fragmentPackRequestAddress);
//                    }
//
//                }
            }
        });

        footerdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_fragmentHome_to_fragmentCollectRequest);
            }
        });

        footerleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

        footerright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLearn);
            }
        });


        youScorelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

        scoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

        scoreLayoutChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentHome_to_fragmentLottery);
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void SetLayout() {//____________________________________________________________________ SetLayout
        int width = FooterPrimary.getMeasuredWidth();
        int height = FooterPrimary.getMeasuredHeight();

        if (width < height)
            height = width;
        else
            width = height;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        footer.setLayoutParams(params);

        width = width / 4;
        ViewGroup.LayoutParams paramsUp = (ViewGroup.LayoutParams) footerup.getLayoutParams();
        paramsUp.height = width;
        paramsUp.width = width * 2;
        footerup.setLayoutParams(paramsUp);

        ViewGroup.LayoutParams paramsDown = (ViewGroup.LayoutParams) footerdown.getLayoutParams();
        paramsDown.height = width;
        paramsDown.width = width * 2;
        footerdown.setLayoutParams(paramsDown);

        ViewGroup.LayoutParams paramsLeft = (ViewGroup.LayoutParams) footerleft.getLayoutParams();
        paramsLeft.height = width * 2;
        paramsLeft.width = width;
        footerleft.setLayoutParams(paramsLeft);


        ViewGroup.LayoutParams paramsRight = (ViewGroup.LayoutParams) footerright.getLayoutParams();
        paramsRight.height = width * 2;
        paramsRight.width = width;
        footerright.setLayoutParams(paramsRight);
    }//_____________________________________________________________________________________________ SetLayout


}
