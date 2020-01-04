package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestBinding;
import com.example.wms.viewmodels.packrequest.VM_FragmentPackRequest;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentPackRequest extends Fragment {

    private Context context;
    private VM_FragmentPackRequest vm_fragmentPackRequest;
    private View view;

    @BindView(R.id.FragmentPackRequestTab)
    SmartTabLayout FragmentPackRequestTab;

    @BindView(R.id.FragmentPackRequestView)
    ViewPager FragmentPackRequestView;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentPackRequestBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack_request, container, false
        );
        vm_fragmentPackRequest = new VM_FragmentPackRequest(context);
        binding.setPackrequst(vm_fragmentPackRequest);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentPackRequest() {//________________________________________________________________ Start FragmentPackRequest

    }//_____________________________________________________________________________________________ End FragmentPackRequest


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.FragmentPackRequestAddress, FragmentPackRequestAddress.class)
                .add(R.string.FragmentPackRequestPrimery, FragmentPackRequestPrimery.class)
                .create());

        FragmentPackRequestView.setAdapter(adapter);
        FragmentPackRequestTab.setViewPager(FragmentPackRequestView);
        FragmentPackRequestView.setCurrentItem(1);


    }//_____________________________________________________________________________________________ End SetTabs



}
