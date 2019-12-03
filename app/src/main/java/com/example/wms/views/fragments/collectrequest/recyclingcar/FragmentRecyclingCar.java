package com.example.wms.views.fragments.collectrequest.recyclingcar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentRecyclingCarBinding;
import com.example.wms.viewmodels.collectrequest.recyclingcar.FragmentRecyclingCarViewModel;
import com.example.wms.views.fragments.collectrequest.collectrequest.FragmentCollectRequestOrders;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRecyclingCar extends Fragment {

    private Context context;
    private FragmentRecyclingCarViewModel fragmentRecyclingCarViewModel;

    @BindView(R.id.FragmentRecyclingTab)
    SmartTabLayout FragmentRecyclingTab;

    @BindView(R.id.FragmentRecyclingView)
    ViewPager FragmentRecyclingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRecyclingCarBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_recycling_car, container, false
        );
        fragmentRecyclingCarViewModel = new FragmentRecyclingCarViewModel(context);
        binding.setRecyclingcar(fragmentRecyclingCarViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentRecyclingCar(Context context) {//________________________________________________ Start FragmentRecyclingCar
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRecyclingCar


    public FragmentRecyclingCar() {//_______________________________________________________________ Start FragmentRecyclingCar
    }//_____________________________________________________________________________________________ End FragmentRecyclingCar


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentRecyclingCarPrimery recyclingCarPrimery = new FragmentRecyclingCarPrimery(context);
        FragmentCollectRequestOrders collectRequestOrders = new FragmentCollectRequestOrders(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.FragmentCollectRequestOrder, collectRequestOrders.getClass())
                .add(R.string.FragmentCollectRequestCar, recyclingCarPrimery.getClass())
                .create());

        FragmentRecyclingView.setAdapter(adapter);
        FragmentRecyclingTab.setViewPager(FragmentRecyclingView);

    }//_____________________________________________________________________________________________ End SetTabs


}
