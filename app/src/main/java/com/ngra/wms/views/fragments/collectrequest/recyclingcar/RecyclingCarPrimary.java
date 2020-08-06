package com.ngra.wms.views.fragments.collectrequest.recyclingcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentRecyclingCarPrimeryBinding;
import com.ngra.wms.models.MD_WasteEstimate;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelTime;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.recyclingcar.VM_RecyclingCarPrimary;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;


public class RecyclingCarPrimary extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable {

    private VM_RecyclingCarPrimary vm_recyclingCarPrimary;
    private Integer timePosition = -1;
    private Integer WasteEstimateId;

    @BindView(R.id.MaterialSpinnerSpinnerDay)
    MaterialSpinner MaterialSpinnerSpinnerDay;

    @BindView(R.id.TextViewCount)
    TextView TextViewCount;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;

    public RecyclingCarPrimary() {//________________________________________________________________ RecyclingCarPrimary
    }//_____________________________________________________________________________________________ RecyclingCarPrimary


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            vm_recyclingCarPrimary = new VM_RecyclingCarPrimary(getContext());
            FragmentRecyclingCarPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_recycling_car_primery, container, false
            );
            binding.setVMRecyclingPrimary(vm_recyclingCarPrimary);
            setView(binding.getRoot());
            SetOnClick();
            if (getContext() != null && getArguments() != null)
                WasteEstimateId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), -1);
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        /*        SetVolumeWaste();*/
        vm_recyclingCarPrimary.GetTypeTimes();
        setGetMessageFromObservable(
                RecyclingCarPrimary.this,
                vm_recyclingCarPrimary.getPublishSubject(),
                vm_recyclingCarPrimary);
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissLoading();

        if (action.equals(StaticValues.ML_GetTimeSheetTimes)) {
            SetMaterialSpinnersTimes();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        RelativeLayoutSend.setOnClickListener(v -> {
            if (MaterialSpinnerSpinnerDay.getSelectedIndex() == 0) {
                MaterialSpinnerSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
                MaterialSpinnerSpinnerDay.requestFocus();
                return;
            }

            ShowLoading();


            MD_WasteAmountRequests md_wasteAmountRequests = new MD_WasteAmountRequests(
                    1,
                    null,
                    vm_recyclingCarPrimary.getModelTimes().getTimes().get(timePosition),
                    new MD_WasteEstimate(WasteEstimateId.toString()));
            vm_recyclingCarPrimary.SendCollectRequest(md_wasteAmountRequests);

        });
    }//_____________________________________________________________________________________________ SetOnClick


    private void SetMaterialSpinnersTimes() {//_____________________________________________________ SetMaterialSpinnersTimes

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add(getResources().getString(R.string.ChooseDateDelivery));
        for (ModelTime item : vm_recyclingCarPrimary.getModelTimes().getTimes()) {
            String builder = null;
            ApplicationUtility.MD_GregorianToSun toSun = component.GregorianToSun(item.getDate());
            builder = toSun.getFullStringSun();
            builder = builder +
                    " از " +
                    simpleDateFormat.format(item.getFrom()) +
                    " تا " +
                    simpleDateFormat.format(item.getTo());
            buildingTypes.add(builder);
        }

        MaterialSpinnerSpinnerDay.setItems(buildingTypes);


        MaterialSpinnerSpinnerDay.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0)
                return;
            if (timePosition == -1) {
                timePosition = position - 1;
                MaterialSpinnerSpinnerDay.getItems().remove(0);
                MaterialSpinnerSpinnerDay.setSelectedIndex(MaterialSpinnerSpinnerDay.getItems().size() - 1);
                MaterialSpinnerSpinnerDay.setSelectedIndex(position - 1);
            } else
                timePosition = position;

            MaterialSpinnerSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

    }//_____________________________________________________________________________________________ SetMaterialSpinnersTimes



/*
    private void SetVolumeWaste() {//_______________________________________________________________ SetVolumeWaste
        if (getContext() != null) {
            Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
            int count = realm.where(DB_ItemsWasteList.class).sum("Amount").intValue();
            TextViewCount.setText(String.valueOf(count));
        }
    }//_____________________________________________________________________________________________ SetVolumeWaste
*/


    private void DismissLoading() {//_______________________________________________________________ DismissLoading
        txtLoading.setText(getResources().getString(R.string.FragmentPackRequestPrimarySet));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ DismissLoading


    private void ShowLoading() {//__________________________________________________________________ ShowLoading
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ ShowLoading


}
