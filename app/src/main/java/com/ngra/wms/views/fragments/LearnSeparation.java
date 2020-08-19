package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLearnSeparationBinding;
import com.ngra.wms.viewmodels.VM_LearnSeparation;

import org.jetbrains.annotations.NotNull;


public class LearnSeparation extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_LearnSeparation vm_learnSeparation;

    //______________________________________________________________________________________________ LearnSeparation
    public LearnSeparation() {
    }
    //______________________________________________________________________________________________ LearnSeparation


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_learnSeparation = new VM_LearnSeparation(getContext());
            FragmentLearnSeparationBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn_separation, container, false);
            binding.setVMLearnSeparation(vm_learnSeparation);
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
                LearnSeparation.this,
                vm_learnSeparation.getPublishSubject(),
                vm_learnSeparation);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {
    }
    //______________________________________________________________________________________________ getActionFromObservable


}
