package com.ngra.wms.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentPackRequestPrimaryBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_PackageRequestPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class PackageRequestPrimary extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_PackageRequestPrimary vm_packageRequestPrimary;
    private Integer TimePosition;
    private NavController navController;

    @BindView(R.id.FPRPSpinnerDay)
    MaterialSpinner FPRPSpinnerDay;

    @BindView(R.id.RelativeLayoutSave)
    RelativeLayout RelativeLayoutSave;

    @BindView(R.id.LinearLayoutPackageState)
    LinearLayout LinearLayoutPackageState;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.LinearLayoutTimeSheet)
    LinearLayout LinearLayoutTimeSheet;

    @BindView(R.id.RelativeLayoutState)
    RelativeLayout RelativeLayoutState;

    @BindView(R.id.TextViewState)
    TextView TextViewState;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_packageRequestPrimary = new VM_PackageRequestPrimary(getContext());
            FragmentPackRequestPrimaryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request_primary, container, false);
            binding.setVmRequestprimary(vm_packageRequestPrimary);
            setView(binding.getRoot());
            setOnClick();
            TimePosition = -1;
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                PackageRequestPrimary.this,
                vm_packageRequestPrimary.getPublishSubject(),
                vm_packageRequestPrimary);

        if (getView() != null)
            navController = Navigation.findNavController(getView());

        setStatusPackageRequest();

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ setStatusPackageRequest
    private void setStatusPackageRequest() {


        Byte statues = vm_packageRequestPrimary.getPackageStatus();

        if (statues.equals(StaticValues.PR_NotRequested)) {
            LinearLayoutTimeSheet.setVisibility(View.VISIBLE);
            RelativeLayoutSave.setVisibility(View.VISIBLE);
            LinearLayoutPackageState.setVisibility(View.GONE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            RelativeLayoutState.setVisibility(View.GONE);
            TextViewState.setVisibility(View.VISIBLE);

        } else {
            RelativeLayoutState.setVisibility(View.VISIBLE);
            TextViewState.setVisibility(View.GONE);
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            LinearLayoutTimeSheet.setVisibility(View.GONE);
            vm_packageRequestPrimary.setPackageDate();
        }


    }
    //______________________________________________________________________________________________ setStatusPackageRequest


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissLoading();

        if (action.equals(StaticValues.ML_SendPackageRequest)) {
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            LinearLayoutTimeSheet.setVisibility(View.GONE);
            vm_packageRequestPrimary.setPackageDate();

        }


    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (Home.TwoBackToHome) {
                        if (getContext() != null) {
                            getContext().onBackPressed();
                            getContext().onBackPressed();
                        }

                        return true;
                    } else
                        return false;
                } else
                    return false;
            });
        }


        FPRPSpinnerDay.setOnItemSelectedListener((view, position, id, item) -> {
            if (TimePosition == -1) {
                TimePosition = position - 1;
                FPRPSpinnerDay.getItems().remove(0);
                FPRPSpinnerDay.setSelectedIndex(FPRPSpinnerDay.getItems().size() - 1);
                FPRPSpinnerDay.setSelectedIndex(position - 1);
            } else
                TimePosition = position;

            FPRPSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

        RelativeLayoutSave.setOnClickListener(v -> {
            if (getContext() == null)
                return;
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetPackage);
            navController.navigate(R.id.action_packageRequestPrimary_to_timeSheet, bundle);

        });


    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ dismissLoading
    @SuppressLint("UseCompatLoadingForDrawables")
    private void dismissLoading() {
        if (getContext() == null)
            return;
        txtLoading.setText(getContext().getResources().getString(R.string.Save));
        RelativeLayoutSave.setBackground(getContext().getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ dismissLoading


}
