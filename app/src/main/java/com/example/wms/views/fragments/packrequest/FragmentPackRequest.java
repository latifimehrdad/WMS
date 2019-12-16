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
import com.example.wms.viewmodels.packrequest.FragmentPackRequestViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;


public class FragmentPackRequest extends Fragment {

    private Context context;
    private FragmentPackRequestViewModel fragmentPackRequestViewModel;

    @BindView(R.id.FragmentPackRequestTab)
    SmartTabLayout FragmentPackRequestTab;

    @BindView(R.id.FragmentPackRequestView)
    ViewPager FragmentPackRequestView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPackRequestBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack_request, container, false
        );
        fragmentPackRequestViewModel = new FragmentPackRequestViewModel(context);
        binding.setPackrequst(fragmentPackRequestViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentPackRequest(Context context) {//_________________________________________________ Start FragmentPackRequest
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentPackRequest


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPackRequestPrimery requestPrimery = new FragmentPackRequestPrimery(context);
        FragmentPackRequestAddress requestAddress = new FragmentPackRequestAddress(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.FragmentPackRequestAddress, requestAddress.getClass())
                .add(R.string.FragmentPackRequestPrimery, requestPrimery.getClass())
                .create());

        FragmentPackRequestView.setAdapter(adapter);
        FragmentPackRequestTab.setViewPager(FragmentPackRequestView);
        FragmentPackRequestView.setCurrentItem(1);


    }//_____________________________________________________________________________________________ End SetTabs



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetBackClickAndGoHome(true));
    }//_____________________________________________________________________________________________ End BackClick



}
