package com.ngra.wms.views.fragments.lottery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLotteryBinding;
import com.ngra.wms.viewmodels.lottery.VM_Lottery;
import com.ngra.wms.views.fragments.FragmentPrimary;
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

    public Lottery() {//____________________________________________________________________________ Lottery
    }//_____________________________________________________________________________________________ Lottery



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_lottery = new VM_Lottery(getContext());
            FragmentLotteryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery, container, false
            );
            binding.setVMLottery(vm_lottery);
            setView(binding.getRoot());
            SetTabs();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setPublishSubjectFromObservable(
                Lottery.this,
                vm_lottery.getPublishSubject(),
                vm_lottery);
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetTabs() {//______________________________________________________________________ SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
/*                .add(R.string.LotteryResult, LotteryResult.class)*/
/*                .add(R.string.Lottery, LotteryPrimary.class)*/
                .add(R.string.BestScores, BestScore.class)
                .add(R.string.CitizenScore, UserScore.class)
                .add(R.string.HowGiveScore, LotteryGiveScore.class)
                .create());

        FragmentLotteryView.setAdapter(adapter);
        FragmentLotteryTab.setViewPager(FragmentLotteryView);
        FragmentLotteryView.setCurrentItem(2);

    }//_____________________________________________________________________________________________  SetTabs

}
