package com.ngra.wms.views.fragments.packrequest;

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
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentPackRequestPrimaryBinding;
import com.ngra.wms.models.ModelPackage;
import com.ngra.wms.models.MD_Time;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.packrequest.VM_PackageRequestPrimary;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ngra.wms.views.fragments.home.Home;
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
            TimePosition = -1;
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

        if (getView() != null)
            navController = Navigation.findNavController(getView());

        SetStatusPackageRequest();

    }//_____________________________________________________________________________________________ onStart


    private void SetStatusPackageRequest() {//______________________________________________________ SetStatusPackageRequest


        Byte statues = vm_packageRequestPrimary.GetPackageStatus();

        if (statues.equals(StaticValues.PR_NotRequested)) {
            LinearLayoutTimeSheet.setVisibility(View.VISIBLE);
            RelativeLayoutSave.setVisibility(View.VISIBLE);
            LinearLayoutPackageState.setVisibility(View.GONE);
            FPRPSpinnerDay.setVisibility(View.GONE);
//            Handler handler = new Handler();
//            handler.postDelayed(() -> {
//
//            },200);

            //vm_packageRequestPrimary.GetTypeTimes();
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
                SetPackageDate(StaticFunctions.PackageRequestDate(getContext()));

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


    }//_____________________________________________________________________________________________ SetStatusPackageRequest


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissLoading();

        if (action.equals(StaticValues.ML_SendPackageRequest)) {
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            LinearLayoutTimeSheet.setVisibility(View.GONE);
            FPRPStatusViewScroller.getStatusView().setCurrentCount(2);
            ModelPackage modelPackage = new ModelPackage();
/*
            modelPackage.setRequestDate(vm_packageRequestPrimary.getMRTimes().getTimes().get(TimePosition).getDate());
            modelPackage.setFromDeliver(vm_packageRequestPrimary.getMRTimes().getTimes().get(TimePosition).getFrom());
            modelPackage.setToDeliver(vm_packageRequestPrimary.getMRTimes().getTimes().get(TimePosition).getTo());
*/
            SetPackageDate(modelPackage);

        }

        if (action.equals(StaticValues.ML_GetTimeSheet)) {
            SetMaterialSpinnersTimes();
        }


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetPackageDate(ModelPackage modelPackage) {//______________________________________ SetPackageDate
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


        FPRPSpinnerDay.setOnItemSelectedListener((view, position, id, item) -> {
            if (TimePosition == -1) {
                TimePosition = position - 1;
                FPRPSpinnerDay.getItems().remove(0);
                FPRPSpinnerDay.setSelectedIndex(FPRPSpinnerDay.getItems().size() - 1);
                FPRPSpinnerDay.setSelectedIndex(position - 1);
            } else
                TimePosition = position;

//            timeId = vm_packageRequestPrimary.getMRTimes().getTimes().get(TimePosition).getId();
            FPRPSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

        RelativeLayoutSave.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetPackage);
            navController.navigate(R.id.action_packageRequestPrimary_to_timeSheet, bundle);

//            if (StaticFunctions.isCancel) {
//                if (CheckEmpty()) {
//                    ShowLoading();
//                    vm_packageRequestPrimary.SendPackageRequest(timeId);
//                }
//            }
        });


    }//_____________________________________________________________________________________________ SetOnClick


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        if (TimePosition == -1) {
            FPRPSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            FPRPSpinnerDay.requestFocus();
            return false;
        } else
            return true;
    }//_____________________________________________________________________________________________ CheckEmpty


    private void SetMaterialSpinnersTimes() {//_____________________________________________________ SetMaterialSpinnersTimes

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


/*
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
*/


}
