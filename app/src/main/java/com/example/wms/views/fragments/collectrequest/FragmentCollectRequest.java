package com.example.wms.views.fragments.collectrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequstBinding;
import com.example.wms.viewmodels.collectrequest.FragmentCollectRequestViewModel;
import com.example.wms.views.fragments.register.FragmentRegisterBank;
import com.example.wms.views.fragments.register.FragmentRegisterCode;
import com.example.wms.views.fragments.register.FragmentRegisterPerson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCollectRequest extends Fragment {

    private Context context;
    private FragmentCollectRequestViewModel fragmentCollectRequestViewModel;

    @BindView(R.id.FragmentCollectTab)
    SmartTabLayout FragmentCollectTab;

    @BindView(R.id.FragmentCollectView)
    ViewPager FragmentCollectView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCollectRequstBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_requst,container,false
        );
        fragmentCollectRequestViewModel = new FragmentCollectRequestViewModel(context);
        binding.setCollectrequest(fragmentCollectRequestViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCollectRequest(Context context) {//______________________________________________ Start FragmentCollectRequest
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentCollectRequest


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentCollectRequestPrimery fragmentCollectRequestPrimery = new FragmentCollectRequestPrimery(context);
        FragmentCollectRequestOrders collectRequestOrders = new FragmentCollectRequestOrders(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.FragmentCollectRequestOrder, collectRequestOrders.getClass())
                .add(R.string.FragmentCollectRequst, fragmentCollectRequestPrimery.getClass())
                .create());

        FragmentCollectView.setAdapter(adapter);
        FragmentCollectTab.setViewPager(FragmentCollectView);

    }//_____________________________________________________________________________________________ End SetTabs



}
