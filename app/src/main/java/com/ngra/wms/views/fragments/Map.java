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
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrMapBinding;
import com.ngra.wms.models.MD_AdapterSuggestion;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MD_SuggestionAddress;
import com.ngra.wms.utility.ML_Map;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Map;
import com.ngra.wms.views.adaptors.AP_Suggestion;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Map extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable,
        OnMapReadyCallback,
        AP_Suggestion.ItemAddressClick {


    private NavController navController;
    private VM_Map vm_map;
    private CompositeDisposable compositeDisposable;
    private Integer errorCount = 0;
    private GoogleMap mMap;
    private LocationManager locationManager;
    public Map.MyLocationListener listener;
    private Location GPSLocation = null;
    private Location NetworkLocation = null;
    private LatLng LocationAddress;
    private Integer tryToLocation = 0;
    private Location previousBestLocation = null;
    private Dialog dialogQuestion;
    private Integer positionChooseSuggestion;
    private boolean clickMarker = false;
    private boolean mapLoaded = false;
    private Handler timer;
    private Runnable runnable;
    private Integer boothPosition;
    private List<LatLng> latLngBooths;
    private Circle mapCircle;


    @BindView(R.id.EditTextDestination)
    EditText EditTextDestination;

    @BindView(R.id.ImageViewCloseSuggestion)
    ImageView ImageViewCloseSuggestion;

    @BindView(R.id.RecyclerViewSuggestion)
    RecyclerView RecyclerViewSuggestion;

    @BindView(R.id.GifViewDestination)
    GifView GifViewDestination;

    @BindView(R.id.LayoutChoose)
    RelativeLayout LayoutChoose;

    @BindView(R.id.textChoose)
    TextView textChoose;

    @BindView(R.id.MarkerGif)
    GifView MarkerGif;

    @BindView(R.id.markerInfo)
    LinearLayout markerInfo;

    @BindView(R.id.LinearLayoutChoose)
    LinearLayout LinearLayoutChoose;

    @BindView(R.id.LinearLayoutWaitMap)
    LinearLayout LinearLayoutWaitMap;

    @BindView(R.id.TextViewMapAddress)
    TextView TextViewMapAddress;

    @BindView(R.id.LinearLayoutChooseBooth)
    LinearLayout LinearLayoutChooseBooth;

    @BindView(R.id.imgFullScreen)
    ImageView imgFullScreen;


    //______________________________________________________________________________________________ Map
    public Map() {
    }
    //______________________________________________________________________________________________ Map


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FrMapBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_map, container, false
            );
            vm_map = new VM_Map(getContext());
            binding.setMap(vm_map);
            setView(binding.getRoot());
            init();
        } else {
            FrMapBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_map, container, false
            );
            vm_map = new VM_Map(getContext());
            binding.setMap(vm_map);
            setView(binding.getRoot());
            EditTextDestination.getText().clear();
            RecyclerViewSuggestion.setVisibility(View.GONE);
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Map.this,
                vm_map.getPublishSubject(),
                vm_map);

        imgFullScreen.setVisibility(View.GONE);

        if (getView() != null)
            navController = Navigation.findNavController(getView());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mapLoaded = false;
        LinearLayoutChoose.setVisibility(View.GONE);
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        if (tryToLocation == -1)
            getCurrentLocation();


    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {


        if (action.equals(StaticValues.ML_GetBoothListEmpty)) {
            mMap.clear();
            if (latLngBooths != null) {
                latLngBooths.clear();
                latLngBooths = null;
            }
            return;
        }

        if (action.equals(StaticValues.ML_ReTrySuggestion)) {
            errorCount++;
            GifViewDestination.setVisibility(View.VISIBLE);
            ImageViewCloseSuggestion.setVisibility(View.GONE);
            vm_map.getSuggestionAddress(
                    EditTextDestination.getText().toString(),
                    false,
                    errorCount);
            return;
        }

        if (action.equals(StaticValues.ML_NotFoundSuggestion)) {
            GifViewDestination.setVisibility(View.GONE);
            setSuggestionAdapter(false);
            return;
        }

        if (action.equals(StaticValues.ML_GetSuggestion)) {
            setSuggestionAdapter(true);
            return;
        }

        if (action.equals(StaticValues.ML_GetBoothList)) {
            setBoothOnMap();
            return;
        }

        if (action.equals(StaticValues.ML_GetAddress)) {
            getTrueLocationAndMove();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ init
    private void init() {

        RecyclerViewSuggestion.setVisibility(View.GONE);
        editTextDestinationChange();
        setOnClick();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        ImageViewCloseSuggestion.setOnClickListener(v -> RecyclerViewSuggestion.setVisibility(View.GONE));

        LayoutChoose.setOnClickListener(v -> {
            LocationAddress = mMap.getCameraPosition().target;
            Bundle bundle = new Bundle();
            bundle.putDouble(getContext().getResources().getString(R.string.ML_CurrentLat), LocationAddress.latitude);
            bundle.putDouble(getContext().getResources().getString(R.string.ML_CurrentLng), LocationAddress.longitude);
            navController.navigate(R.id.action_map_to_boothReceive2, bundle);

        });

        LinearLayoutChooseBooth.setOnClickListener(v -> {
            mMap.clear();
            latLngBooths.clear();
            Integer BoothId = vm_map.getMd_boothList().get(boothPosition).getId();
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetBooth);
            bundle.putInt(getContext().getString(R.string.ML_Id), BoothId);
            navController.navigate(R.id.action_map_to_timeSheet, bundle);
        });
    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ editTextDestinationChange
    private void editTextDestinationChange() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RxTextView.textChangeEvents(EditTextDestination)
                .skipInitialValue()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

    }
    //______________________________________________________________________________________________ editTextDestinationChange


    //______________________________________________________________________________________________ searchContactsTextWatcher
    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                if (EditTextDestination.getText().toString().length() == 0)
                    return;

                LinearLayoutChoose.setVisibility(View.GONE);
                GifViewDestination.setVisibility(View.VISIBLE);
                ImageViewCloseSuggestion.setVisibility(View.GONE);
                vm_map.getSuggestionAddress(
                        EditTextDestination.getText().toString(),
                        false,
                        errorCount);

                //publishSubject.onNext(textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }
    //______________________________________________________________________________________________ searchContactsTextWatcher


    //______________________________________________________________________________________________ getCurrentLocation
    private void getCurrentLocation() {

        textChoose.setVisibility(View.GONE);
        MarkerGif.setVisibility(View.VISIBLE);
        LinearLayoutWaitMap.setVisibility(View.VISIBLE);

        if (getContext() == null)
            return;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            tryToLocation++;
            if (tryToLocation > 3) {
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
            listener = new Map.MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, (LocationListener) listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                locationManager.removeUpdates(listener);
                if (GPSLocation != null) {
                    getTrueLocation(GPSLocation);
                } else if (NetworkLocation != null) {
                    getTrueLocation(NetworkLocation);
                } else {
                    getCurrentLocation();
                }
            }, 5 * 1000);
        }
    }
    //______________________________________________________________________________________________ getCurrentLocation


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


    //______________________________________________________________________________________________ setBoothOnMap
    private void setBoothOnMap() {

        LinearLayoutWaitMap.setVisibility(View.GONE);
        mMap.clear();
        LocationAddress = mMap.getCameraPosition().target;
        ML_Map ml_map = new ML_Map();
        latLngBooths = new ArrayList<>();
        ml_map.setGoogleMap(mMap);
        for (int i = 0; i < vm_map.getMd_boothList().size(); i++) {
            MD_Booth booth = vm_map.getMd_boothList().get(i);
            LatLng latLng = new LatLng(booth.getLocation().getLatitude(), booth.getLocation().getLongitude());
            latLngBooths.add(latLng);
            ml_map.AddMarker(latLng, booth.getName(), Integer.toString(i), R.drawable.booth_pin2);
        }
        latLngBooths.add(LocationAddress);
        ml_map.AddMarker(LocationAddress, "مرکز شعاع", "current", R.drawable.marker_point);
        ml_map.setML_LatLongs(latLngBooths);
        clickMarker = true;
        ml_map.setML_Stroke_Width(1.0f);
        ml_map.setML_Stroke_Color(getContext().getResources().getColor(R.color.Links));
        ml_map.setML_Fill_Color(getContext().getResources().getColor(R.color.CircleMap));
        double radiusCircle = 5000;
        mapCircle = ml_map.DrawCircle(LocationAddress, radiusCircle);
        ml_map.setML_LatLongs(ml_map.getCirclePoint(LocationAddress, radiusCircle));
        ml_map.AutoZoom();

    }
    //______________________________________________________________________________________________ setBoothOnMap


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
        mMap.setMaxZoomPreference(15);

        mMap.setOnMapLoadedCallback(() -> {
            mapLoaded = true;
            clickMarker = false;
            if (!StaticFunctions.isLocationEnabled(getContext())) {
                showRequestTypeDialog();
            } else {
                tryToLocation = 0;
                getCurrentLocation();
            }
        });


        mMap.setOnCameraMoveStartedListener(i -> {
            if (timer != null && runnable != null) {
                timer.removeCallbacks(runnable);
                timer = null;
                runnable = null;
            }

            if (markerInfo.getVisibility() == View.VISIBLE) {
                SetAnimationTopToBottom();
                markerInfo.setVisibility(View.GONE);
            }

        });


        mMap.setOnCameraIdleListener(() -> {

            LatLng center = mMap.getCameraPosition().target;

            if (!mapLoaded)
                return;

            if (clickMarker) {
                clickMarker = false;
                return;
            }

            if ((latLngBooths != null) && (latLngBooths.size() > 0)) {

                float[] distance = new float[2];

                Location.distanceBetween(center.latitude, center.longitude,
                        mapCircle.getCenter().latitude, mapCircle.getCenter().longitude, distance);

                if (distance[0] <= mapCircle.getRadius())
                    return;

            }

            timer = new Handler();
            runnable = () -> vm_map.getBoothList(center.latitude, center.longitude);
            timer.postDelayed(runnable, 500);

        });


        mMap.setOnMarkerClickListener(marker -> {
            clickMarker = true;
            String tag = marker.getTag().toString();
            if (!tag.equalsIgnoreCase("current")) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                    boothPosition = Integer.valueOf(tag);
                    TextViewMapAddress.setText(vm_map.getMd_boothList().get(boothPosition).getAddress());

                    SetAnimationBottomToTop();
                    markerInfo.setVisibility(View.VISIBLE);
                }, 250);

            }
            return false;
        });

    }
    //______________________________________________________________________________________________ onMapReady


    //______________________________________________________________________________________________ getTrueLocation
    private void getTrueLocation(Location location) {
        LocationAddress = new LatLng(location.getLatitude(), location.getLongitude());
        vm_map.getAddress(location.getLatitude(), location.getLongitude(), true, errorCount);

    }
    //______________________________________________________________________________________________ getTrueLocation


    //______________________________________________________________________________________________ getTrueLocationAndMove
    private void getTrueLocationAndMove() {
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        float zoom = (float) 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationAddress, zoom));
    }
    //______________________________________________________________________________________________ getTrueLocationAndMove


    private void SetAnimationTopToBottom() {//______________________________________________________ SetAnimationTopToBottom
        markerInfo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
    }//_____________________________________________________________________________________________ SetAnimationTopToBottom


    private void SetAnimationBottomToTop() {//______________________________________________________ SetAnimationBottomToTop
        markerInfo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
    }//_____________________________________________________________________________________________ SetAnimationBottomToTop


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


    //______________________________________________________________________________________________ setAnimationTopToBottom
    private void setAnimationTopToBottom() {
        markerInfo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
    }
    //______________________________________________________________________________________________ setAnimationTopToBottom


    //______________________________________________________________________________________________ showRequestTypeDialog
    private void showRequestTypeDialog() {
        tryToLocation = -1;
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


    //______________________________________________________________________________________________ setSuggestionAdapter
    private void setSuggestionAdapter(boolean LoadMore) {

        hideKeyboard();
        List<MD_AdapterSuggestion> list = new ArrayList<>();
        for (MD_SuggestionAddress address : vm_map.getSuggestionAddresses()) {
            String ad = totalAddress(address);
            list.add(new MD_AdapterSuggestion(ad, false));
        }

        if (positionChooseSuggestion == null)
            positionChooseSuggestion = -1;

        if (LoadMore) {
            String loadMore = getContext().getResources().getString(R.string.LoadMore);
            list.add(new MD_AdapterSuggestion(loadMore, true));
        } else
            positionChooseSuggestion--;

        AP_Suggestion ap_suggestion = new AP_Suggestion(list, getContext(), Map.this);
        RecyclerViewSuggestion.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewSuggestion.setAdapter(ap_suggestion);
        RecyclerViewSuggestion.setVisibility(View.VISIBLE);
        GifViewDestination.setVisibility(View.GONE);
        ImageViewCloseSuggestion.setVisibility(View.VISIBLE);
        if (positionChooseSuggestion < list.size())
            RecyclerViewSuggestion.scrollToPosition(positionChooseSuggestion - 1);
        RecyclerViewSuggestion.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ setSuggestionAdapter


    //______________________________________________________________________________________________ totalAddress
    private String totalAddress(MD_SuggestionAddress address) {
        String City = address.getAddress().getCity();
        String Neighbourhood = address.getAddress().getNeighbourhood();
        String Road = address.getAddress().getRoad();
        String district = address.getAddress().getDistrict();
        String suburb = address.getAddress().getSuburb();

        String ad = "";
        if (City != null && City.length() > 0 && !City.equalsIgnoreCase("null"))
            ad = City;
        else
            ad = district;

        if (suburb != null && suburb.length() > 0 && !suburb.equalsIgnoreCase("null"))
            ad = ad + " " + suburb;

        if (Neighbourhood != null && Neighbourhood.length() > 0 && !Neighbourhood.equalsIgnoreCase("null"))
            ad = ad + " " + Neighbourhood;

        if (Road != null && Road.length() > 0 && !Road.equalsIgnoreCase("null"))
            ad = ad + " " + Road;

        return ad;
    }
    //______________________________________________________________________________________________ totalAddress


    //______________________________________________________________________________________________ itemAddressClick
    @Override
    public void itemAddressClick(Integer position) {

        positionChooseSuggestion = position;
        LinearLayoutChoose.setVisibility(View.GONE);
        vm_map.getSuggestionAddress(
                EditTextDestination.getText().toString(),
                true,
                errorCount);

    }
    //______________________________________________________________________________________________ itemAddressClick


    //______________________________________________________________________________________________ itemAddressMapClick
    @Override
    public void itemAddressMapClick(Integer position) {

//        clickMarker = true;
        positionChooseSuggestion = position;
        Double lat = vm_map.getSuggestionAddresses().get(position).getLat();
        Double lng = vm_map.getSuggestionAddresses().get(position).getLon();
        LatLng current = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(current)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        hideKeyboard();
        RecyclerViewSuggestion.setVisibility(View.GONE);
        EditTextDestination.getText().clear();
        /*        LinearLayoutChoose.setVisibility(View.VISIBLE);*/

    }
    //______________________________________________________________________________________________ itemAddressMapClick


}
