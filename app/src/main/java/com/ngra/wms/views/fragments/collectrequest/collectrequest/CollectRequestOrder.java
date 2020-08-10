package com.ngra.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCollectRequestOrdersBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestOrder;
import com.ngra.wms.views.adaptors.collectrequest.AP_Order;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CollectRequestOrder extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable{

    private VM_CollectRequestOrder vm_collectRequestOrder;

    @BindView(R.id.RecyclerViewOrder)
    RecyclerView RecyclerViewOrder;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.TextViewNoRequest)
    TextView TextViewNoRequest;


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
        TextViewNoRequest.setVisibility(View.GONE);
        gifLoading.setVisibility(View.VISIBLE);
        RecyclerViewOrder.setVisibility(View.GONE);
        vm_collectRequestOrder.GetItemsOfOrder();
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        gifLoading.setVisibility(View.GONE);
        RecyclerViewOrder.setVisibility(View.VISIBLE);

        if (action.equals(StaticValues.ML_CollectOrderDone))
            SetAdapter();

    }//_____________________________________________________________________________________________ GetMessageFromObservable




    private void SetAdapter() {//___________________________________________________________________ SetAdapter

        AP_Order ap_order = new AP_Order(vm_collectRequestOrder.getMd_itemWasteRequests());
        RecyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewOrder.setAdapter(ap_order);

        if (vm_collectRequestOrder.getMd_itemWasteRequests().size() == 0)
            TextViewNoRequest.setVisibility(View.VISIBLE);

    }//_____________________________________________________________________________________________ SetAdapter



}
