package com.example.wms.views.mlmap;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;


import java.util.Arrays;
import java.util.List;

public class MehrdadLatifiMap {

    private GoogleMap googleMap;
    private List<LatLng> ML_LatLong;
    private int ML_Stroke_Color = 0;
    private Float ML_Stroke_Width = 3f;
    private int ML_PATTERN_DASH_LENGTH_PX = 0;
    private int ML_PATTERN_GAP_LENGTH_PX = 0;
    private int ML_PATTERN_DASH_LENGTH_Color = 0;
    private int ML_PATTERN_GAP_LENGTH_Color = 0;
    private int ML_Fill_Color = 0;


    public void DrawPolylines() {//_________________________________________________________________ Start DrawPolylines
        Polyline polyline = getGoogleMap().addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(getML_LatLong()));
        stylePolyline(polyline);
    }//_____________________________________________________________________________________________ End DrawPolylines


    public void DrawPolygon(boolean Pattern) {//_______________________________ Start DrawPolylines
        Polygon polygon = getGoogleMap().addPolygon(new PolygonOptions()
                .clickable(true)
                .addAll(getML_LatLong()));
        stylePolygon(polygon, Pattern);
    }//_____________________________________________________________________________________________ End DrawPolylines


    private void stylePolyline(Polyline polyline) {//_______________________________________________ Start stylePolyline
        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        polyline.setWidth(getML_Stroke_Width());
        polyline.setColor(getML_Stroke_Color());
        polyline.setJointType(JointType.ROUND);

    }//_____________________________________________________________________________________________ End stylePolyline


    private void stylePolygon(Polygon polygon, boolean Pattern) {//_________________________________ Start stylePolygon

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
    }//_____________________________________________________________________________________________ End stylePolygon


    public Boolean isInside(LatLng point, List<LatLng> latLngs) {//_________________________________ Start isInside
        return
                PolyUtil.containsLocation(point, latLngs, true);
    }//_____________________________________________________________________________________________ End isInside


    public void getDeviceLocation(FragmentActivity context) {//_____________________________________ Start getDeviceLocation
        try {
            if (true) {
                FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(context, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Object mLastKnownLocation = task.getResult();
                            Log.i("meri", mLastKnownLocation.toString());

                        } else {

                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }//_____________________________________________________________________________________________ End getDeviceLocation


    public void AddMarker(LatLng latLng, String title, String tag, int icon) {//____________________ Start AddMarker
        Marker marker1 = getGoogleMap().addMarker(new MarkerOptions()
        .position(latLng)
        .title(title)
        .icon(BitmapDescriptorFactory.fromResource(icon)));
        marker1.setTag(tag);
    }//_____________________________________________________________________________________________ End AddMarker





    //______________________________________________________________________________________________ Start Getter AND Setter

    public List<LatLng> getML_LatLong() {
        return ML_LatLong;
    }

    public void setML_LatLong(List<LatLng> ML_LatLong) {
        this.ML_LatLong = ML_LatLong;
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


    //______________________________________________________________________________________________ End Getter AND Setter
}
