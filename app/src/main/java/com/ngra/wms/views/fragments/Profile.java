package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentProfileBinding;
import com.ngra.wms.viewmodels.VM_Profile;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Profile extends FragmentPrimary {

    @BindView(R.id.FragmentRegistryTab)
    SmartTabLayout FragmentRegistryTab;

    @BindView(R.id.FragmentRegistryView)
    ViewPager FragmentRegistryView;


    //______________________________________________________________________________________________ Profile
    public Profile() {
    }
    //______________________________________________________________________________________________ Profile



    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            FragmentProfileBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile, container, false);
            VM_Profile vm_profile = new VM_Profile(getContext());
            binding.setVmProfile(vm_profile);
            setView(binding.getRoot());
            SetTabs();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView




    private void SetTabs() {//______________________________________________________________________ SetTabs


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.RegisterPersonCodeNew, ProfileCode.class)
                .add(R.string.RegisterPersonBank, ProfileBank.class)
                .add(R.string.RegisterPerson, ProfilePerson.class)
                .create());

        FragmentRegistryView.setAdapter(adapter);
        FragmentRegistryTab.setViewPager(FragmentRegistryView);
        FragmentRegistryView.setCurrentItem(2);

    }//_____________________________________________________________________________________________ SetTabs


}
