package com.example.wms.views.fragments.lottery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryResultBinding;
import com.example.wms.viewmodels.lottery.VM_LotteryResult;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

public class LotteryResult extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_LotteryResult vm_lotteryResult;


    public LotteryResult() {//______________________________________________________________________ LotteryResult
    }//_____________________________________________________________________________________________ LotteryResult



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_lotteryResult = new VM_LotteryResult(getContext());
            FragmentLotteryResultBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery_result, container, false
            );
            binding.setVMLotteryResult(vm_lotteryResult);
            setView(binding.getRoot());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setGetMessageFromObservable(
                LotteryResult.this,
                vm_lotteryResult.getPublishSubject(),
                vm_lotteryResult);
    }//_____________________________________________________________________________________________ End onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
