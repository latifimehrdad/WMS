package com.example.wms.views.fragments.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBinding;
import com.example.wms.viewmodels.user.profile.VM_Profile;
import com.example.wms.views.fragments.FragmentPrimary;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Profile extends FragmentPrimary {

    private VM_Profile vm_profile;

    @BindView(R.id.FragmentRegistryTab)
    SmartTabLayout FragmentRegistryTab;

    @BindView(R.id.FragmentRegistryView)
    ViewPager FragmentRegistryView;


    public Profile() {//____________________________________________________________________________ Profile

    }//_____________________________________________________________________________________________ Profile



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentProfileBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile, container, false);
            vm_profile = new VM_Profile(getContext());
            binding.setVmProfile(vm_profile);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetTabs();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    private void SetTabs() {//______________________________________________________________________ SetTabs


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.RegisterPersonCodeNew, FragmentProfileCode.class)
                .add(R.string.RegisterPersonBank, FragmentProfileBank.class)
                .add(R.string.RegisterPerson, FragmentProfilePerson.class)
                .create());

        FragmentRegistryView.setAdapter(adapter);
        FragmentRegistryTab.setViewPager(FragmentRegistryView);
        FragmentRegistryView.setCurrentItem(2);

    }//_____________________________________________________________________________________________ SetTabs


}
