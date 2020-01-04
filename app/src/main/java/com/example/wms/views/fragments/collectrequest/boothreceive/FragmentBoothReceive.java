package com.example.wms.views.fragments.collectrequest.boothreceive;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentBoothReceiveBinding;
import com.example.wms.viewmodels.collectrequest.boothreceive.VM_FragmentBoothReceive;
import com.example.wms.views.fragments.collectrequest.collectrequest.FragmentCollectRequestOrders;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentBoothReceive extends Fragment {

    private Context context;
    private VM_FragmentBoothReceive vm_fragmentBoothReceive;
    private View view;

    @BindView(R.id.FragmentBoothTab)
    SmartTabLayout FragmentBoothTab;

    @BindView(R.id.FragmentBoothView)
    ViewPager FragmentBoothView;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentBoothReceiveBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_booth_receive,container, false
        );
        vm_fragmentBoothReceive = new VM_FragmentBoothReceive(context);
        binding.setBoothreceive(vm_fragmentBoothReceive);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentBoothReceive() {//_______________________________________________________________ Start FragmentBoothReceive
    }//_____________________________________________________________________________________________ End FragmentBoothReceive


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart



    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.FragmentCollectRequestOrder, FragmentCollectRequestOrders.class)
                .add(R.string.FragmentCollectRequestBooth, FragmentBoothReceivePrimery.class)
                .create());

        FragmentBoothView.setAdapter(adapter);
        FragmentBoothTab.setViewPager(FragmentBoothView);
        FragmentBoothView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs

}
