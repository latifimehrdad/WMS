package com.example.wms.views.fragments.callwithus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCallBinding;
import com.example.wms.databinding.FragmentCallWithUsBinding;
import com.example.wms.viewmodels.callwithus.VM_CallWithUs;
import com.example.wms.views.fragments.FragmentPrimary;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallWithUs extends FragmentPrimary {

    private VM_CallWithUs vm_callWithUs;

    @BindView(R.id.FragmentCallWithUsTab)
    SmartTabLayout FragmentCallWithUsTab;

    @BindView(R.id.FragmentCallWithUsView)
    ViewPager FragmentCallWithUsView;

    public CallWithUs() {//_________________________________________________________________________ CallWithUs
    }//_____________________________________________________________________________________________ CallWithUs



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_callWithUs = new VM_CallWithUs(getContext());
            FragmentCallWithUsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_call_with_us, container, false
            );
            binding.setVMCall(vm_callWithUs);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.CallWithUs, FragmentCall.class)
                .add(R.string.SupportApp, FragmentSupport.class)
                .create());

        FragmentCallWithUsView.setAdapter(adapter);
        FragmentCallWithUsTab.setViewPager(FragmentCallWithUsView);
        FragmentCallWithUsView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs

}
