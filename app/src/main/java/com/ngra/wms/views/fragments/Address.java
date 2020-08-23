package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrAddressBinding;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_Time;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Address;
import com.ngra.wms.views.adaptors.AP_Address;


import org.jetbrains.annotations.NotNull;


import butterknife.BindView;

public class Address extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable,
        AP_Address.ItemAddressClick {


    private VM_Address vm_address;
    private Integer timeSheetId;
    private Integer boothId;
    private NavController navController;
    private int TimeSheetType;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewAddress)
    RecyclerView RecyclerViewAddress;

    @BindView(R.id.ImageViewNew)
    ImageView ImageViewNew;


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_address = new VM_Address(getContext());
            FrAddressBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_address, container, false
            );
            binding.setAddress(vm_address);
            setView(binding.getRoot());
            gifLoading.setVisibility(View.VISIBLE);
            timeSheetId = getArguments().getInt(getContext().getString(R.string.ML_TimeId), 0);
            boothId = getArguments().getInt(getContext().getString(R.string.ML_Id), 0);
            TimeSheetType = getArguments().getInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetBooth);
            setOnclick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Address.this,
                vm_address.getPublishSubject(),
                vm_address);

        if (getView() != null)
            navController = Navigation.findNavController(getView());

        getAddress();

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {
        ImageViewNew.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getResources().getString(R.string.ML_Id), 0);
            navController.navigate(R.id.action_address_to_packageRequestAddress, bundle);
        });
    }
    //______________________________________________________________________________________________ setOnclick


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetAddress)) {
            setItemsAddress();
            return;
        }

        if (action.equals(StaticValues.ML_CollectRequestDone)) {
            if (boothId != 0) {
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
            } else {
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
            }
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ getAddress
    private void getAddress() {
        vm_address.getContactAddress();
    }
    //______________________________________________________________________________________________ getAddress


    //______________________________________________________________________________________________ setItemsAddress
    private void setItemsAddress() {
        AP_Address ap_address = new AP_Address(vm_address.getAddress(), Address.this);
        RecyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewAddress.setAdapter(ap_address);
    }
    //______________________________________________________________________________________________ setItemsAddress


    //______________________________________________________________________________________________ itemAddressClick
    @Override
    public void itemAddressClick(Integer position) {

        if (timeSheetId != -1) {
            MD_WasteAmountRequests md_wasteAmountRequests;
            if (boothId != 0) {
                md_wasteAmountRequests = new MD_WasteAmountRequests(
                        0,
                        new MD_Booth(boothId),
                        new MD_Time(timeSheetId),
                        ChooseWaste.wasteLists,
                        new MD_SpinnerItem(vm_address.getAddress().get(position).getId()));

            } else {
                md_wasteAmountRequests = new MD_WasteAmountRequests(
                        1,
                        null,
                        new MD_Time(timeSheetId),
                        ChooseWaste.wasteLists,
                        new MD_SpinnerItem(vm_address.getAddress().get(position).getId()));
            }

            vm_address.sendCollectRequest(md_wasteAmountRequests);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(getResources().getString(R.string.ML_Id), Integer.valueOf(vm_address.getAddress().get(position).getId()));
            navController.navigate(R.id.action_address_to_packageRequestAddress, bundle);
        }


    }
    //______________________________________________________________________________________________ itemAddressClick


}
