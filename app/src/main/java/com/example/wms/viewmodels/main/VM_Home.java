package com.example.wms.viewmodels.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.example.wms.R;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;

import static com.example.wms.views.activitys.MainActivity.complateprofile;

public class VM_Home extends VM_Primary {

    private Context context;

    public VM_Home(Context context) {//_____________________________________________________________ VM_Home
        this.context = context;
    }//_____________________________________________________________________________________________ VM_Home



    public void CheckProfile() {//__________________________________________________________________ CheckProfile

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            complateprofile = false;
        } else {
            complateprofile = prefs.getBoolean(context.getString(R.string.ML_CompleteProfile), false);
        }

        if (!complateprofile) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getPublishSubject().onNext(StaticValues.ML_GotoProfile);
                }
            }, 10);
        }

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
