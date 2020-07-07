package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestOrdersBinding;
import com.example.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestOrder;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestPrimary;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.learn.Learn;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectRequestPrimary extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_CollectRequestPrimary vm_collectRequestPrimary;
    private NavController navController;


    @BindView(R.id.fcrpRecyclingCar)
    LinearLayout fcrpRecyclingCar;

    @BindView(R.id.fcrpBoothReceive)
    LinearLayout fcrpBoothReceive;

    public CollectRequestPrimary() {//______________________________________________________________ CollectRequestPrimary
    }//_____________________________________________________________________________________________ CollectRequestPrimary

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
//        if (getView() == null) {
            vm_collectRequestPrimary = new VM_CollectRequestPrimary(getContext());
            FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_collect_request_primery, container, false
            );
            binding.setVMCollectPrimary(vm_collectRequestPrimary);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetClicks();
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

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClicks() {//____________________________________________________________________ Start

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

    }//_____________________________________________________________________________________________ End

}
