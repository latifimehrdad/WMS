package com.ngra.wms.views.fragments.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ngra.wms.viewmodels.main.VM_Home;
import com.ngra.wms.views.custom.textview.VerticalTextView;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ngra.wms.views.fragments.collectrequest.ChooseWaste;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Home extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_Home vm_home;
    public static boolean TwoBackToHome = false;


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
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_home, container, false);
            vm_home = new VM_Home(getContext());
            binding.setVmHome(vm_home);
            setView(binding.getRoot());
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
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        CheckProfile();

        if (ChooseWaste.wasteLists != null)
            if (ChooseWaste.wasteLists.size() > 0) {
                ChooseWaste.wasteLists.clear();
                ChooseWaste.wasteLists = null;
                navController.navigate(R.id.action_home_to_collectRequestOrder);
            }
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        hideKeyboard();
        Handler handler = new Handler();
        handler.postDelayed(this::SetLayout, 500);
        SetClick();
    }//_____________________________________________________________________________________________ init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GotoProfile)) {
            navController.navigate(R.id.action_home_to_profile);
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void CheckProfile() {//_________________________________________________________________ CheckToken
        vm_home.CheckProfile();
    }//_____________________________________________________________________________________________ CheckToken


    private void SetClick() {//_____________________________________________________________________ SetClick


        footerup.setOnClickListener(v -> {

            if (vm_home.GetPackageState() != StaticValues.PR_NotRequested) {
                TwoBackToHome = false;
                navController.navigate(R.id.action_home_to_packageRequestPrimary);
            } else {
                if (vm_home.IsAddressCompleted()) {
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

        footerdown.setOnClickListener(v -> {
            if (vm_home.GetPackageState() == StaticValues.PR_Delivered)
                navController.navigate(R.id.action_home_to_chooseWaste);
//            navController.navigate(R.id.action_home_to_collectRequestPrimary);
            else {
                if (getContext() != null)
                    ShowMessage(getContext().getResources().getString(R.string.PackageNotDelivered),
                            getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error),
                            getResources().getColor(R.color.mlCollectRight1));
            }
        });

        footerleft.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

        footerright.setOnClickListener(v -> navController.navigate(R.id.action_home_to_learn));

        youScorelayout.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

        scoreLayout.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

        scoreLayoutChart.setOnClickListener(v -> navController.navigate(R.id.action_home_to_lottery));

    }//_____________________________________________________________________________________________ SetClick


    private void SetLayout() {//____________________________________________________________________ SetLayout
        int lWidth = FooterPrimary.getMeasuredWidth();
        int lHeight = FooterPrimary.getMeasuredHeight();

        if (lWidth < lHeight)
            lHeight = lWidth;
        else
            lWidth = lHeight;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(lWidth, lHeight);
        footer.setLayoutParams(params);

        lWidth = lWidth / 4;
        ViewGroup.LayoutParams paramsUp = footerup.getLayoutParams();
        paramsUp.height = lWidth;
        paramsUp.width = lWidth * 2;
        footerup.setLayoutParams(paramsUp);

        ViewGroup.LayoutParams paramsDown = footerdown.getLayoutParams();
        paramsDown.height = lWidth;
        paramsDown.width = lWidth * 2;
        footerdown.setLayoutParams(paramsDown);

        ViewGroup.LayoutParams paramsLeft = footerleft.getLayoutParams();
        paramsLeft.height = lWidth * 2;
        paramsLeft.width = lWidth;
        footerleft.setLayoutParams(paramsLeft);


        ViewGroup.LayoutParams paramsRight = footerright.getLayoutParams();
        paramsRight.height = lWidth * 2;
        paramsRight.width = lWidth;
        footerright.setLayoutParams(paramsRight);
    }//_____________________________________________________________________________________________ SetLayout


}
