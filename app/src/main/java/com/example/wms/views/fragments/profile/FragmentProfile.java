/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBinding;
import com.example.wms.viewmodels.user.profile.VM_FragmentProfile;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentProfile extends Fragment {

    private Context context;
    private VM_FragmentProfile vm_fragmentProfile;
    private View view;

    @BindView(R.id.FragmentRegisteryTab)
    SmartTabLayout FragmentRegisteryTab;

    @BindView(R.id.FragmentRegisteryView)
    ViewPager FragmentRegisteryView;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (view == null) {
            this.context = getContext();
            FragmentProfileBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile, container, false
            );
            vm_fragmentProfile = new VM_FragmentProfile(context);
            binding.setProfile(vm_fragmentProfile);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            SetTabs();
        }
        return view;
    }//_____________________________________________________________________________________________ onCreateView


    public FragmentProfile() {//____________________________________________________________________ FragmentProfile

    }//_____________________________________________________________________________________________ FragmentProfile



    private void SetTabs() {//______________________________________________________________________ SetTabs


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.RegisterPersonCodeNew, FragmentProfileCode.class)
                .add(R.string.RegisterPersonBank, FragmentProfileBank.class)
                .add(R.string.RegisterPerson, FragmentProfilePerson.class)
                .create());

        FragmentRegisteryView.setAdapter(adapter);
        FragmentRegisteryTab.setViewPager(FragmentRegisteryView);
        FragmentRegisteryView.setCurrentItem(2);

    }//_____________________________________________________________________________________________ SetTabs


}
