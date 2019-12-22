package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Marker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentPackRequestAddress extends Fragment implements OnMapReadyCallback {

    private Context context;
    private FragmentPackRequestAddressViewModel fragmentPackRequestAddressViewModel;
    private GoogleMap mMap;
    private boolean FullScreen = false;
    private StringBuilder mResult;
    private PublishSubject<String> Observables = null;
    private MehrdadLatifiMap mehrdadLatifiMap = new MehrdadLatifiMap();


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

    @BindView(R.id.markerInfo)
    LinearLayout markerInfo;

    @BindView(R.id.wazeLayout)
    LinearLayout wazeLayout;

    Marker m1;
    Marker m2;

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
        Observables = PublishSubject.create();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fpraMap);
        mapFragment.getMapAsync(this);

        SetMaterialSpinnersItems();
        SetOnClick();
        FullScreen = false;
        textChoose.setVisibility(View.VISIBLE);
        MarkerGif.setVisibility(View.GONE);
        ObserverObservables();

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
        mMap.getUiSettings().setMapToolbarEnabled(false);
        markerInfo.setVisibility(View.GONE);


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


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                SetAnimationBottomToTop();
                markerInfo.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (markerInfo.getVisibility() == View.VISIBLE) {
                    SetAnimationTopToBottom();
                    markerInfo.setVisibility(View.GONE);
                }
            }
        });

        DrawPolyline();
    }//_____________________________________________________________________________________________ End Void onMapReady


    private void SetOnClick() {//___________________________________________________________________ Start SetOnClick

        LayoutChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChoose.setVisibility(View.GONE);
                MarkerGif.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Observables.onNext("TextAddress");
                    }
                }, 1000);


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


        wazeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String uri = "waze://?ll=35.838930, 51.014575&navigate=yes";
//                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });

    }//_____________________________________________________________________________________________ End SetOnClick


    private void DrawPolyline() {
        mehrdadLatifiMap.setGoogleMap(mMap);
        LatLng negra = new LatLng(35.831414, 50.959419);
        m1 = mehrdadLatifiMap.AddMarker(negra, null, "0", R.drawable.ic_check);


        negra = new LatLng(35.831414, 50.969419);
        m2 = mehrdadLatifiMap.AddMarker(negra, "Negra2", "1", R.drawable.ic_check);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(negra, 13));

//        List<LatLng> latLngs = new ArrayList<>();
//        String json = "[[50.9983097,35.8313515],[50.9985491,35.831239],[50.9987017,35.8311867],[50.9996985,35.8310453],[51.0015582,35.8309464],[51.0021977,35.8309355],[51.0027549,35.8309489],[51.0050454,35.8308562],[51.0080309,35.8307547],[51.0081905,35.8292293],[51.0082644,35.8291793],[51.0082772,35.828537],[51.0083355,35.8283533],[51.0084536,35.8282969],[51.0107476,35.8282431],[51.0124799,35.8281881],[51.0132275,35.8281854],[51.0143343,35.8281813],[51.0150261,35.8281766],[51.0152658,35.8281675],[51.0178447,35.8280693],[51.0194079,35.8280096],[51.0192486,35.8259208],[51.0200085,35.8260055],[51.0207817,35.8267213],[51.0207629,35.8269947],[51.0210863,35.8272432],[51.021361,35.827278],[51.0226699,35.8281305],[51.0237214,35.8286524],[51.0249444,35.8288786],[51.025953,35.8290178],[51.0269193,35.8291523],[51.0290079,35.8301198],[51.0312824,35.8306938],[51.0326922,35.8309055],[51.0327573,35.8324122],[51.0327092,35.8329466],[51.0323198,35.8332278],[51.0316474,35.8334803],[51.0310875,35.8334788],[51.0311554,35.834033],[51.0308121,35.8359466],[51.0300729,35.838509],[51.0293141,35.8402818],[51.0279135,35.8413692],[51.0248002,35.8426872],[51.0222757,35.8434183],[51.0194276,35.8444216],[51.0189108,35.8447545],[51.0187593,35.845079],[51.0197193,35.8462434],[51.0196447,35.846723],[51.0188081,35.8476081],[51.0175834,35.8491472],[51.0165401,35.8502976],[51.0119393,35.847193],[51.0075603,35.8469112],[51.0074384,35.8457755],[51.0073646,35.8453486],[51.0062469,35.8438203],[51.0039567,35.8406629],[51.0029268,35.8392689],[51.0028511,35.8391664],[51.0017534,35.8385798],[51.0007241,35.8381164],[51.0014633,35.8371517],[51.0017481,35.8368436],[51.0024841,35.8361429],[51.0025572,35.8360727],[51.0019056,35.8353943],[51.0005213,35.8338243],[51.0002582,35.8335325],[50.999926,35.8331188],[50.9990536,35.8321167],[50.998794,35.8318491],[50.9983097,35.8313515]]";
//        try {
//            JSONArray jsonArr = new JSONArray(json);
//            for(int i = 0; i < jsonArr.length(); i++){
//                //JSONObject object = jsonArr.getJSONObject(i);
//                JSONArray j = jsonArr.getJSONArray(i);
//                latLngs.add(new LatLng(j.getDouble(0),j.getDouble(1)));
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        mehrdadLatifiMap.showCurrentPlace(context);
//        mehrdadLatifiMap.setML_LatLongs(latLngs);
//        mehrdadLatifiMap.setML_Stroke_Width(2.0f);
//        mehrdadLatifiMap.setML_Fill_Color(getResources().getColor(R.color.mlPolyline));
//        mehrdadLatifiMap.setML_Stroke_Color(getResources().getColor(R.color.colorAccent));

//        mehrdadLatifiMap.DrawPolygon(false);
//        mehrdadLatifiMap.AutoZoom();

//        Observables.onNext("LatLongAddress");
//        mehrdadLatifiMap.DrawPolygon(false);

//        mehrdadLatifiMap.DrawCircle(negra, 4000);


//        negra = new LatLng(35.831414, 50.969419);
//        mehrdadLatifiMap.AddMarker(negra,"Negra","0",R.drawable.ic_check);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(negra, 13));
    }


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        Observables
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (s) {
                                    case "TextAddress":
                                        String LongAddress = "";
                                        LatLng negra = mMap.getCameraPosition().target;
                                        try {
                                            Geocoder geocoder;
                                            List<Address> addresses;
                                            Locale locale = new Locale("fa_IR");
                                            Locale.setDefault(locale);
                                            geocoder = new Geocoder(context, locale);
                                            addresses = geocoder.getFromLocation(negra.latitude, negra.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                                            if (addresses.size() == 0) {
                                                textChoose.setText(LongAddress);
                                                textChoose.setVisibility(View.VISIBLE);
                                                MarkerGif.setVisibility(View.GONE);
                                                return;
                                            }
                                            Address LongPosition = addresses.get(0);
                                            for (Address longAddress : addresses) {
                                                String ad = longAddress.getAddressLine(0);
                                                if (ad.length() > LongAddress.length()) {
                                                    LongAddress = ad;
                                                    LongPosition = longAddress;
                                                }
                                            }
                                            //String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                            String city = LongPosition.getLocality();
                                            String state = LongPosition.getAdminArea();
                                            String country = LongPosition.getCountryName();
                                            String SubLocality = LongPosition.getSubLocality();
                                            String knownName = LongPosition.getFeatureName();
                                            String thoroughfare = LongPosition.getThoroughfare();
                                            String Sunthoroughfare = LongPosition.getSubThoroughfare();
                                            StringBuilder address = new StringBuilder();
                                            address.append("");
                                            if ((country != null) && (!country.equalsIgnoreCase("null"))) {
                                                address.append(country);
                                                address.append(" ");
                                            }

                                            if ((state != null) && (!state.equalsIgnoreCase("null"))) {
                                                address.append(state);
                                                address.append(" ");
                                            }

                                            if ((city != null) && (!city.equalsIgnoreCase("null"))) {
                                                address.append(city);
                                                address.append(" ");
                                            }

                                            if ((thoroughfare != null) && (!thoroughfare.equalsIgnoreCase("null"))) {
                                                address.append(thoroughfare);
                                                address.append(" ");
                                            }

                                            if ((Sunthoroughfare != null) && (!Sunthoroughfare.equalsIgnoreCase("null"))) {
                                                address.append(Sunthoroughfare);
                                                address.append(" ");
                                            }

                                            if ((SubLocality != null) && (!SubLocality.equalsIgnoreCase("null"))) {
                                                address.append(SubLocality);
                                                address.append(" ");
                                            }
                                            if ((knownName != null) &&
                                                    (!knownName.equalsIgnoreCase("null"))
                                                    && (!knownName.equalsIgnoreCase(thoroughfare)))
                                                address.append(knownName);
                                            fpraEditAddress.setText(address);
                                            textChoose.setVisibility(View.VISIBLE);
                                            MarkerGif.setVisibility(View.GONE);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            textChoose.setText(LongAddress);
                                            textChoose.setVisibility(View.VISIBLE);
                                            MarkerGif.setVisibility(View.GONE);
                                        }
                                        break;
                                    case "LatLongAddress":
                                        mehrdadLatifiMap.findAddress(context, "عظیمیه", Observables);
                                        break;
                                    case "FindAddress":
                                        mehrdadLatifiMap.AddMarker(
                                                mehrdadLatifiMap.getML_FindAddress()
                                                , "address"
                                                , "find"
                                                ,
                                                R.drawable.ic_check
                                        );
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mehrdadLatifiMap.getML_FindAddress(), 14));
                                        break;
                                }

                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


    private void SetAnimationBottomToTop() {//______________________________________________________ Start SetAnimationBottomToTop
        markerInfo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom));
    }//_____________________________________________________________________________________________ End SetAnimationBottomToTop


    private void SetAnimationTopToBottom() {//______________________________________________________ Start SetAnimationTopToBottom
        markerInfo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom));
    }//_____________________________________________________________________________________________ End SetAnimationTopToBottom

}
