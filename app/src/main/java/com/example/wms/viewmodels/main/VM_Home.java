package com.example.wms.viewmodels.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;

import com.example.wms.R;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.VM_Primary;

import static com.example.wms.views.activitys.MainActivity.complateprofile;

public class VM_Home extends VM_Primary {

    public VM_Home(Activity context) {//____________________________________________________________ VM_Home
        setContext(context);
    }//_____________________________________________________________________________________________ VM_Home



    public void CheckProfile() {//__________________________________________________________________ CheckProfile

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            complateprofile = false;
        } else {
            complateprofile = prefs.getBoolean(getContext().getString(R.string.ML_CompleteProfile), false);
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


    public int GetPackageState() {//________________________________________________________________ CheckGetPackage

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return 0;
        } else {
            return prefs.getInt(getContext().getString(R.string.ML_PackageRequestStatus), 0);
        }
    }//_____________________________________________________________________________________________ CheckGetPackage



    public boolean IsAddressCompleted() {//_________________________________________________________ IsAddressCompleted
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            return false;
        } else {
            return prefs.getBoolean(getContext().getString(R.string.ML_AddressCompleted), false);
        }
    }//_____________________________________________________________________________________________ IsAddressCompleted

}
