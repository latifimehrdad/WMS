package com.example.wms.views.fragments.collectrequest.boothreceive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentBoothReceiveBinding;
import com.example.wms.viewmodels.collectrequest.boothreceive.VM_BoothReceive;
import com.example.wms.viewmodels.collectrequest.boothreceive.VM_FragmentBoothReceive;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.collectrequest.collectrequest.CollectRequestOrder;
import com.example.wms.views.fragments.collectrequest.collectrequest.FragmentCollectRequestOrders;
import com.example.wms.views.fragments.learn.Learn;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoothReceive extends FragmentPrimary {

    private VM_BoothReceive vm_boothReceive;

    @BindView(R.id.FragmentBoothTab)
    SmartTabLayout FragmentBoothTab;

    @BindView(R.id.FragmentBoothView)
    ViewPager FragmentBoothView;


    public BoothReceive() {//_______________________________________________________________________ BoothReceive
    }//_____________________________________________________________________________________________ BoothReceive

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_boothReceive = new VM_BoothReceive(getContext());
            FragmentBoothReceiveBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_booth_receive,container, false);
            binding.setVmBoothReceive(vm_boothReceive);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
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
