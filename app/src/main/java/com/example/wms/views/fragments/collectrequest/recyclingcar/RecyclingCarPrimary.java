package com.example.wms.views.fragments.collectrequest.recyclingcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.database.DB_ItemsWasteList;
import com.example.wms.databinding.FragmentRecyclingCarPrimeryBinding;
import com.example.wms.models.ModelTime;
import com.example.wms.utility.ApplicationUtility;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.collectrequest.recyclingcar.VM_RecyclingCarPrimary;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.fragments.FragmentPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class RecyclingCarPrimary extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_RecyclingCarPrimary vm_recyclingCarPrimary;

    @BindView(R.id.MaterialSpinnerSpinnerDay)
    MaterialSpinner MaterialSpinnerSpinnerDay;

    @BindView(R.id.TextViewCount)
    TextView TextViewCount;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textTime)
    TextView textTime;

    public RecyclingCarPrimary() {//________________________________________________________________ RecyclingCarPrimary
    }//_____________________________________________________________________________________________ RecyclingCarPrimary


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            vm_recyclingCarPrimary = new VM_RecyclingCarPrimary(getContext());
            FragmentRecyclingCarPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_recycling_car_primery, container, false
            );
            binding.setVMRecyclingPrimary(vm_recyclingCarPrimary);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetVolumeWaste();
        vm_recyclingCarPrimary.GetTypeTimes();
        setGetMessageFromObservable(
                RecyclingCarPrimary.this,
                vm_recyclingCarPrimary.getPublishSubject(),
                vm_recyclingCarPrimary);
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable
        if (action.equals(StaticValues.ML_GetTimeSheetTimes)) {
            SetMaterialSpinnersTimes();
        }
    }//_____________________________________________________________________________________________ GetMessageFromObservable



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
        buildingTypes.add(getResources().getString(R.string.ChooseDateDelivery));
        for (ModelTime item : vm_recyclingCarPrimary.getModelTimes().getTimes()) {
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

        MaterialSpinnerSpinnerDay.setItems(buildingTypes);

    }//_____________________________________________________________________________________________ SetMaterialSpinnersTimes



    private void SetVolumeWaste() {//_______________________________________________________________ SetVolumeWaste
        Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
        Integer count = realm.where(DB_ItemsWasteList.class).sum("Count").intValue();
        TextViewCount.setText(count.toString());
    }//_____________________________________________________________________________________________ SetVolumeWaste


}
