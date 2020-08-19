package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLotteryBinding;
import com.ngra.wms.viewmodels.VM_Lottery;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Lottery extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_Lottery vm_lottery;

    @BindView(R.id.FragmentLotteryTab)
    SmartTabLayout FragmentLotteryTab;

    @BindView(R.id.FragmentLotteryView)
    ViewPager FragmentLotteryView;

    //______________________________________________________________________________________________ Lottery
    public Lottery() {
    }
    //______________________________________________________________________________________________ Lottery


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_lottery = new VM_Lottery(getContext());
            FragmentLotteryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery, container, false
            );
            binding.setVMLottery(vm_lottery);
            setView(binding.getRoot());
            setTabs();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Lottery.this,
                vm_lottery.getPublishSubject(),
                vm_lottery);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setTabs
    private void setTabs() {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.BestScores, BestScore.class)
                .add(R.string.CitizenScore, UserScore.class)
                .add(R.string.HowGiveScore, LotteryGiveScore.class)
                .create());

        FragmentLotteryView.setAdapter(adapter);
        FragmentLotteryTab.setViewPager(FragmentLotteryView);
        FragmentLotteryView.setCurrentItem(2);
    }
    //______________________________________________________________________________________________ setTabs

}
