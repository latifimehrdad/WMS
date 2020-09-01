package com.ngra.wms.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.google.android.gms.maps.model.LatLng;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCollectRequestOrdersBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_CollectRequestOrder;
import com.ngra.wms.views.adaptors.collectrequest.AP_Order;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CollectRequestOrder extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable,
        AP_Order.ItemRequestClick {

    private VM_CollectRequestOrder vm_collectRequestOrder;

    @BindView(R.id.RecyclerViewOrder)
    RecyclerView RecyclerViewOrder;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.TextViewNoRequest)
    TextView TextViewNoRequest;


    //______________________________________________________________________________________________ CollectRequestOrder
    public CollectRequestOrder() {
    }
    //______________________________________________________________________________________________ CollectRequestOrder


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
//        if (getView() == null) {
        vm_collectRequestOrder = new VM_CollectRequestOrder(getContext());
        FragmentCollectRequestOrdersBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_orders, container, false);
        binding.setVMCollectOrder(vm_collectRequestOrder);
        setView(binding.getRoot());
//        }

        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                CollectRequestOrder.this,
                vm_collectRequestOrder.getPublishSubject(),
                vm_collectRequestOrder);
        TextViewNoRequest.setVisibility(View.GONE);
        gifLoading.setVisibility(View.VISIBLE);
        RecyclerViewOrder.setVisibility(View.GONE);
        vm_collectRequestOrder.getItemsOfOrder();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);
        RecyclerViewOrder.setVisibility(View.VISIBLE);

        if (action.equals(StaticValues.ML_CollectOrderDone)) {
            setAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_CancelRequest)) {
            vm_collectRequestOrder.getItemsOfOrder();
            return;
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {

        AP_Order ap_order = new AP_Order(vm_collectRequestOrder.getMd_itemWasteRequests(), CollectRequestOrder.this);
        RecyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewOrder.setAdapter(ap_order);

        if (vm_collectRequestOrder.getMd_itemWasteRequests().size() == 0)
            TextViewNoRequest.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ setAdapter



    //______________________________________________________________________________________________ itemRequestCancel
    @Override
    public void itemRequestCancel(Integer position) {
        vm_collectRequestOrder.cancelRequestWaste(position);
    }
    //______________________________________________________________________________________________ itemRequestCancel



    //______________________________________________________________________________________________ itemRequestRouting
    @Override
    public void itemRequestRouting(Integer position) {

        LatLng latLng = new LatLng(vm_collectRequestOrder.getMd_itemWasteRequests().get(position).getBooth().getLocation().getLatitude(),
                vm_collectRequestOrder.getMd_itemWasteRequests().get(position).getBooth().getLocation().getLongitude());
        String uri = "geo:" + latLng.latitude + "," + latLng.longitude + "?q=" + latLng.latitude + "," + latLng.longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
    //______________________________________________________________________________________________ itemRequestRouting



    //______________________________________________________________________________________________ itemRequestCall
    @Override
    public void itemRequestCall(Integer position) {
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + vm_collectRequestOrder.getMd_itemWasteRequests().get(position).getBooth().getPhoneNumber()));
        getContext().startActivity(call);
    }
    //______________________________________________________________________________________________ itemRequestCall


}
