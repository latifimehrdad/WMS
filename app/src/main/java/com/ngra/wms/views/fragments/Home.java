package com.ngra.wms.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentHomeBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Home;
import com.ngra.wms.views.application.ApplicationWMS;


import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Home extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private NavController navController;
    private VM_Home vm_home;
    public static boolean TwoBackToHome = false;
    public static String downloadAppLink;


    @BindView(R.id.RelativeLayoutFooterPrimary)
    RelativeLayout RelativeLayoutFooterPrimary;

    @BindView(R.id.RelativeLayoutFooter)
    RelativeLayout RelativeLayoutFooter;

    @BindView(R.id.TextViewFooterUp)
    TextView TextViewFooterUp;

    @BindView(R.id.TextViewFooterDown)
    TextView TextViewFooterDown;

    @BindView(R.id.VerticalTextViewFooterRight)
    TextView VerticalTextViewFooterRight;

    @BindView(R.id.VerticalTextViewFooterLeft)
    TextView VerticalTextViewFooterLeft;

    @BindView(R.id.LinearLayoutUserScore)
    LinearLayout LinearLayoutUserScore;

    @BindView(R.id.LinearLayoutScore)
    LinearLayout LinearLayoutScore;

    @BindView(R.id.LinearLayoutScoreChart)
    LinearLayout LinearLayoutScoreChart;

    @BindView(R.id.LinearLayoutShareApp)
    LinearLayout LinearLayoutShareApp;


    //______________________________________________________________________________________________ Home
    public Home() {

    }
    //______________________________________________________________________________________________ Home


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_home, container, false);
            vm_home = new VM_Home(getContext());
            binding.setHome(vm_home);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Home.this,
                vm_home.getPublishSubject(),
                vm_home);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        checkProfile();
        vm_home.getScoreReport();

        if (ChooseWaste.wasteLists != null)
            if (ChooseWaste.wasteLists.size() > 0) {
                ChooseWaste.wasteLists.clear();
                ChooseWaste.wasteLists = null;
                navController.navigate(R.id.action_home_to_collectRequestOrder);
            }

        VerticalTextViewFooterLeft.setVisibility(View.INVISIBLE);
        VerticalTextViewFooterRight.setVisibility(View.INVISIBLE);
        RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        rotate.setFillAfter(true);
        VerticalTextViewFooterLeft.setAnimation(rotate);
        VerticalTextViewFooterRight.setAnimation(rotate);
        VerticalTextViewFooterLeft.setVisibility(View.VISIBLE);
        VerticalTextViewFooterRight.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        hideKeyboard();
        Handler handler = new Handler();
        handler.postDelayed(this::setLayout, 500);
        setClick();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(StaticValues.ML_GotoProfile)) {
            navController.navigate(R.id.action_home_to_profile);
            return;
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ checkProfile
    private void checkProfile() {
        vm_home.checkProfile();
    }
    //______________________________________________________________________________________________ checkProfile


    //______________________________________________________________________________________________ setClick
    private void setClick() {


        LinearLayoutShareApp.setOnClickListener(v -> {

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            StringBuilder builder = new StringBuilder();
            builder.append(getString(R.string.IntroduceToFriendsLink));
            builder.append(System.getProperty("line.separator"));
            builder.append(downloadAppLink);
            builder.append(System.getProperty("line.separator"));
            builder.append(getString(R.string.ReagentCode));
            builder.append(System.getProperty("line.separator"));
            builder.append(vm_home.getPhoneNumber());
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(android.content.Intent.EXTRA_TEXT, builder.toString());
            startActivity(Intent.createChooser(intent, getString(R.string.app_name)));

        });


        TextViewFooterUp.setOnClickListener(v -> {

            if (vm_home.getPackageState() != StaticValues.PR_NotRequested) {
                TwoBackToHome = false;
                navController.navigate(R.id.action_home_to_packageRequestPrimary);
            } else {
                if (vm_home.isAddressCompleted()) {
                    TwoBackToHome = false;
                    navController.navigate(R.id.action_home_to_packageRequestPrimary);
                } else {
                    TwoBackToHome = true;
                    if (getContext() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString(getContext().getResources().getString(R.string.ML_Type), getContext().getResources().getString(R.string.ML_Save));
                        bundle.putInt(getResources().getString(R.string.ML_Id), 0);
                        navController.navigate(R.id.action_home_to_packageRequestAddress, bundle);
                    }
                }

            }
        });

        TextViewFooterDown.setOnClickListener(v -> {
            if (vm_home.getPackageState() == StaticValues.PR_Delivered)
                navController.navigate(R.id.action_home_to_chooseWaste);
//            navController.navigate(R.id.action_home_to_collectRequestPrimary);
            else {
                if (getContext() != null)
                    showMessageDialog(getContext().getResources().getString(R.string.PackageNotDelivered),
                            getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error),
                            getResources().getColor(R.color.mlCollectRight1));
            }
        });

        VerticalTextViewFooterLeft.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

        VerticalTextViewFooterRight.setOnClickListener(v -> navController.navigate(R.id.action_home_to_learn));

        LinearLayoutUserScore.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

        LinearLayoutScore.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

        LinearLayoutScoreChart.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ setLayout
    private void setLayout() {
        int lWidth = RelativeLayoutFooterPrimary.getMeasuredWidth();
        int lHeight = RelativeLayoutFooterPrimary.getMeasuredHeight();

        if (lWidth < lHeight)
            lHeight = lWidth;
        else
            lWidth = lHeight;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(lWidth, lHeight);
        RelativeLayoutFooter.setLayoutParams(params);

        lWidth = lWidth / 4;
        ViewGroup.LayoutParams paramsUp = TextViewFooterUp.getLayoutParams();
        paramsUp.height = lWidth;
        paramsUp.width = lWidth * 2;
        TextViewFooterUp.setLayoutParams(paramsUp);

        ViewGroup.LayoutParams paramsDown = TextViewFooterDown.getLayoutParams();
        paramsDown.height = lWidth;
        paramsDown.width = lWidth * 2;
        TextViewFooterDown.setLayoutParams(paramsDown);

        ViewGroup.LayoutParams paramsLeft = VerticalTextViewFooterLeft.getLayoutParams();
        paramsLeft.height = lWidth * 2;
        paramsLeft.width = lWidth;
        VerticalTextViewFooterLeft.setLayoutParams(paramsLeft);


        ViewGroup.LayoutParams paramsRight = VerticalTextViewFooterRight.getLayoutParams();
        paramsRight.height = lWidth * 2;
        paramsRight.width = lWidth;
        VerticalTextViewFooterRight.setLayoutParams(paramsRight);


        VerticalTextViewFooterLeft.setVisibility(View.INVISIBLE);
        VerticalTextViewFooterRight.setVisibility(View.INVISIBLE);
        RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        rotate.setFillAfter(true);
        VerticalTextViewFooterLeft.setAnimation(rotate);
        VerticalTextViewFooterRight.setAnimation(rotate);
        VerticalTextViewFooterLeft.setVisibility(View.VISIBLE);
        VerticalTextViewFooterRight.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ setLayout




    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}
