package com.example.wms.views.fragments.aboutus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentAboutPrimeryBinding;
import com.example.wms.viewmodels.aboutus.VM_About;
import com.example.wms.viewmodels.lottery.VM_LotteryGiveScore;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.lottery.LotteryGiveScore;

import butterknife.ButterKnife;

public class About extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_About vm_about;

    public About() {//______________________________________________________________________________ About
    }//_____________________________________________________________________________________________ About



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_about = new VM_About(getContext());
            FragmentAboutPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_about_primery, container, false
            );
            binding.setVMAbout(vm_about);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setGetMessageFromObservable(
                About.this,
                vm_about.getPublishSubject(),
                vm_about);
    }//_____________________________________________________________________________________________ End onStart



    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
