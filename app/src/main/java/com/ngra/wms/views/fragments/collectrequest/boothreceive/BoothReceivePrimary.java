package com.ngra.wms.views.fragments.collectrequest.boothreceive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.database.DB_ItemsWasteList;
import com.ngra.wms.databinding.FragmentBoothReceivePrimeryBinding;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.models.MR_Collect;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.models.ModelTime;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.boothreceive.VM_BoothReceivePrimary;
import com.ngra.wms.views.adaptors.collectrequest.AP_BoothList;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class BoothReceivePrimary extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable, OnMapReadyCallback,
        AP_BoothList.ItemBoothClick {

    private GoogleMap mMap;
    private VM_BoothReceivePrimary vm_boothReceivePrimary;
    private Integer timePosition = -1;
    private AP_BoothList ap_boothList;


    @BindView(R.id.MaterialSpinnerSpinnerDay)
    MaterialSpinner MaterialSpinnerSpinnerDay;

    @BindView(R.id.TextViewCount)
    TextView TextViewCount;

    @BindView(R.id.RecyclerViewBooths)
    RecyclerView RecyclerViewBooths;


    public BoothReceivePrimary() {//________________________________________________________________ BoothReceivePrimary
    }//_____________________________________________________________________________________________ BoothReceivePrimary

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
            SetVolumeWaste();
            vm_boothReceivePrimary.GetTypeTimes();
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
        setGetMessageFromObservable(
                BoothReceivePrimary.this,
                vm_boothReceivePrimary.getPublishSubject(),
                vm_boothReceivePrimary);

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
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable


        if (ap_boothList != null)
            ap_boothList.notifyDataSetChanged();

        if (action.equals(StaticValues.ML_GetTimeSheetTimes)) {
            SetMaterialSpinnersTimes();
            vm_boothReceivePrimary.GetBoothList();
            return;
        }

        if (action.equals(StaticValues.ML_GetBoothList)) {
            SetAdapterBooth();
            return;
        }

        if (action.equals(StaticValues.ML_CollectRequestDone)) {
            ap_boothList = null;
            RecyclerViewBooths.setAdapter(null);
            Realm realm = Realm.getDefaultInstance();
            RealmResults<DB_ItemsWasteList> delete = realm.where(DB_ItemsWasteList.class).findAll();
            try {
                realm.beginTransaction();
                delete.deleteAllFromRealm();
                realm.commitTransaction();
            } finally {
                realm.close();
            }
        }

        if (action.equals(StaticValues.ML_DialogClose)) {
            getContext().onBackPressed();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetMaterialSpinnersTimes() {//_____________________________________________________ SetMaterialSpinnersTimes

        ApplicationUtility utility = null;
        if (getContext() != null) {
            utility = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add(getResources().getString(R.string.ChooseDateDelivery));
        for (ModelTime item : vm_boothReceivePrimary.getModelTimes().getTimes()) {
            String builder = null;
            if (utility != null) {
                builder = utility.MiladiToJalali(item.getDate(), "FullJalaliString") +
                        " از ساعت " +
                        simpleDateFormat.format(item.getFrom()) +
                        " تا " +
                        simpleDateFormat.format(item.getTo());
            }
            buildingTypes.add(builder);
        }

        MaterialSpinnerSpinnerDay.setItems(buildingTypes);


        MaterialSpinnerSpinnerDay.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0)
                return;
            if (timePosition == -1) {
                timePosition = position - 1;
                MaterialSpinnerSpinnerDay.getItems().remove(0);
                MaterialSpinnerSpinnerDay.setSelectedIndex(MaterialSpinnerSpinnerDay.getItems().size() - 1);
                MaterialSpinnerSpinnerDay.setSelectedIndex(position - 1);
            } else
                timePosition = position;
            MaterialSpinnerSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

    }//_____________________________________________________________________________________________ SetMaterialSpinnersTimes


    private void SetVolumeWaste() {//_______________________________________________________________ SetVolumeWaste
        if (getContext() != null) {
            Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
            int count = realm.where(DB_ItemsWasteList.class).sum("Amount").intValue();
            TextViewCount.setText(String.valueOf(count));
        }
    }//_____________________________________________________________________________________________ SetVolumeWaste


    private void SetAdapterBooth() {//______________________________________________________________ SetAdapterBooth
        ap_boothList = new AP_BoothList(BoothReceivePrimary.this, vm_boothReceivePrimary.getBoothList());
        RecyclerViewBooths.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewBooths.setAdapter(ap_boothList);
        ShowBoothsOnMap();
    }//_____________________________________________________________________________________________ SetAdapterBooth


    private void ShowBoothsOnMap() {//______________________________________________________________ ShowBoothsOnMap
//        List<LatLng> latLngsBooth = new ArrayList<>();
//        if (vm_boothReceivePrimary.getBoothList() != null && vm_boothReceivePrimary.getBoothList().size() > 0) {
//            MehrdadLatifiMap latifiMap = new MehrdadLatifiMap();
//            latifiMap.setGoogleMap(mMap);
//            for (MD_Booth md_booth : vm_boothReceivePrimary.getBoothList()) {
//                LatLng latLng = new LatLng(md_booth.getLocation().getLatitude(), md_booth.getLocation().getLongitude());
//                latLngsBooth.add(latLng);
//                latifiMap.AddMarker(latLng, md_booth.getName(), "", R.drawable.marker_point);
//            }
//            latifiMap.setML_LatLongs(latLngsBooth);
//            latifiMap.AutoZoom();
//        }
    }//_____________________________________________________________________________________________ ShowBoothsOnMap


    @Override
    public void itemBoothMap(Integer position) {//__________________________________________________ itemBoothClick
//        mMap.clear();
//        MD_Location md_location = vm_boothReceivePrimary.getBoothList().get(position).getLocation();
//        LatLng latLng = new LatLng(md_location.getLatitude(), md_location.getLongitude());
//        MehrdadLatifiMap latifiMap = new MehrdadLatifiMap();
//        latifiMap.setGoogleMap(mMap);
//        latifiMap.AddMarker(latLng, vm_boothReceivePrimary.getBoothList().get(position).getName(), "", R.drawable.marker_point);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng)      // Sets the center of the map to Mountain View
//                .zoom(16)                   // Sets the zoom
//                .build();                   // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }//_____________________________________________________________________________________________ itemBoothClick


    @Override
    public void itemChoose(Integer position) {//____________________________________________________ itemChoose

        if (timePosition == -1) {
            MaterialSpinnerSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerSpinnerDay.requestFocus();
            ap_boothList.notifyDataSetChanged();
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DB_ItemsWasteList> wasteLists = realm.where(DB_ItemsWasteList.class).findAll();
        List<MR_Collect> collects = new ArrayList<>();
        for (DB_ItemsWasteList item : wasteLists) {
            MD_ItemWaste waste = new MD_ItemWaste(item.getId(), "", "");
            MR_Collect collect = new MR_Collect(waste, item.getAmount());
            collects.add(collect);
        }
        realm.close();

        MD_WasteAmountRequests md_wasteAmountRequests = new MD_WasteAmountRequests(
                0,
                vm_boothReceivePrimary.getBoothList().get(position),
                vm_boothReceivePrimary.getModelTimes().getTimes().get(timePosition),
                collects);
        vm_boothReceivePrimary.SendCollectRequest(md_wasteAmountRequests);

    }//_____________________________________________________________________________________________ itemChoose
}
