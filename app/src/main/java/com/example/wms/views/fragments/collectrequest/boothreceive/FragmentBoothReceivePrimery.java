package com.example.wms.views.fragments.collectrequest.boothreceive;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentBoothReceivePrimeryBinding;
import com.example.wms.viewmodels.collectrequest.boothreceive.VM_FragmentBoothReceivePrimary;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentBoothReceivePrimery extends Fragment implements OnMapReadyCallback {

    private Context context;
    private VM_FragmentBoothReceivePrimary vm_fragmentBoothReceivePrimary;
    private GoogleMap mMap;
    private View view;

    @BindView(R.id.FBRPSpinnerHours)
    MaterialSpinner FBRPSpinnerHours;

    @BindView(R.id.FBRPSpinnerDay)
    MaterialSpinner FBRPSpinnerDay;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentBoothReceivePrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_booth_receive_primery,container,false
        );
        vm_fragmentBoothReceivePrimary = new VM_FragmentBoothReceivePrimary(context);
        binding.setBoothreceiveprimery(vm_fragmentBoothReceivePrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentBoothReceivePrimery() {//________________________________________________________ Start FragmentBoothReceivePrimery
    }//_____________________________________________________________________________________________ End FragmentBoothReceivePrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);
        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onStart


    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FBRPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
        FBRPSpinnerDay.setItems("روز تحویل","شنبه","سه شنبه");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems



    private View.OnKeyListener SetKey(View view){//_________________________________________________ Start SetBackClickAndGoHome
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode != 4) {
                    return false;
                }
                //MainActivity.FragmentMessage.onNext("CollectRequest");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetBackClickAndGoHome



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




}
