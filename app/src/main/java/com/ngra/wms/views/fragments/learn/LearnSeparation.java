package com.ngra.wms.views.fragments.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLearnSeparationBinding;
import com.ngra.wms.viewmodels.learn.VM_LearnSeparation;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;


public class LearnSeparation extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_LearnSeparation vm_learnSeparation;


    public LearnSeparation() {//____________________________________________________________________ LearnSeparation
    }//_____________________________________________________________________________________________ LearnSeparation



    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_learnSeparation = new VM_LearnSeparation(getContext());
            FragmentLearnSeparationBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn_separation, container, false);
            binding.setVMLearnSeparation(vm_learnSeparation);
            setView(binding.getRoot());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________ Start
        super.onStart();
        setGetMessageFromObservable(
                LearnSeparation.this,
                vm_learnSeparation.getPublishSubject(),
                vm_learnSeparation);
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


}
