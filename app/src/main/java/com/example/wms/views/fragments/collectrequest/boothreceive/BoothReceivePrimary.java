package com.example.wms.views.fragments.collectrequest.boothreceive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentBoothReceivePrimeryBinding;
import com.example.wms.viewmodels.collectrequest.boothreceive.VM_BoothReceivePrimary;
import com.example.wms.views.fragments.FragmentPrimary;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoothReceivePrimary extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable, OnMapReadyCallback {

    private GoogleMap mMap;
    private VM_BoothReceivePrimary vm_boothReceivePrimary;

    @BindView(R.id.FBRPSpinnerHours)
    MaterialSpinner FBRPSpinnerHours;

    @BindView(R.id.FBRPSpinnerDay)
    MaterialSpinner FBRPSpinnerDay;


    public BoothReceivePrimary() {//________________________________________________________________ BoothReceivePrimary
    }//_____________________________________________________________________________________________ BoothReceivePrimary

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_boothReceivePrimary = new VM_BoothReceivePrimary(getContext());
            FragmentBoothReceivePrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_booth_receive_primery,container,false
            );
            binding.setVMBoothReceivePrimary(vm_boothReceivePrimary);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetMaterialSpinnersItems();
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);
        setGetMessageFromObservable(
                BoothReceivePrimary.this,
                vm_boothReceivePrimary.getPublishSubject(),
                vm_boothReceivePrimary);
    }//_____________________________________________________________________________________________ End onStart



    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FBRPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
        FBRPSpinnerDay.setItems("روز تحویل","شنبه","سه شنبه");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems



    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap)
    {//_____________________________________________________________________________________________ Start Void onMapReady
        mMap = googleMap;
        LatLng sydney = new LatLng(35.832483, 50.961751);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Negra"));
        float zoom = (float) 11;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }//_____________________________________________________________________________________________ End Void onMapReady



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
