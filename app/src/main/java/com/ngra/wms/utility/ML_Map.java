package com.ngra.wms.utility;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.ngra.wms.utility.polyutil.ML_PolyUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.subjects.PublishSubject;

public class ML_Map {

    private GoogleMap googleMap;
    private List<LatLng> ML_LatLongs;
    private int ML_Stroke_Color = 0;
    private Float ML_Stroke_Width = 3f;
    private int ML_PATTERN_DASH_LENGTH_PX = 0;
    private int ML_PATTERN_GAP_LENGTH_PX = 0;
    private int ML_PATTERN_DASH_LENGTH_Color = 0;
    private int ML_PATTERN_GAP_LENGTH_Color = 0;
    private int ML_Fill_Color = 0;
    private LatLng ML_FindAddress;


    public void DrawPolyLines() {//_________________________________________________________________ DrawPolyLines
        Polyline polyline = getGoogleMap().addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(getML_LatLongs()));
        stylePolyline(polyline);
    }//_____________________________________________________________________________________________ DrawPolyLines


    public void DrawPolygon(boolean Pattern) {//____________________________________________________ DrawPolygon
        Polygon polygon = getGoogleMap().addPolygon(new PolygonOptions()
                .clickable(true)
                .addAll(getML_LatLongs()));
        stylePolygon(polygon, Pattern);
    }//_____________________________________________________________________________________________ DrawPolygon


    private void stylePolyline(Polyline polyline) {//_______________________________________________ stylePolyline
        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        polyline.setWidth(getML_Stroke_Width());
        polyline.setColor(getML_Stroke_Color());
        polyline.setJointType(JointType.ROUND);

    }//_____________________________________________________________________________________________ stylePolyline


    private void stylePolygon(Polygon polygon, boolean Pattern) {//_________________________________ stylePolygon

        if (Pattern) {
            PatternItem DASH = new Dash(getML_PATTERN_DASH_LENGTH_PX());
            PatternItem GAP = new Gap(getML_PATTERN_GAP_LENGTH_PX());
            List<PatternItem> PATTERN_POLYGON = Arrays.asList(GAP, DASH);
            List<PatternItem> pattern = PATTERN_POLYGON;
            polygon.setStrokePattern(pattern);
        }
        polygon.setStrokeWidth(getML_Stroke_Width());
        polygon.setStrokeColor(getML_Stroke_Color());
        polygon.setFillColor(getML_Fill_Color());
    }//_____________________________________________________________________________________________ stylePolygon


    public Boolean isInside(LatLng point, List<LatLng> latLngs) {//_________________________________ Start isInside
        return
                ML_PolyUtil.containsLocation(point, latLngs, true);
    }//_____________________________________________________________________________________________ End isInside


    public Boolean isInside(LatLng point) {//_______________________________________________________ Start isInside
        return
                ML_PolyUtil.containsLocation(point, getML_LatLongs(), true);
    }//_____________________________________________________________________________________________ End isInside



    public static Boolean isInsidePath(LatLng point, List<LatLng> latLngs) {//____________________ Start isInside
/*        boolean in = ML_PolyUtil.containsLocation(point, latLngs, true);
        if(in)
            return true;
        else {*/
            return ML_PolyUtil.isLocationOnPath(point,latLngs,true, 15);
/*        }*/
        //containsLocation(point, latLngs, true);
    }//_____________________________________________________________________________________________ End isInside



//
//    public void getDeviceLocation(FragmentActivity context) {//_____________________________________ Start getDeviceLocation
//        try {
//            if (true) {
//                FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
//
//                Task locationResult = mFusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(context, new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            Object mLastKnownLocation = task.getResult();
//                            Log.i("meri", mLastKnownLocation.toString());
//
//                        } else {
//
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//
//        }
//    }//_____________________________________________________________________________________________ End getDeviceLocation


    public void AddMarker(LatLng latLng, String title, String tag, int icon) {//____________________ AddMarker
        Marker marker = getGoogleMap().addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(icon)));
        marker.setTag(tag);
    }//_____________________________________________________________________________________________ End AddMarker


    public Circle DrawCircle(LatLng latLng, double radius) {//________________________________________ Start DrawCircle
        Circle circle = getGoogleMap().addCircle(new CircleOptions()
                .center(latLng)
                .radius(radius)
                .strokeWidth(getML_Stroke_Width())
                .strokeColor(getML_Stroke_Color())
                .fillColor(getML_Fill_Color()));
        return circle;
    }//_____________________________________________________________________________________________ End DrawCircle


    public void findAddress(Context c, String location, PublishSubject<String> Observables) {//_____ Start findAddress
        if (location != null && !location.equalsIgnoreCase("")) {
            Locale locale = new Locale("fa_IR");
            Locale.setDefault(locale);
            Geocoder geocoder = new Geocoder(c, locale);

            try {
                List<Address> addressList;
                addressList = geocoder.getFromLocationName(location, 1);
                if (addressList.size() > 0) {
                    setML_FindAddress(new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude()));
                    Observables.onNext("FindAddress");
                } else
                    Observables.onNext("NoAddress");

            } catch (IOException e) {
                e.printStackTrace();
                Observables.onNext("NoAddress");
            }
        }
    }//_____________________________________________________________________________________________ End findAddress


    public ArrayList<LatLng> getCirclePoint(LatLng centre, double radius) {//_____________________________________________________________________________________________ Start getCirclePoint
        ArrayList<LatLng> points = new ArrayList<LatLng>();

        double EARTH_RADIUS = 6378100.0;
        double lat = centre.latitude * Math.PI / 180.0;
        double lon = centre.longitude * Math.PI / 180.0;

        for (double t = 0; t <= Math.PI * 2; t += 0.3) {
            double latPoint = lat + (radius / EARTH_RADIUS) * Math.sin(t);
            double lonPoint = lon + (radius / EARTH_RADIUS) * Math.cos(t) / Math.cos(lat);
            LatLng point = new LatLng(latPoint * 180.0 / Math.PI, lonPoint * 180.0 / Math.PI);
            points.add(point);

        }

        return points;
    }//_____________________________________________________________________________________________ End getCirclePoint


    public void AutoZoom() {//_______________________________________________________________________ Start AutoZoom
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : getML_LatLongs()) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        getGoogleMap().animateCamera(cu);
    }//_____________________________________________________________________________________________ End AutoZoom




    //______________________________________________________________________________________________ Start Getter AND Setter

    public List<LatLng> getML_LatLongs() {
        return ML_LatLongs;
    }

    public void setML_LatLongs(List<LatLng> ML_LatLong) {
        this.ML_LatLongs = ML_LatLong;
    }

    public int getML_Stroke_Color() {
        return ML_Stroke_Color;
    }

    public void setML_Stroke_Color(int ML_Stroke_Color) {
        this.ML_Stroke_Color = ML_Stroke_Color;
    }

    public Float getML_Stroke_Width() {
        return ML_Stroke_Width;
    }

    public void setML_Stroke_Width(Float ML_Stroke_Width) {
        this.ML_Stroke_Width = ML_Stroke_Width;
    }

    public int getML_PATTERN_DASH_LENGTH_PX() {
        return ML_PATTERN_DASH_LENGTH_PX;
    }

    public void setML_PATTERN_DASH_LENGTH_PX(int ML_PATTERN_DASH_LENGTH_PX) {
        this.ML_PATTERN_DASH_LENGTH_PX = ML_PATTERN_DASH_LENGTH_PX;
    }

    public int getML_PATTERN_GAP_LENGTH_PX() {
        return ML_PATTERN_GAP_LENGTH_PX;
    }

    public void setML_PATTERN_GAP_LENGTH_PX(int ML_PATTERN_GAP_LENGTH_PX) {
        this.ML_PATTERN_GAP_LENGTH_PX = ML_PATTERN_GAP_LENGTH_PX;
    }

    public int getML_PATTERN_DASH_LENGTH_Color() {
        return ML_PATTERN_DASH_LENGTH_Color;
    }

    public void setML_PATTERN_DASH_LENGTH_Color(int ML_PATTERN_DASH_LENGTH_Color) {
        this.ML_PATTERN_DASH_LENGTH_Color = ML_PATTERN_DASH_LENGTH_Color;
    }

    public int getML_PATTERN_GAP_LENGTH_Color() {
        return ML_PATTERN_GAP_LENGTH_Color;
    }

    public void setML_PATTERN_GAP_LENGTH_Color(int ML_PATTERN_GAP_LENGTH_Color) {
        this.ML_PATTERN_GAP_LENGTH_Color = ML_PATTERN_GAP_LENGTH_Color;
    }

    public int getML_Fill_Color() {
        return ML_Fill_Color;
    }

    public void setML_Fill_Color(int ML_Fill_Color) {
        this.ML_Fill_Color = ML_Fill_Color;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public LatLng getML_FindAddress() {
        return ML_FindAddress;
    }

    public void setML_FindAddress(LatLng ML_FindAddress) {
        this.ML_FindAddress = ML_FindAddress;
    }

    //______________________________________________________________________________________________ End Getter AND Setter
}
