package com.ngra.wms.views.fragments.callwithus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCallBinding;
import com.ngra.wms.viewmodels.callwithus.VM_CallWithUs;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

public class Calls extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_CallWithUs vm_callWithUs;


    public Calls() {//______________________________________________________________________________ Calls
    }//_____________________________________________________________________________________________ Calls



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_callWithUs = new VM_CallWithUs(getContext());
            FragmentCallBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_call, container, false
            );
            binding.setVMCall(vm_callWithUs);
            setView(binding.getRoot());
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
