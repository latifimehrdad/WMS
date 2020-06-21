package com.example.wms.views.fragments.callwithus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCallWithUsBinding;
import com.example.wms.viewmodels.callwithus.VM_FragmentCallWithUs;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentCallWithUs extends Fragment {

    private Context context;
    private VM_FragmentCallWithUs vm_fragmentCallWithUs;
    private View view;

    @BindView(R.id.FragmentCallWithUsTab)
    SmartTabLayout FragmentCallWithUsTab;

    @BindView(R.id.FragmentCallWithUsView)
    ViewPager FragmentCallWithUsView;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentCallWithUsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_call_with_us, container, false
        );
        vm_fragmentCallWithUs = new VM_FragmentCallWithUs(context);
        binding.setCallus(vm_fragmentCallWithUs);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCallWithUs() {//_________________________________________________________________ Start FragmentCallWithUs
    }//_____________________________________________________________________________________________ End FragmentCallWithUs


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.CallWithUs, FragmentCall.class)
                .add(R.string.SupportApp, FragmentSupport.class)
                .create());

        FragmentCallWithUsView.setAdapter(adapter);
        FragmentCallWithUsTab.setViewPager(FragmentCallWithUsView);
        FragmentCallWithUsView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs



}
