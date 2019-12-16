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
import com.example.wms.viewmodels.callwithus.FragmentCallWithUsViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentCallWithUs extends Fragment {

    private Context context;
    private FragmentCallWithUsViewModel fragmentCallWithUsViewModel;

    @BindView(R.id.FragmentCallWithUsTab)
    SmartTabLayout FragmentCallWithUsTab;

    @BindView(R.id.FragmentCallWithUsView)
    ViewPager FragmentCallWithUsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCallWithUsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_call_with_us, container, false
        );
        fragmentCallWithUsViewModel = new FragmentCallWithUsViewModel(context);
        binding.setCallus(fragmentCallWithUsViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentCallWithUs(Context context) {//__________________________________________________ Start FragmentCallWithUs
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentCallWithUs


    public FragmentCallWithUs() {//_________________________________________________________________ Start FragmentCallWithUs
    }//_____________________________________________________________________________________________ End FragmentCallWithUs


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs


        FragmentCall fragmentCall = new FragmentCall(context);
        FragmentSupport fragmentSupport = new FragmentSupport(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.CallWithUs, fragmentCall.getClass())
                .add(R.string.SupportApp, fragmentSupport.getClass())
                .create());

        FragmentCallWithUsView.setAdapter(adapter);
        FragmentCallWithUsTab.setViewPager(FragmentCallWithUsView);
        FragmentCallWithUsView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetBackClickAndGoHome(true));
    }//_____________________________________________________________________________________________ End BackClick




}
