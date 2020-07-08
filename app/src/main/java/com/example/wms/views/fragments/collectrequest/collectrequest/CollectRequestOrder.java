package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestOrdersBinding;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestOrder;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.ButterKnife;

public class CollectRequestOrder extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_CollectRequestOrder vm_collectRequestOrder;


    public CollectRequestOrder() {//________________________________________________________________ CollectRequestOrder
    }//_____________________________________________________________________________________________ CollectRequestOrder


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
//        if (getView() == null) {
            vm_collectRequestOrder = new VM_CollectRequestOrder(getContext());
            FragmentCollectRequestOrdersBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_collect_request_orders,container,false);
            binding.setVMCollectOrder(vm_collectRequestOrder);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
//        }

        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                CollectRequestOrder.this,
                vm_collectRequestOrder.getPublishSubject(),
                vm_collectRequestOrder);
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
