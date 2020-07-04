package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequstBinding;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequest;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_FragmentCollectRequest;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.learn.Learn;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectRequest extends FragmentPrimary {

    private VM_CollectRequest vm_collectRequest;

    @BindView(R.id.FragmentCollectTab)
    SmartTabLayout FragmentCollectTab;

    @BindView(R.id.FragmentCollectView)
    ViewPager FragmentCollectView;

    public CollectRequest() {//_____________________________________________________________________ CollectRequest
    }//_____________________________________________________________________________________________ CollectRequest


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            vm_collectRequest = new VM_CollectRequest(getContext());
            FragmentCollectRequstBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_collect_requst,container,false);
            binding.setVMCollectRequest(vm_collectRequest);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
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
                .add(R.string.FragmentCollectRequest, CollectRequestPrimary.class)
                .create());

        FragmentCollectView.setAdapter(adapter);
        FragmentCollectTab.setViewPager(FragmentCollectView);
        FragmentCollectView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs

}
