package com.ngra.wms.views.fragments.collectrequest.boothreceive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentBoothReceiveBinding;
import com.ngra.wms.viewmodels.collectrequest.boothreceive.VM_BoothReceive;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ngra.wms.views.fragments.collectrequest.collectrequest.CollectRequestOrder;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class BoothReceive extends FragmentPrimary {

    @BindView(R.id.FragmentBoothTab)
    SmartTabLayout FragmentBoothTab;

    @BindView(R.id.FragmentBoothView)
    ViewPager FragmentBoothView;


    public BoothReceive() {//_______________________________________________________________________ BoothReceive
    }//_____________________________________________________________________________________________ BoothReceive

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            VM_BoothReceive vm_boothReceive = new VM_BoothReceive(getContext());
            FragmentBoothReceiveBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_booth_receive,container, false);
            binding.setVmBoothReceive(vm_boothReceive);
            setView(binding.getRoot());
            SetTabs();
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentCollectRequestOrder, CollectRequestOrder.class)
                .add(R.string.FragmentCollectRequestBooth, BoothReceivePrimary.class)
                .create());

        FragmentBoothView.setAdapter(adapter);
        FragmentBoothTab.setViewPager(FragmentBoothView);
        FragmentBoothView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs


}
