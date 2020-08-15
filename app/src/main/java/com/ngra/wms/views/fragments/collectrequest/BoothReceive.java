package com.ngra.wms.views.fragments.collectrequest;

import android.annotation.SuppressLint;
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

import com.cunoraz.gifview.library.GifView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentBoothReceivePrimeryBinding;
import com.ngra.wms.models.MD_Location;
import com.ngra.wms.utility.ML_Map;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.boothreceive.VM_BoothReceivePrimary;
import com.ngra.wms.views.adaptors.collectrequest.AP_BoothList;
import com.ngra.wms.views.fragments.FragmentPrimary;


import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class BoothReceive extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable, OnMapReadyCallback,
        AP_BoothList.ItemBoothClick {

    private GoogleMap mMap;
    private VM_BoothReceivePrimary vm_boothReceivePrimary;
    private AP_BoothList ap_boothList;
    private NavController navController;


    @BindView(R.id.RecyclerViewBooths)
    RecyclerView RecyclerViewBooths;

    @BindView(R.id.LinearLayoutMap)
    LinearLayout LinearLayoutMap;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    public BoothReceive() {//_______________________________________________________________________ BoothReceive
    }//_____________________________________________________________________________________________ BoothReceive



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_boothReceivePrimary = new VM_BoothReceivePrimary(getContext());
            FragmentBoothReceivePrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_booth_receive_primery, container, false
            );
            binding.setVMBoothReceivePrimary(vm_boothReceivePrimary);
            setView(binding.getRoot());
            gifLoading.setVisibility(View.VISIBLE);

            vm_boothReceivePrimary.GetBoothList();
            LinearLayoutMap.setVisibility(View.GONE);
//            if (getContext() != null && getArguments() != null) {
//                WasteEstimateId = getArguments().getString(getContext().getResources().getString(R.string.ML_Id), "-1");
//                TimeId = getArguments().getInt(getContext().getResources().getString(R.string.ML_TimeId), -1);
//            }

        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        setPublishSubjectFromObservable(
                BoothReceive.this,
                vm_boothReceivePrimary.getPublishSubject(),
                vm_boothReceivePrimary);

        if (getView() != null)
            navController = Navigation.findNavController(getView());

    }//_____________________________________________________________________________________________ End onStart


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {//_____________________________________________________________________________________________ Start Void onMapReady
        mMap = googleMap;
        LatLng sydney = new LatLng(35.832483, 50.961751);
        float zoom = (float) 10;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }//_____________________________________________________________________________________________ End Void onMapReady


    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        gifLoading.setVisibility(View.GONE);

        if (ap_boothList != null)
            ap_boothList.notifyDataSetChanged();


        if (action.equals(StaticValues.ML_GetBoothList)) {
            SetAdapterBooth();
            return;
        }


        if (action.equals(StaticValues.ML_DialogClose)) {
            if (getContext() != null) {
                getContext().onBackPressed();
                getContext().onBackPressed();
            }
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable




    private void SetAdapterBooth() {//______________________________________________________________ SetAdapterBooth
        ap_boothList = new AP_BoothList(BoothReceive.this, vm_boothReceivePrimary.getBoothList());
        RecyclerViewBooths.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewBooths.setAdapter(ap_boothList);
    }//_____________________________________________________________________________________________ SetAdapterBooth



    @Override
    public void itemBoothMap(Integer position) {//__________________________________________________ itemBoothClick
        mMap.clear();
        if (vm_boothReceivePrimary.getBoothList() != null && vm_boothReceivePrimary.getBoothList().size() > 0)
            if (vm_boothReceivePrimary.getBoothList().get(position).getLocation()!= null){
                LinearLayoutMap.setVisibility(View.VISIBLE);
                MD_Location md_location = vm_boothReceivePrimary.getBoothList().get(position).getLocation();
                LatLng latLng = new LatLng(md_location.getLatitude(), md_location.getLongitude());
                ML_Map latifiMap = new ML_Map();
                latifiMap.setGoogleMap(mMap);
                latifiMap.AddMarker(latLng, vm_boothReceivePrimary.getBoothList().get(position).getName(), "", R.drawable.marker_point);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(16)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
    }//_____________________________________________________________________________________________ itemBoothClick




    @Override
    public void itemChoose(Integer position) {//____________________________________________________ itemChoose

        Integer BoothId = vm_boothReceivePrimary.getBoothList().get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetBooth);
        bundle.putInt(getContext().getString(R.string.ML_Id), BoothId);
        navController.navigate(R.id.action_boothReceive2_to_timeSheet, bundle);

    }//_____________________________________________________________________________________________ itemChoose
}
