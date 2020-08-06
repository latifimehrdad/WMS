package com.ngra.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentCollectRequstBinding;
import com.ngra.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequests;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CollectRequest extends FragmentPrimary {

    @BindView(R.id.FragmentCollectTab)
    SmartTabLayout FragmentCollectTab;

    @BindView(R.id.FragmentCollectView)
    ViewPager FragmentCollectView;

    public CollectRequest() {//_____________________________________________________________________ CollectRequest
    }//_____________________________________________________________________________________________ CollectRequest


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            VM_CollectRequests vm_collectRequests = new VM_CollectRequests(getContext());
            FragmentCollectRequstBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_collect_requst,container,false);
            binding.setVMCollectRequest(vm_collectRequests);
            setView(binding.getRoot());
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
