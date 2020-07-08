package com.example.wms.views.fragments.packrequest;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;
import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestPrimaryBinding;
import com.example.wms.models.ModelPackage;
import com.example.wms.models.ModelTime;
import com.example.wms.utility.ApplicationUtility;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.packrequest.VM_PackageRequestPrimary;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.home.Home;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import params.com.stepview.StatusViewScroller;

public class PackageRequestPrimary extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_PackageRequestPrimary vm_packageRequestPrimary;
    private Integer timeId;
    private Integer TimePosition;

    @BindView(R.id.FPRPSpinnerHours)
    MaterialSpinner FPRPSpinnerHours;

    @BindView(R.id.FPRPSpinnerDay)
    MaterialSpinner FPRPSpinnerDay;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textTime)
    TextView textTime;

    @BindView(R.id.RelativeLayoutSave)
    RelativeLayout RelativeLayoutSave;

    @BindView(R.id.LinearLayoutPackageState)
    LinearLayout LinearLayoutPackageState;

    @BindView(R.id.FPRPStatusViewScroller)
    StatusViewScroller FPRPStatusViewScroller;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;


    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_packageRequestPrimary = new VM_PackageRequestPrimary(getContext());
            FragmentPackRequestPrimaryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request_primary, container, false);
            binding.setVmRequestprimary(vm_packageRequestPrimary);
            setView(binding.getRoot());
            SetOnClick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                PackageRequestPrimary.this,
                vm_packageRequestPrimary.getPublishSubject(),
                vm_packageRequestPrimary);

        SetStatusPackageRequest();

    }//_____________________________________________________________________________________________ onStart


    private void SetStatusPackageRequest() {//_____________________________________________________ SetStatusPackageRequest


        Byte statues = vm_packageRequestPrimary.GetPackageStatus();

        if (statues.equals(StaticValues.PR_NotRequested)) {
            RelativeLayoutSave.setVisibility(View.VISIBLE);
            LinearLayoutPackageState.setVisibility(View.GONE);
            FPRPSpinnerDay.setVisibility(View.VISIBLE);
            vm_packageRequestPrimary.GetTypeTimes();
        } else {
            FPRPStatusViewScroller.getStatusView().setCurrentCount(statues + 1);
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            SetPackageDate(StaticFunctions.PackageRequestDate(getContext()));
        }


    }//_____________________________________________________________________________________________ SetStatusPackageRequest


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissLoading();

        if (action.equals(StaticValues.ML_SendPackageRequest)) {
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            FPRPStatusViewScroller.getStatusView().setCurrentCount(2);
            ModelPackage modelPackage = new ModelPackage();
            modelPackage.setRequestDate(vm_packageRequestPrimary.getModelTimes().getTimes().get(TimePosition).getDate());
            modelPackage.setFromDeliver(vm_packageRequestPrimary.getModelTimes().getTimes().get(TimePosition).getFrom());
            modelPackage.setToDeliver(vm_packageRequestPrimary.getModelTimes().getTimes().get(TimePosition).getTo());
            SetPackageDate(modelPackage);

        }

        if (action.equals(StaticValues.ML_GetTimeSheetTimes)) {
            SetMaterialSpinnersTimes();
        }


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetPackageDate(ModelPackage modelPackage) {//______________________________________ SetPackageDate
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        ApplicationUtility utility = null;
        if (getContext() != null) {
            utility = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility();
        }
        if (utility != null) {
            textDate.setText(utility.MiladiToJalali(
                    modelPackage.getFromDeliver(), "FullJalaliString"));
        }
        String builder = simpleDateFormat.format(modelPackage.getFromDeliver()) +
                " تا " +
                simpleDateFormat.format(modelPackage.getToDeliver());
        textTime.setText(builder);
    }//_____________________________________________________________________________________________ SetPackageDate


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

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

        FPRPSpinnerDay.setOnClickListener(v -> {
            if (vm_packageRequestPrimary.getModelTimes() == null) {
                ShowProgressDialog();
                vm_packageRequestPrimary.GetTypeTimes();
            } else
                SetMaterialSpinnersTimes();
        });

        FPRPSpinnerDay.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0)
                return;
            TimePosition = position - 1;
            timeId = vm_packageRequestPrimary.getModelTimes().getTimes().get(TimePosition).getId();
            FPRPSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

        RelativeLayoutSave.setOnClickListener(v -> {
            if (StaticFunctions.isCancel) {
                if (CheckEmpty()) {
                    ShowLoading();
                    vm_packageRequestPrimary.SendPackageRequest(timeId);
                }
            }
        });


    }//_____________________________________________________________________________________________ SetOnClick


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        if (FPRPSpinnerDay.getSelectedIndex() == 0) {
            FPRPSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            FPRPSpinnerDay.requestFocus();
            return false;
        } else
            return true;
    }//_____________________________________________________________________________________________ CheckEmpty


    private void SetMaterialSpinnersTimes() {//_____________________________________________________ SetMaterialSpinnersTimes

        ApplicationUtility utility = null;
        if (getContext() != null) {
            utility = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add("انتخاب تاریخ دریافت");
        for (ModelTime item : vm_packageRequestPrimary.getModelTimes().getTimes()) {
            String builder = null;
            if (utility != null) {
                builder = utility.MiladiToJalali(item.getDate(), "FullJalaliString") +
                        " از ساعت " +
                        simpleDateFormat.format(item.getFrom()) +
                        " تا " +
                        simpleDateFormat.format(item.getTo());
            }
            buildingTypes.add(builder);
        }

        FPRPSpinnerDay.setItems(buildingTypes);

    }//_____________________________________________________________________________________________ SetMaterialSpinnersTimes


    private void DismissLoading() {//_______________________________________________________________ DismissLoading
        StaticFunctions.isCancel = true;
        txtLoading.setText(getResources().getString(R.string.Save));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ DismissLoading


    private void ShowLoading() {//__________________________________________________________________ ShowLoading
        StaticFunctions.isCancel = false;
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ ShowLoading


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog

        if (getContext() != null) {
            DialogProgress progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .ShowProgress(getContext(), null);
            progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }
    }//_____________________________________________________________________________________________ ShowProgressDialog


}
