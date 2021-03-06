package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ngra.wms.R;
import com.ngra.wms.models.ModelPackage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class VM_PackageRequestPrimary extends VM_Primary {

    private String deliveryDate;
    private String deliveryTime;
    private Byte packStatus;


    //______________________________________________________________________________________________ VM_PackageRequestPrimary
    public VM_PackageRequestPrimary(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_PackageRequestPrimary


    //______________________________________________________________________________________________ getPackageStatus
    public Byte getPackageStatus() {

        packStatus = 0;
        if (getContext() != null) {
            SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
            if (prefs != null)
                packStatus = (byte) prefs.getInt(getContext().getString(R.string.ML_PackageRequestStatus), 0);
        }
        notifyChange();
        return packStatus;
    }
    //______________________________________________________________________________________________ getPackageStatus


    //______________________________________________________________________________________________ packageRequestDate
    public ModelPackage packageRequestDate() {
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            ModelPackage modelPackage = new ModelPackage();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            String sDate = prefs.getString(getContext().getString(R.string.ML_PackageRequestDate), null);
            Date date;
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setRequestDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            sDate = prefs.getString(getContext().getString(R.string.ML_PackageRequestFrom), null);
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setFromDeliver(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            sDate = prefs.getString(getContext().getString(R.string.ML_PackageRequestTo), null);
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setToDeliver(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return modelPackage;
        } else
            return null;
    }
    //______________________________________________________________________________________________ packageRequestDate


    //______________________________________________________________________________________________ setPackageDate
    public void setPackageDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        deliveryDate = getUtility().gregorianToSun(packageRequestDate().getFromDeliver()).getFullStringSun();
        deliveryTime = simpleDateFormat.format(packageRequestDate().getFromDeliver()) +
                " تا " +
                simpleDateFormat.format(packageRequestDate().getToDeliver());
        notifyChange();
    }
    //______________________________________________________________________________________________ setPackageDate


    //______________________________________________________________________________________________ getDeliveryDate
    public String getDeliveryDate() {
        return deliveryDate;
    }
    //______________________________________________________________________________________________ getDeliveryDate


    //______________________________________________________________________________________________ setDeliveryDate
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    //______________________________________________________________________________________________ setDeliveryDate


    //______________________________________________________________________________________________ getDeliveryTime
    public String getDeliveryTime() {
        return deliveryTime;
    }
    //______________________________________________________________________________________________ getDeliveryTime


    //______________________________________________________________________________________________ setDeliveryTime
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    //______________________________________________________________________________________________ setDeliveryTime


    //______________________________________________________________________________________________ getPackStatus
    public Byte getPackStatus() {
        return packStatus;
    }
    //______________________________________________________________________________________________ getPackStatus


    //______________________________________________________________________________________________ setPackStatus
    public void setPackStatus(Byte packStatus) {
        this.packStatus = packStatus;
    }
    //______________________________________________________________________________________________ setPackStatus


}
