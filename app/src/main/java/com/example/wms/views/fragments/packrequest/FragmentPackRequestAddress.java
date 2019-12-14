package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestAddressBinding;
import com.example.wms.viewmodels.packrequest.FragmentPackRequestAddressViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentPackRequestAddress extends Fragment implements OnMapReadyCallback {

    private Context context;
    private FragmentPackRequestAddressViewModel fragmentPackRequestAddressViewModel;
    private GoogleMap mMap;

    @BindView(R.id.FPRAMaterialSpinnerType)
    MaterialSpinner FPRAMaterialSpinnerType;

    @BindView(R.id.UnitCount)
    EditText UnitCount;

    @BindView(R.id.FPRAMaterialSpinnerUser)
    MaterialSpinner FPRAMaterialSpinnerUser;

    @BindView(R.id.PersonCount)
    EditText PersonCount;

    @BindView(R.id.fpraEditAddress)
    EditText fpraEditAddress;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPackRequestAddressBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack_request_address, container, false
        );
        fragmentPackRequestAddressViewModel = new FragmentPackRequestAddressViewModel(context);
        binding.setRequstaddress(fragmentPackRequestAddressViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentPackRequestAddress(Context context) {//__________________________________________ Start FragmentPackRequestAddress
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentPackRequestAddress


    public FragmentPackRequestAddress() {//_________________________________________________________ Start FragmentPackRequestAddress
    }//_____________________________________________________________________________________________ End FragmentPackRequestAddress


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);

        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onStart


    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FPRAMaterialSpinnerType.setItems("نوع واحد", "آپارتمان", "ویلایی");
        FPRAMaterialSpinnerUser.setItems("کاربری ساختمان", "تجاری", "مسکونی");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems




    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
        FPRAMaterialSpinnerType.setOnKeyListener(SetKey(FPRAMaterialSpinnerType));
        UnitCount.setOnKeyListener(SetKey(UnitCount));
        FPRAMaterialSpinnerUser.setOnKeyListener(SetKey(FPRAMaterialSpinnerUser));
        PersonCount.setOnKeyListener(SetKey(PersonCount));
        fpraEditAddress.setOnKeyListener(SetKey(fpraEditAddress));

    }//_____________________________________________________________________________________________ End BackClick




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
