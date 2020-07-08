package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestPrimary;
import com.example.wms.views.adaptors.collectrequest.AP_ItemsWast;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;


public class CollectRequestPrimary extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_ItemsWast.ItemWastClick {


    private VM_CollectRequestPrimary vm_collectRequestPrimary;
    private NavController navController;


    @BindView(R.id.fcrpRecyclingCar)
    LinearLayout fcrpRecyclingCar;

    @BindView(R.id.fcrpBoothReceive)
    LinearLayout fcrpBoothReceive;

    @BindView(R.id.RecyclerViewItemsWaste)
    RecyclerView RecyclerViewItemsWaste;

    public CollectRequestPrimary() {//______________________________________________________________ CollectRequestPrimary
    }//_____________________________________________________________________________________________ CollectRequestPrimary

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
//        if (getView() == null) {
        vm_collectRequestPrimary = new VM_CollectRequestPrimary(getContext());
        FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_primery, container, false
        );
        binding.setVMCollectPrimary(vm_collectRequestPrimary);
        setView(binding.getRoot());
        SetClicks();
        GetItemsOfWast();
//        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                CollectRequestPrimary.this,
                vm_collectRequestPrimary.getPublishSubject(),
                vm_collectRequestPrimary);
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GetItemsOfWastIsSuccess)) {
            SetItemsWastAdapter();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GetItemsOfWast() {//_______________________________________________________________ GetItemsOfWast
        vm_collectRequestPrimary.GetItemsOfWast();
    }//_____________________________________________________________________________________________ GetItemsOfWast


    private void SetClicks() {//____________________________________________________________________ SetClicks

        fcrpRecyclingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_collectRequest_to_recyclingCar);
            }
        });

        fcrpBoothReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_collectRequest_to_boothReceive);
            }
        });

    }//_____________________________________________________________________________________________ SetClicks


    private void SetItemsWastAdapter() {//__________________________________________________________ getMd_itemWasts
        AP_ItemsWast ap_itemsWast = new AP_ItemsWast(vm_collectRequestPrimary.getMd_itemWasts(), CollectRequestPrimary.this);
        RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RecyclerViewItemsWaste.setAdapter(ap_itemsWast);
    }//_____________________________________________________________________________________________ getMd_itemWasts



    @Override
    public void itemWastClick(Integer position) {//_________________________________________________ itemWastClick

    }//_____________________________________________________________________________________________ itemWastClick


}
