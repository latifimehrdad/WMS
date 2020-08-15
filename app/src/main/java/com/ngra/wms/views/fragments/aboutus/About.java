package com.ngra.wms.views.fragments.aboutus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentAboutPrimeryBinding;
import com.ngra.wms.viewmodels.aboutus.VM_About;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

public class About extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_About vm_about;

    public About() {//______________________________________________________________________________ About
    }//_____________________________________________________________________________________________ About



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_about = new VM_About(getContext());
            FragmentAboutPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_about_primery, container, false
            );
            binding.setVMAbout(vm_about);
            setView(binding.getRoot());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setPublishSubjectFromObservable(
                About.this,
                vm_about.getPublishSubject(),
                vm_about);
    }//_____________________________________________________________________________________________ End onStart



    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
