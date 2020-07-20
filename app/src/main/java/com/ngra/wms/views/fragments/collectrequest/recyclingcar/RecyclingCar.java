package com.ngra.wms.views.fragments.collectrequest.recyclingcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentRecyclingCarBinding;
import com.ngra.wms.viewmodels.collectrequest.recyclingcar.VM_RecyclingCar;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ngra.wms.views.fragments.collectrequest.collectrequest.CollectRequestOrder;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;

public class RecyclingCar extends FragmentPrimary {


    private VM_RecyclingCar vm_recyclingCar;

    @BindView(R.id.FragmentRecyclingTab)
    SmartTabLayout FragmentRecyclingTab;

    @BindView(R.id.FragmentRecyclingView)
    ViewPager FragmentRecyclingView;

    public RecyclingCar() {//_______________________________________________________________________ RecyclingCar
    }//_____________________________________________________________________________________________ RecyclingCar

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_recyclingCar = new VM_RecyclingCar(getContext());
            FragmentRecyclingCarBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_recycling_car, container, false
            );
            binding.setVMRecyclingCar(vm_recyclingCar);
            setView(binding.getRoot());
            SetTabs();
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart



    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentCollectRequestOrder, CollectRequestOrder.class)
                .add(R.string.FragmentCollectRequestCar, RecyclingCarPrimary.class)
                .create());

        FragmentRecyclingView.setAdapter(adapter);
        FragmentRecyclingTab.setViewPager(FragmentRecyclingView);
        FragmentRecyclingView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs


}
