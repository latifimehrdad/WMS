package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestOrdersBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestOrder;
import com.example.wms.views.adaptors.collectrequest.AP_Order;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CollectRequestOrder extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_Order.ItemOrderClick {

    private VM_CollectRequestOrder vm_collectRequestOrder;

    @BindView(R.id.RecyclerViewOrder)
    RecyclerView RecyclerViewOrder;



    public CollectRequestOrder() {//________________________________________________________________ CollectRequestOrder
    }//_____________________________________________________________________________________________ CollectRequestOrder


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
//        if (getView() == null) {
            vm_collectRequestOrder = new VM_CollectRequestOrder(getContext());
            FragmentCollectRequestOrdersBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_collect_request_orders,container,false);
            binding.setVMCollectOrder(vm_collectRequestOrder);
            setView(binding.getRoot());
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
        vm_collectRequestOrder.GetItemsOfOrder();
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_CollectOrderDone))
            SetAdapter();

    }//_____________________________________________________________________________________________ GetMessageFromObservable




    private void SetAdapter() {//___________________________________________________________________ SetAdapter

        AP_Order ap_order = new AP_Order(vm_collectRequestOrder.getMd_itemWasteRequests(),CollectRequestOrder.this);
        RecyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewOrder.setAdapter(ap_order);

    }//_____________________________________________________________________________________________ SetAdapter


    @Override
    public void itemOrderCall(Integer position) {//___________________________________________ itemOrderCall

    }//_____________________________________________________________________________________________ itemOrderCall


}
