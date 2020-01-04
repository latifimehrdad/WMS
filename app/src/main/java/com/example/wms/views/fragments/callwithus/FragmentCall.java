package com.example.wms.views.fragments.callwithus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.wms.R;
import com.example.wms.databinding.FragmentCallBinding;
import com.example.wms.viewmodels.callwithus.VM_FragmentCallWithUs;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentCall extends Fragment {

    private Context context;
    private VM_FragmentCallWithUs VMFragmentCallWithUs;
    private View view;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentCallBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_call, container, false
        );
        VMFragmentCallWithUs = new VM_FragmentCallWithUs(context);
        binding.setCall(VMFragmentCallWithUs);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCall() {//_______________________________________________________________________ Start VM_FragmentCall
    }//_____________________________________________________________________________________________ End VM_FragmentCall


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



}
