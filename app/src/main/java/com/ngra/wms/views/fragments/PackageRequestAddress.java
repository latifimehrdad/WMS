package com.ngra.wms.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentPackRequestAddressBinding;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_PackageRequestAddress;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.dialogs.DialogProgress;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ngra.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class PackageRequestAddress extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable, OnMapReadyCallback {


    private NavController navController;
    private VM_PackageRequestAddress vm_packageRequestAddress;
    private boolean FullScreen = false;
    private Integer TryToLocation = 0;
    private GoogleMap mMap;
    private LocationManager locationManager;
    public PackageRequestAddress.MyLocationListener listener;
    private Location GPSLocation = null;
    private Location NetworkLocation = null;
    private LatLng LocationAddress;
    private Long BuildingTypeId;
    private Long BuildingUseId;
    private DialogProgress progress;
    private Dialog dialogQuestion;
    private Location previousBestLocation = null;
    private String addressType;
    private Integer AddressId;

    @BindView(R.id.MaterialSpinnerType)
    MaterialSpinner MaterialSpinnerType;

    @BindView(R.id.EditTextUnitCount)
    EditText EditTextUnitCount;

    @BindView(R.id.MaterialSpinnerUses)
    MaterialSpinner MaterialSpinnerUses;

    @BindView(R.id.EditTextPersonCount)
    EditText EditTextPersonCount;

    @BindView(R.id.EditTextAddress)
    EditText EditTextAddress;

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

    @BindView(R.id.RelativeLayoutSave)
    RelativeLayout RelativeLayoutSave;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.LinearLayoutWaitMap)
    LinearLayout LinearLayoutWaitMap;

    @BindView(R.id.markerInfo)
    LinearLayout markerInfo;

    @BindView(R.id.EditTextPlateNumber)
    EditText EditTextPlateNumber;

    @BindView(R.id.EditTextUnitNumber)
    EditText EditTextUnitNumber;

    @BindView(R.id.textViewWaitMap)
    TextView textViewWaitMap;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_packageRequestAddress = new VM_PackageRequestAddress(getContext());
            FragmentPackRequestAddressBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request_address, container, false);
            binding.setVmRequestAddress(vm_packageRequestAddress);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        setPublishSubjectFromObservable(
                PackageRequestAddress.this,
                vm_packageRequestAddress.getPublishSubject(),
                vm_packageRequestAddress);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        LinearLayoutWaitMap.setVisibility(View.VISIBLE);
        textViewWaitMap.setText(getContext().getResources().getString(R.string.FindYourLocation));
        FullScreen = false;
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        if (TryToLocation == -1)
            getCurrentLocation();

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {

        BuildingTypeId = Long.valueOf(-1);
        BuildingUseId = Long.valueOf(-1);
        setTextWatcher();
        setOnClick();
        dismissLoading();
        vm_packageRequestAddress.getTypeBuilding();
        if (getArguments() != null) {
            addressType = getArguments().getString(getContext().getResources().getString(R.string.ML_Type), "");
            AddressId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            if (addressType.equalsIgnoreCase(getContext().getString(R.string.ML_Save)))
                vm_packageRequestAddress.getUserAddress();
        } else {
            addressType = "";
            AddressId = 0;
        }

    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissLoading();
        if (action.equals(StaticValues.ML_GetAddress)) {
            vm_packageRequestAddress.setAddress();
            return;
        }

        if (action.equals(StaticValues.ML_SetAddress)) {
            EditTextAddress.setText(vm_packageRequestAddress.getAddressString());
            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam1.weight = 1;
            scrollView.setLayoutParams(childParam1);
            FullScreen = false;
            return;
        }

        if (action.equals(StaticValues.ML_EditUserAddress)) {
            if (!addressType.equals(""))
                navController.navigate(R.id.action_packageRequestAddress_to_packageRequestPrimary);
            else
                getContext().onBackPressed();
            return;
        }

        if (action.equals(StaticValues.ML_GetHousingBuildings)) {
            setMaterialSpinnerType();
            setMaterialSpinnerUses();
            return;
        }

        if (action.equals(StaticValues.ML_GetUserAddress)) {
            AddressId = Integer.valueOf(vm_packageRequestAddress.getAddressId());
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        LayoutChoose.setOnClickListener(v -> {
            textChoose.setVisibility(View.GONE);
            MarkerGif.setVisibility(View.VISIBLE);
            LocationAddress = mMap.getCameraPosition().target;
            vm_packageRequestAddress.getAddress(LocationAddress.latitude, LocationAddress.longitude);
        });


        imgFullScreen.setOnClickListener(v -> {
            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            if (FullScreen) {
                childParam1.weight = 1;
                scrollView.setLayoutParams(childParam1);
                FullScreen = false;
            } else {
                childParam1.weight = 0.025f;
                scrollView.setLayoutParams(childParam1);
                FullScreen = true;
            }
        });


        RelativeLayoutSave.setOnClickListener(v -> {
            hideKeyboard();
            if (checkEmpty()) {
                showLoading();
                vm_packageRequestAddress.saveAddress(
                        EditTextAddress.getText().toString(),
                        LocationAddress.latitude,
                        LocationAddress.longitude,
                        BuildingTypeId,
                        Integer.valueOf(EditTextUnitCount.getText().toString()),
                        BuildingUseId,
                        Integer.valueOf(EditTextPersonCount.getText().toString()),
                        EditTextPlateNumber.getText().toString(),
                        EditTextUnitNumber.getText().toString(),
                        AddressId
                );
            }
        });


        MaterialSpinnerUses.setOnItemSelectedListener((view, position, id, item) -> {
            if (BuildingUseId == -1) {
                BuildingUseId = Long.valueOf(vm_packageRequestAddress.getBuildingTypes().getBuildingUses().get(position - 1).getId());
                MaterialSpinnerUses.getItems().remove(0);
                MaterialSpinnerUses.setSelectedIndex(MaterialSpinnerUses.getItems().size() - 1);
                MaterialSpinnerUses.setSelectedIndex(position - 1);
            } else
                BuildingUseId = Long.valueOf(vm_packageRequestAddress.getBuildingTypes().getBuildingUses().get(position).getId());

            if (getContext() != null) {
                MaterialSpinnerUses.setBackgroundColor(getContext().getResources().getColor(R.color.mlWhite));
            }
        });

        MaterialSpinnerType.setOnItemSelectedListener((view, position, id, item) -> {
            if (BuildingTypeId == -1) {
                BuildingTypeId = Long.valueOf(vm_packageRequestAddress.getBuildingTypes().getBuildingTypes().get(position - 1).getId());
                MaterialSpinnerType.getItems().remove(0);
                MaterialSpinnerType.setSelectedIndex(MaterialSpinnerType.getItems().size() - 1);
                MaterialSpinnerType.setSelectedIndex(position - 1);
            } else
                BuildingTypeId = Long.valueOf(vm_packageRequestAddress.getBuildingTypes().getBuildingTypes().get(position).getId());

            if (getContext() != null) {
                MaterialSpinnerType.setBackgroundColor(getContext().getResources().getColor(R.color.mlWhite));
            }
        });

    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ getCurrentLocation
    private void getCurrentLocation() {

        textChoose.setVisibility(View.GONE);
        MarkerGif.setVisibility(View.VISIBLE);

        if (getContext() == null)
            return;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            TryToLocation++;
            if (TryToLocation > 3) {
                textChoose.setVisibility(View.VISIBLE);
                MarkerGif.setVisibility(View.GONE);
                LatLng current = new LatLng(35.831350, 50.998434);
                float zoom = (float) 16;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));

                showMessageDialog(
                        getResources().getString(R.string.NotFoundLocation)
                        , getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlBlack));


                return;
            }

            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            listener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, (LocationListener) listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                locationManager.removeUpdates(listener);
                if (GPSLocation != null) {
                    getTrueLocationAndMove(GPSLocation);
                } else if (NetworkLocation != null) {
                    getTrueLocationAndMove(NetworkLocation);
                } else {
                    getCurrentLocation();
                }
            }, 5 * 1000);
        }
    }
    //______________________________________________________________________________________________ getCurrentLocation


    //______________________________________________________________________________________________ onMapReady
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        markerInfo.setVisibility(View.GONE);

        mMap.setOnMapLoadedCallback(() -> {
            LinearLayoutWaitMap.setVisibility(View.GONE);
            if (!StaticFunctions.isLocationEnabled(getContext())) {
                showRequestTypeDialog();
            } else {
                TryToLocation = 0;
                getCurrentLocation();
            }
        });

        mMap.setOnCameraMoveStartedListener(i -> {
            textChoose.setVisibility(View.GONE);
            MarkerGif.setVisibility(View.VISIBLE);
            if (markerInfo.getVisibility() == View.VISIBLE) {
                setAnimationTopToBottom();
                markerInfo.setVisibility(View.GONE);
            }
        });

        mMap.setOnCameraIdleListener(() -> {
            textChoose.setVisibility(View.VISIBLE);
            MarkerGif.setVisibility(View.GONE);
        });


    }
    //______________________________________________________________________________________________ onMapReady


    //______________________________________________________________________________________________ setAnimationTopToBottom
    private void setAnimationTopToBottom() {
        markerInfo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
    }
    //______________________________________________________________________________________________ setAnimationTopToBottom


    //______________________________________________________________________________________________ showRequestTypeDialog
    private void showRequestTypeDialog() {
        TryToLocation = -1;
        if (dialogQuestion != null)
            if (dialogQuestion.isShowing())
                dialogQuestion.dismiss();
        dialogQuestion = null;
        if (getContext() != null) {
            dialogQuestion = new Dialog(getContext());
        }
        dialogQuestion.setCancelable(false);
        dialogQuestion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogQuestion.setContentView(R.layout.dialog_question);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogQuestion.getWindow();
        if (window != null) {
            lp.copyFrom(window.getAttributes());
        }
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (window != null) {
            window.setAttributes(lp);
        }

        TextView TextViewQuestionTitle = dialogQuestion
                .findViewById(R.id.TextViewQuestionTitle);

        TextView TextViewYes = dialogQuestion
                .findViewById(R.id.TextViewYes);

        TextView TextViewNo = dialogQuestion
                .findViewById(R.id.TextViewNo);

        ImageView ImageViewYes = dialogQuestion
                .findViewById(R.id.ImageViewYes);

        ImageView ImageViewNo = dialogQuestion
                .findViewById(R.id.ImageViewNo);

        if (getContext() != null) {
            TextViewQuestionTitle.setText(getContext().getResources().getString(R.string.TurnOnGps));
            TextViewYes.setText(getContext().getResources().getString(R.string.ML_TurnOnLocation));
            TextViewNo.setText(getContext().getResources().getString(R.string.ML_TurnOffLocation));
            ImageViewYes.setImageDrawable(getContext().getResources().getDrawable(R.drawable.svg_pin));
            ImageViewNo.setImageDrawable(getContext().getResources().getDrawable(R.drawable.svg_disconnected));
        }


        LinearLayout LinearLayoutYes = dialogQuestion
                .findViewById(R.id.LinearLayoutYes);

        LinearLayout LinearLayoutNo = dialogQuestion
                .findViewById(R.id.LinearLayoutNo);

        LinearLayoutNo.setOnClickListener(v -> {
            textChoose.setVisibility(View.VISIBLE);
            MarkerGif.setVisibility(View.GONE);
            LatLng current = new LatLng(35.831350, 50.998434);
            float zoom = (float) 16;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
            dialogQuestion.dismiss();
        });

        LinearLayoutYes.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            dialogQuestion.dismiss();
        });

        dialogQuestion.show();
    }
    //______________________________________________________________________________________________ showRequestTypeDialog


    //______________________________________________________________________________________________ getTrueLocationAndMove
    private void getTrueLocationAndMove(Location location) {
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        float zoom = (float) 16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
    }
    //______________________________________________________________________________________________ getTrueLocationAndMove


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        EditTextAddress.setBackgroundResource(R.color.mlEdit);
        EditTextAddress.addTextChangedListener(TextChangeForChangeBack(EditTextAddress));
        EditTextPersonCount.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        EditTextPersonCount.addTextChangedListener(TextChangeForChangeBack(EditTextPersonCount));
        EditTextUnitCount.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        EditTextUnitCount.addTextChangedListener(TextChangeForChangeBack(EditTextUnitCount));
        EditTextUnitNumber.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        EditTextUnitNumber.addTextChangedListener(TextChangeForChangeBack(EditTextUnitNumber));
        EditTextPlateNumber.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        EditTextPlateNumber.addTextChangedListener(TextChangeForChangeBack(EditTextPlateNumber));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ setMaterialSpinnerUses
    private void setMaterialSpinnerUses() {
        List<String> buildingUses = new ArrayList<>();
        buildingUses.add("کاربری ساختمان");
        for (MD_SpinnerItem item : vm_packageRequestAddress.getBuildingTypes().getBuildingUses())
            buildingUses.add(item.getTitle());
        MaterialSpinnerUses.setItems(buildingUses);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerUses


    //______________________________________________________________________________________________ setMaterialSpinnerType
    private void setMaterialSpinnerType() {
        if (progress != null)
            progress.dismiss();
        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add("نوع واحد");
        for (MD_SpinnerItem item : vm_packageRequestAddress.getBuildingTypes().getBuildingTypes())
            buildingTypes.add(item.getTitle());
        MaterialSpinnerType.setItems(buildingTypes);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerType


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean address;
        boolean personcount;
        boolean unitcount;
        boolean spinneruser;
        boolean spinnertype;
        boolean plate;
        boolean unit;


        if (EditTextUnitNumber.getText().length() < 1) {
            EditTextUnitNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextUnitNumber.setError(getResources().getString(R.string.EmptyUnitNumber));
            EditTextUnitNumber.requestFocus();
            unit = false;
        } else
            unit = true;

        if (EditTextPlateNumber.getText().length() < 1) {
            EditTextPlateNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextPlateNumber.setError(getResources().getString(R.string.EmptyPlateNumber));
            EditTextPlateNumber.requestFocus();
            plate = false;
        } else
            plate = true;


        if (BuildingTypeId == -1) {
            MaterialSpinnerType.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerType.requestFocus();
            spinnertype = false;
        } else
            spinnertype = true;

        if (BuildingUseId == -1) {
            MaterialSpinnerUses.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerUses.requestFocus();
            spinneruser = false;
        } else
            spinneruser = true;

        if (EditTextUnitCount.getText().length() < 1) {
            EditTextUnitCount.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextUnitCount.setError(getResources().getString(R.string.EmptyUnitCount));
            EditTextUnitCount.requestFocus();
            unitcount = false;
        } else
            unitcount = true;

        if (EditTextPersonCount.getText().length() < 1) {
            EditTextPersonCount.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextPersonCount.setError(getResources().getString(R.string.EmptyPersonCount));
            EditTextPersonCount.requestFocus();
            personcount = false;
        } else
            personcount = true;


        if (EditTextAddress.getText().length() < 1 || LocationAddress == null) {
            EditTextAddress.setBackgroundResource(R.drawable.edit_empty_background);
            EditTextAddress.setError(getResources().getString(R.string.EmptyAddress));
            EditTextAddress.requestFocus();
            address = false;
        } else
            address = true;


        return address && personcount && unitcount && spinneruser && spinnertype && unit && plate;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        StaticFunctions.isCancel = true;
        txtLoading.setText(getResources().getString(R.string.FragmentPackRequestAddress));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);

        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);

    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        StaticFunctions.isCancel = false;
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


    //______________________________________________________________________________________________ showProgressDialog
    private void showProgressDialog() {

        if (getContext() != null) {
            progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .showProgress(getContext(), null);
            progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }
    }
    //______________________________________________________________________________________________ showProgressDialog


    //______________________________________________________________________________________________ MyLocationListener
    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(final Location loc) {

            if (isBetterLocation(loc, previousBestLocation)) {
                if (loc.getProvider().equalsIgnoreCase("gps"))
                    GPSLocation = loc;
                else
                    NetworkLocation = loc;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderDisabled(String provider) {

        }


        public void onProviderEnabled(String provider) {

        }
    }
    //______________________________________________________________________________________________ MyLocationListener


    //______________________________________________________________________________________________ isBetterLocation
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        int TWO_MINUTES = 1000 * 60 * 2;
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }
    //______________________________________________________________________________________________ isBetterLocation


    //______________________________________________________________________________________________ isSameProvider
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    //______________________________________________________________________________________________ isSameProvider


}
