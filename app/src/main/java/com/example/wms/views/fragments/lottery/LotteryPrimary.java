package com.example.wms.views.fragments.lottery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryPrimeryBinding;
import com.example.wms.viewmodels.lottery.VM_LotteryPrimary;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

public class LotteryPrimary extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_LotteryPrimary vm_lotteryPrimary;

    public LotteryPrimary() {//_____________________________________________________________________ LotteryPrimary
    }//_____________________________________________________________________________________________ LotteryPrimary



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_lotteryPrimary = new VM_LotteryPrimary(getContext());
            FragmentLotteryPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery_primery, container, false
            );
            binding.setVMLotteryPrimary(vm_lotteryPrimary);
            setView(binding.getRoot());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setGetMessageFromObservable(
                LotteryPrimary.this,
                vm_lotteryPrimary.getPublishSubject(),
                vm_lotteryPrimary);
    }//_____________________________________________________________________________________________ End onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
