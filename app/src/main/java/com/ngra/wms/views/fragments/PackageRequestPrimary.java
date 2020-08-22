package com.ngra.wms.views.fragments;

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
import com.ngra.wms.models.ModelPackage;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_PackageRequestPrimary;
import com.ngra.wms.views.application.ApplicationWMS;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import params.com.stepview.StatusViewScroller;

public class PackageRequestPrimary extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_PackageRequestPrimary vm_packageRequestPrimary;
    private Integer TimePosition;
    private NavController navController;

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

    @BindView(R.id.LinearLayoutTimeSheet)
    LinearLayout LinearLayoutTimeSheet;

    @BindView(R.id.TextViewRequest)
    TextView TextViewRequest;

    @BindView(R.id.TextViewSend)
    TextView TextViewSend;

    @BindView(R.id.TextViewDeliver)
    TextView TextViewDeliver;

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

        } else {
            if (statues.equals(StaticValues.PR_NotRequested)) {
                RelativeLayoutState.setVisibility(View.GONE);
                TextViewState.setVisibility(View.VISIBLE);
            } else {
                RelativeLayoutState.setVisibility(View.VISIBLE);
                TextViewState.setVisibility(View.GONE);
            }
            FPRPStatusViewScroller.getStatusView().setCurrentCount(statues + 1);
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            LinearLayoutTimeSheet.setVisibility(View.GONE);
            if (getContext() != null)
                setPackageDate(StaticFunctions.PackageRequestDate(getContext()));

            String tag = (String) TextViewRequest.getTag();
            if (statues.equals(Byte.valueOf(tag)))
                TextViewRequest.setBackground(getContext().getResources().getDrawable(R.drawable.layout_border_black));
            else
                TextViewRequest.setBackground(null);


            tag = (String) TextViewSend.getTag();
            if (statues.equals(Byte.valueOf(tag)))
                TextViewSend.setBackground(getContext().getResources().getDrawable(R.drawable.layout_border_black));
            else
                TextViewSend.setBackground(null);


            tag = (String) TextViewDeliver.getTag();
            if (statues.equals(Byte.valueOf(tag)))
                TextViewDeliver.setBackground(getContext().getResources().getDrawable(R.drawable.layout_border_black));
            else
                TextViewDeliver.setBackground(null);

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
            FPRPStatusViewScroller.getStatusView().setCurrentCount(2);
            ModelPackage modelPackage = new ModelPackage();
            setPackageDate(modelPackage);

        }

        if (action.equals(StaticValues.ML_GetTimeSheet)) {
            setMaterialSpinnersTimes();
        }


    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setPackageDate
    private void setPackageDate(ModelPackage modelPackage) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility();
        textDate.setText(component.GregorianToSun(modelPackage.getFromDeliver()).getFullStringSun());

        String builder = simpleDateFormat.format(modelPackage.getFromDeliver()) +
                " تا " +
                simpleDateFormat.format(modelPackage.getToDeliver());
        textTime.setText(builder);
    }
    //______________________________________________________________________________________________ setPackageDate


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
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetPackage);
            navController.navigate(R.id.action_packageRequestPrimary_to_timeSheet, bundle);

        });


    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        if (TimePosition == -1) {
            FPRPSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            FPRPSpinnerDay.requestFocus();
            return false;
        } else
            return true;
    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ setMaterialSpinnersTimes
    private void setMaterialSpinnersTimes() {

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add("انتخاب تاریخ دریافت");
/*        for (MD_Time item : vm_packageRequestPrimary.getMRTimes().getTimes()) {
            String builder = null;
            ApplicationUtility.MD_GregorianToSun toSun = component.GregorianToSun(item.getDate());
            builder = toSun.getFullStringSun();
            builder = builder +
                    " از " +
                    simpleDateFormat.format(item.getFrom()) +
                    " تا " +
                    simpleDateFormat.format(item.getTo());
            buildingTypes.add(builder);
        }*/

        FPRPSpinnerDay.setItems(buildingTypes);
    }
    //______________________________________________________________________________________________ setMaterialSpinnersTimes


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        StaticFunctions.isCancel = true;
        txtLoading.setText(getResources().getString(R.string.Save));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        StaticFunctions.isCancel = false;
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


}
