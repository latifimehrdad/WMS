package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentAboutPrimeryBinding;
import com.ngra.wms.viewmodels.VM_About;

import org.jetbrains.annotations.NotNull;

public class About extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_About vm_about;


    //______________________________________________________________________________________________ About
    public About() {
    }
    //______________________________________________________________________________________________ About


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_about = new VM_About(getContext());
            FragmentAboutPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_about_primery, container, false
            );
            binding.setVMAbout(vm_about);
            setView(binding.getRoot());
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setPublishSubjectFromObservable(
                About.this,
                vm_about.getPublishSubject(),
                vm_about);
    }//_____________________________________________________________________________________________ onStart


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
