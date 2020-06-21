package com.example.wms.views.fragments.lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryBinding;
import com.example.wms.viewmodels.lottery.VM_FragmentLottery;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLottery extends Fragment {

    private Context context;
    private VM_FragmentLottery vm_fragmentLottery;
    private View view;

    @BindView(R.id.FragmentLotteryTab)
    SmartTabLayout FragmentLotteryTab;

    @BindView(R.id.FragmentLotteryView)
    ViewPager FragmentLotteryView;



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentLotteryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery, container, false
        );
        vm_fragmentLottery = new VM_FragmentLottery(context);
        binding.setLattery(vm_fragmentLottery);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLottery() {//____________________________________________________________________ Start FragmentLottery
    }//_____________________________________________________________________________________________ End FragmentLottery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.HowGiveScore, FragmentLotteryGiveScore.class)
                .add(R.string.LotteryResult, FragmentLotteryResult.class)
                .add(R.string.Lottery, FragmentLotteryPrimery.class)
                .create());

        FragmentLotteryView.setAdapter(adapter);
        FragmentLotteryTab.setViewPager(FragmentLotteryView);
        FragmentLotteryView.setCurrentItem(2);

    }//_____________________________________________________________________________________________ End SetTabs


}
