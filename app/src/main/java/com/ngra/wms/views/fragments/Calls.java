package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCallBinding;
import com.ngra.wms.viewmodels.VM_CallWithUs;

import org.jetbrains.annotations.NotNull;

public class Calls extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_CallWithUs vm_callWithUs;

    //______________________________________________________________________________________________ Calls
    public Calls() {
    }
    //______________________________________________________________________________________________ Calls


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_callWithUs = new VM_CallWithUs(getContext());
            FragmentCallBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_call, container, false
            );
            binding.setVMCall(vm_callWithUs);
            setView(binding.getRoot());
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Calls.this,
                vm_callWithUs.getPublishSubject(),
                vm_callWithUs);
    }
    //______________________________________________________________________________________________ onStart



    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ getActionFromObservable



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
