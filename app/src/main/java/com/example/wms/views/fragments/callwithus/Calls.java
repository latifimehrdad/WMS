package com.example.wms.views.fragments.callwithus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCallBinding;
import com.example.wms.viewmodels.callwithus.VM_CallWithUs;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.ButterKnife;

public class Calls extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_CallWithUs vm_callWithUs;


    public Calls() {//______________________________________________________________________________ Calls
    }//_____________________________________________________________________________________________ Calls



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_callWithUs = new VM_CallWithUs(getContext());
            FragmentCallBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_call, container, false
            );
            binding.setVMCall(vm_callWithUs);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Calls.this,
                vm_callWithUs.getPublishSubject(),
                vm_callWithUs);
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
