/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentRegisterBank extends Fragment {

    private Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentRegisterBank(Context context) {//________________________________________________ Start FragmentRegisterBank
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRegisterBank


    public FragmentRegisterBank() {//_______________________________________________________________ Start FragmentRegisterBank
    }//_____________________________________________________________________________________________ End FragmentRegisterBank


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
