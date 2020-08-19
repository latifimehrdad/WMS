package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCallWithUsBinding;
import com.ngra.wms.viewmodels.VM_CallWithUs;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CallWithUs extends FragmentPrimary {

    @BindView(R.id.FragmentCallWithUsTab)
    SmartTabLayout FragmentCallWithUsTab;

    @BindView(R.id.FragmentCallWithUsView)
    ViewPager FragmentCallWithUsView;

    //______________________________________________________________________________________________ CallWithUs
    public CallWithUs() {
    }
    //______________________________________________________________________________________________ CallWithUs


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            VM_CallWithUs vm_callWithUs = new VM_CallWithUs(getContext());
            FragmentCallWithUsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_call_with_us, container, false
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
        SetTabs();
    }
    //______________________________________________________________________________________________ onStart



    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.CallWithUs, Calls.class)
//                .add(R.string.SupportApp, UserMessage.class)
                .add(R.string.SupportApp, CallSupport.class)
                .create());

        FragmentCallWithUsView.setAdapter(adapter);
        FragmentCallWithUsTab.setViewPager(FragmentCallWithUsView);
        FragmentCallWithUsView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs

}
