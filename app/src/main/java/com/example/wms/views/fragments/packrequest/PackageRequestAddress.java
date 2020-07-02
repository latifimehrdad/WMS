package com.example.wms.views.fragments.packrequest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestAddressBinding;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.packrequest.VM_PackageRequestAddress;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.user.login.Login;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class PackageRequestAddress extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable, OnMapReadyCallback {


    private NavController navController;
    private VM_PackageRequestAddress vm_packageRequestAddress;
    private boolean FullScreen = false;
    private Integer TryToLocation = 0;
    private GoogleMap mMap;
    private LocationManager locationManager;
    public PackageRequestAddress.MyLocationListener listener;
    private Location GPSLocation = null;
    private Location NetworkLocation = null;
    private boolean getLocation = false;
    private LatLng LocationAddress;
    private Long BuildingTypeId;
    private Long BuildingUseId;
    private DialogProgress progress;
    private Dialog dialogQuestion;
    private final int TWO_MINUTES = 1000 * 60 * 2;
    private Location previousBestLocation = null;

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

    @BindView(R.id.markerInfo)
    LinearLayout markerInfo;

    @BindView(R.id.RelativeLayoutSave)
    RelativeLayout RelativeLayoutSave;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.TextViewWaitMap)
    TextView TextViewWaitMap;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_packageRequestAddress = new VM_PackageRequestAddress(getContext());
            FragmentPackRequestAddressBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request_address, container, false);
            binding.setVmRequestAddress(vm_packageRequestAddress);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(getView());
        setGetMessageFromObservable(
                PackageRequestAddress.this,
                vm_packageRequestAddress.getPublishSubject(),
                vm_packageRequestAddress);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);
        TextViewWaitMap.setVisibility(View.VISIBLE);
        FullScreen = false;
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        if (TryToLocation == -1)
            GetCurrentLocation();

    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

        SetTextWatcher();
        SetOnClick();
        DismissLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vm_packageRequestAddress.GetTypeBuilding();
            }
        }, 100);


    }//_____________________________________________________________________________________________ init


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        setAccessClick(true);

        DismissLoading();
        if (action == StaticValues.ML_GetAddress) {
            vm_packageRequestAddress.SetAddress();
            return;
        }

        if (action == StaticValues.ML_SetAddress) {
            EditTextAddress.setText(vm_packageRequestAddress.getAddressString());
            return;
        }

        if (action == StaticValues.ML_EditUserAddress) {
            navController.navigate(R.id.action_packageRequestAddress_to_packageRequestPrimary);
            return;
        }

        if (action == StaticValues.ML_GetHousingBuildings) {
            SetMaterialSpinnerType();
            return;
        }

        if (action == StaticValues.ML_SetAddress) {
            EditTextAddress.setText(vm_packageRequestAddress.getAddressString());
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        LayoutChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
                LocationAddress = mMap.getCameraPosition().target;
                vm_packageRequestAddress.GetAddress(LocationAddress.latitude, LocationAddress.longitude);
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
                    childParam1.weight = 0.025f;
                    scrollView.setLayoutParams(childParam1);
                    FullScreen = true;
                }
            }
        });


        RelativeLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAccessClick()) {
                    if (CheckEmpty()) {
                        ShowLoading();
                        vm_packageRequestAddress.SaveAddress(
                                EditTextAddress.getText().toString(),
                                LocationAddress.latitude,
                                LocationAddress.longitude,
                                BuildingTypeId,
                                Integer.valueOf(EditTextUnitCount.getText().toString()),
                                BuildingUseId,
                                Integer.valueOf(EditTextPersonCount.getText().toString())
                        );
                    }
                } else
                    vm_packageRequestAddress.CancelRequest();
            }
        });


        MaterialSpinnerUses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vm_packageRequestAddress.getBuildingTypes() == null) {
                    ShowProgressDialog();
                    vm_packageRequestAddress.GetTypeBuilding();
                } else
                    SetMaterialSpinnerUses();
            }
        });

        MaterialSpinnerUses.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                BuildingUseId = Long.valueOf(vm_packageRequestAddress.getBuildingTypes().getBuildingUses().get(position - 1).getId());
                MaterialSpinnerUses.setBackgroundColor(getContext().getResources().getColor(R.color.mlEdit));
            }
        });

        MaterialSpinnerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vm_packageRequestAddress.getBuildingTypes() == null) {
                    ShowProgressDialog();
                    vm_packageRequestAddress.GetTypeBuilding();
                } else
                    SetMaterialSpinnerType();
            }
        });

        MaterialSpinnerType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                BuildingTypeId = Long.valueOf(vm_packageRequestAddress.getBuildingTypes().getBuildingTypes().get(position - 1).getId());
                MaterialSpinnerType.setBackgroundColor(getContext().getResources().getColor(R.color.mlEdit));
            }
        });

    }//_____________________________________________________________________________________________ SetOnClick


    private void GetCurrentLocation() {//___________________________________________________________ Start GetCurrentLocation

        textChoose.setVisibility(View.GONE);
        MarkerGif.setVisibility(View.VISIBLE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

        } else {
            TryToLocation++;
            if (TryToLocation > 3) {
                textChoose.setVisibility(View.VISIBLE);
                MarkerGif.setVisibility(View.GONE);
                LatLng current = new LatLng(35.831350, 50.998434);
                float zoom = (float) 16;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));

                ShowMessage(
                        getResources().getString(R.string.NotFoundLocation)
                        , getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlBlack));


                return;
            }

            locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
            listener = new PackageRequestAddress.MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, (LocationListener) listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    locationManager.removeUpdates(listener);
                    if (GPSLocation != null) {
                        GetTrueLocationAndMove(GPSLocation);
                    } else if (NetworkLocation != null) {
                        GetTrueLocationAndMove(NetworkLocation);
                    } else {
                        GetCurrentLocation();
                    }
                }
            }, 5 * 1000);
        }
    }//_____________________________________________________________________________________________ End GetCurrentLocation



    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {//_________________________________________________ Void onMapReady
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        markerInfo.setVisibility(View.GONE);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                TextViewWaitMap.setVisibility(View.GONE);
                if (!StaticFunctions.isLocationEnabled(getContext())) {
                    ShowRequestTypeDialog();
                } else {
                    TryToLocation = 0;
                    GetCurrentLocation();
                }
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
                if (markerInfo.getVisibility() == View.VISIBLE) {
                    SetAnimationTopToBottom();
                    markerInfo.setVisibility(View.GONE);
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                textChoose.setVisibility(View.VISIBLE);
                MarkerGif.setVisibility(View.GONE);
            }
        });


    }//_____________________________________________________________________________________________ onMapReady



    private void SetAnimationTopToBottom() {//______________________________________________________ SetAnimationTopToBottom
        markerInfo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
    }//_____________________________________________________________________________________________ SetAnimationTopToBottom



    private void ShowRequestTypeDialog() {//________________________________________________________ ShowRequestTypeDialog
        TryToLocation = -1;
        if (dialogQuestion != null)
            if (dialogQuestion.isShowing())
                dialogQuestion.dismiss();
        dialogQuestion = null;
        dialogQuestion = new Dialog(getContext());
        dialogQuestion.setCancelable(false);
        dialogQuestion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogQuestion.setContentView(R.layout.dialog_question);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogQuestion.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        TextView TextViewQuestionTitle = (TextView) dialogQuestion
                .findViewById(R.id.TextViewQuestionTitle);
        TextViewQuestionTitle.setText(getContext().getResources().getString(R.string.TurnOnGps));

        TextView TextViewYes = (TextView) dialogQuestion
                .findViewById(R.id.TextViewYes);
        TextViewYes.setText(getContext().getResources().getString(R.string.ML_TurnOnLocation));

        TextView TextViewNo = (TextView) dialogQuestion
                .findViewById(R.id.TextViewNo);
        TextViewNo.setText(getContext().getResources().getString(R.string.ML_TurnOffLocation));

        ImageView ImageViewYes = (ImageView) dialogQuestion
                .findViewById(R.id.ImageViewYes);

        ImageViewYes.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_baseline_my_location));


        LinearLayout LinearLayoutYes = (LinearLayout) dialogQuestion
                .findViewById(R.id.LinearLayoutYes);

        LinearLayout LinearLayoutNo = (LinearLayout) dialogQuestion
                .findViewById(R.id.LinearLayoutNo);

        LinearLayoutNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChoose.setVisibility(View.VISIBLE);
                MarkerGif.setVisibility(View.GONE);
                LatLng current = new LatLng(35.831350, 50.998434);
                float zoom = (float) 16;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
                dialogQuestion.dismiss();
            }
        });

        LinearLayoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialogQuestion.dismiss();
            }
        });

        dialogQuestion.show();
    }//_____________________________________________________________________________________________ ShowRequestTypeDialog



    private void GetTrueLocationAndMove(Location location) {//______________________________________ Start GetTrueLocationAndMove
        getLocation = true;
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        float zoom = (float) 16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoom));
    }//_____________________________________________________________________________________________ End GetTrueLocationAndMove


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        EditTextAddress.setBackgroundResource(R.color.mlEdit);
        EditTextAddress.addTextChangedListener(TextChangeForChangeBack(EditTextAddress));
        EditTextPersonCount.setBackgroundResource(R.color.mlEdit);
        EditTextPersonCount.addTextChangedListener(TextChangeForChangeBack(EditTextPersonCount));
        EditTextUnitCount.setBackgroundResource(R.color.mlEdit);
        EditTextUnitCount.addTextChangedListener(TextChangeForChangeBack(EditTextUnitCount));
    }//_____________________________________________________________________________________________ SetTextWatcher


    private void SetMaterialSpinnerUses() {//_______________________________________________________ SetMaterialSpinnerUses
        List<String> buildingUses = new ArrayList<>();
        buildingUses.add("کاربری ساختمان");
        for (ModelSpinnerItem item : vm_packageRequestAddress.getBuildingTypes().getBuildingUses())
            buildingUses.add(item.getTitle());
        MaterialSpinnerUses.setItems(buildingUses);
    }//_____________________________________________________________________________________________ SetMaterialSpinnerUses



    private void SetMaterialSpinnerType() {//_______________________________________________________ SetMaterialSpinnerType
        if (progress != null)
            progress.dismiss();
        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add("نوع واحد");
        for (ModelSpinnerItem item : vm_packageRequestAddress.getBuildingTypes().getBuildingTypes())
            buildingTypes.add(item.getTitle());
        MaterialSpinnerType.setItems(buildingTypes);
        SetMaterialSpinnerUses();
    }//_____________________________________________________________________________________________ SetMaterialSpinnerType



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean address = false;
        boolean personcount = false;
        boolean unitcount = false;
        boolean spinneruser = false;
        boolean spinnertype = false;


        if (MaterialSpinnerType.getSelectedIndex() == 0) {
            MaterialSpinnerType.setBackgroundColor(getContext().getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerType.requestFocus();
            spinnertype = false;
        } else
            spinnertype = true;

        if (MaterialSpinnerUses.getSelectedIndex() == 0) {
            MaterialSpinnerUses.setBackgroundColor(getContext().getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerUses.requestFocus();
            spinneruser = false;
        } else
            spinneruser = true;

        if (EditTextUnitCount.getText().length() < 1) {
            EditTextUnitCount.setBackgroundResource(R.drawable.edit_empty_background);
            EditTextUnitCount.setError(getResources().getString(R.string.EmptyUnitCount));
            EditTextUnitCount.requestFocus();
            unitcount = false;
        } else
            unitcount = true;

        if (EditTextPersonCount.getText().length() < 1) {
            EditTextPersonCount.setBackgroundResource(R.drawable.edit_empty_background);
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


        if (address && personcount && unitcount && spinneruser && spinnertype)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void DismissLoading() {//_______________________________________________________________ DismissLoading
        StaticFunctions.isCancel = true;
        txtLoading.setText(getResources().getString(R.string.Save));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);

        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);

    }//_____________________________________________________________________________________________ DismissLoading


    private void ShowLoading() {//__________________________________________________________________ ShowLoading
        StaticFunctions.isCancel = false;
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSave.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ ShowLoading


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(getContext(), null);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog



    public class MyLocationListener implements LocationListener {//_________________________________ MyLocationListener

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
    }//_____________________________________________________________________________________________ MyLocationListener




    protected boolean isBetterLocation(Location location, Location currentBestLocation) {//_________ isBetterLocation
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
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
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }//_____________________________________________________________________________________________ isBetterLocation


    private boolean isSameProvider(String provider1, String provider2) {//__________________________ isSameProvider
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }//_____________________________________________________________________________________________ isSameProvider



}
