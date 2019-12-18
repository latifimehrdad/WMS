package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.cunoraz.gifview.library.GifView;
import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestAddressBinding;
import com.example.wms.viewmodels.packrequest.FragmentPackRequestAddressViewModel;

import com.example.wms.views.mlmap.MehrdadLatifiMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentPackRequestAddress extends Fragment implements OnMapReadyCallback{

    private Context context;
    private FragmentPackRequestAddressViewModel fragmentPackRequestAddressViewModel;
    private GoogleMap mMap;
    private boolean FullScreen = false;
    private StringBuilder mResult;


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

    @BindView(R.id.imgFullScreen)
    ImageView imgFullScreen;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.LayoutChoose)
    RelativeLayout LayoutChoose;


    @BindView(R.id.textChoose)
    TextView textChoose;

    @BindView(R.id.MarkerGif)
    GifView MarkerGif;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getContext();
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


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);

        SetMaterialSpinnersItems();
        SetOnClick();
        FullScreen = false;
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);

    }//_____________________________________________________________________________________________ End onStart


    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FPRAMaterialSpinnerType.setItems("نوع واحد", "آپارتمان", "ویلایی");
        FPRAMaterialSpinnerUser.setItems("کاربری ساختمان", "تجاری", "مسکونی");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems


    private void BackClick(View view) {//___________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetBackClickAndGoHome(true));
        FPRAMaterialSpinnerType.setOnKeyListener(SetBackClickAndGoHome(true));
        UnitCount.setOnKeyListener(SetBackClickAndGoHome(true));
        FPRAMaterialSpinnerUser.setOnKeyListener(SetBackClickAndGoHome(true));
        PersonCount.setOnKeyListener(SetBackClickAndGoHome(true));
        fpraEditAddress.setOnKeyListener(SetBackClickAndGoHome(true));

    }//_____________________________________________________________________________________________ End BackClick


    @Override
    public void onMapReady(GoogleMap googleMap) {//_____________________________________________________________________________________________ Start Void onMapReady
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                textChoose.setVisibility(View.VISIBLE);
                MarkerGif.setVisibility(View.GONE);
            }
        });


        DrawPolyline();
    }//_____________________________________________________________________________________________ End Void onMapReady


    private void SetOnClick() {//___________________________________________________________________ Start SetOnClick

        LayoutChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FullScreen) {
                    LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
                    childParam1.weight = 1;
                    scrollView.setLayoutParams(childParam1);
                    FullScreen = false;
                } else {
                    LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
                    childParam1.weight = 0.02f;
                    scrollView.setLayoutParams(childParam1);
                    FullScreen = true;
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetOnClick


    private void DrawPolyline() {
//        List<LatLng> latLngs = new ArrayList<>();
//        latLngs.add(new LatLng(35.831414,50.959419));
//        latLngs.add(new LatLng(35.830170,50.961350));
//        latLngs.add(new LatLng(35.829100,50.963453));
//        latLngs.add(new LatLng(35.827395,50.962080));
//        latLngs.add(new LatLng(35.827586, 50.960814));
//        latLngs.add(new LatLng(35.828960,50.961050));
//        latLngs.add(new LatLng(35.829343, 50.958894));
//        latLngs.add(new LatLng(35.831414,50.959419));
        MehrdadLatifiMap mehrdadLatifiMap = new MehrdadLatifiMap();
//        mehrdadLatifiMap.setML_LatLong(latLngs);
//        mehrdadLatifiMap.setML_Stroke_Width(2.0f);
//        mehrdadLatifiMap.setML_Fill_Color(getResources().getColor(R.color.mlPolyline));
//        mehrdadLatifiMap.setML_Stroke_Color(getResources().getColor(R.color.colorAccent));
//        mehrdadLatifiMap.setGoogleMap(mMap);
//        mehrdadLatifiMap.DrawPolygon(false);
//        mehrdadLatifiMap.setGoogleMap(mMap);
        LatLng negra = new LatLng(35.831414, 50.959419);
//        mehrdadLatifiMap.AddMarker(negra,"Negra","0",R.drawable.ic_check);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(negra, 13));
    }



}
