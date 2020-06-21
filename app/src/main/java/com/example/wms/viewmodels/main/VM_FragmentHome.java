/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.viewmodels.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.utility.StaticValues;

import static com.example.wms.views.activitys.MainActivity.complateprofile;

public class VM_FragmentHome {

    private Context context;


    public VM_FragmentHome(Context context) {//_____________________________________________________ VM_FragmentHome
        this.context = context;
    }//_____________________________________________________________________________________________ VM_FragmentHome


    public boolean CheckProfile() {//_______________________________________________________________ CheckProfile

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            complateprofile = false;
        } else {
            complateprofile = prefs.getBoolean(context.getString(R.string.ML_CompleteProfile), false);
        }
        return complateprofile;

    }//_____________________________________________________________________________________________ CheckProfile



    public boolean IsPackageState() {//_____________________________________________________________ IsPackageState
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(context.getString(R.string.ML_IsPackageState), false);
        }
    }//_____________________________________________________________________________________________ IsPackageState



    public int GetPackageState() {//________________________________________________________________ CheckGetPackage

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return 0;
        } else {
            return prefs.getInt(context.getString(R.string.ML_PackageRequest), 0);
        }
    }//_____________________________________________________________________________________________ CheckGetPackage



    public boolean IsAddressCompleted() {//_________________________________________________________ IsAddressCompleted
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(context.getString(R.string.ML_AddressCompleted), false);
        }
    }//_____________________________________________________________________________________________ IsAddressCompleted


}
