package com.example.wms.views.fragments.lottery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentBoothReceivePrimeryBinding;
import com.example.wms.databinding.FragmentLotteryGiveScoreBinding;
import com.example.wms.viewmodels.collectrequest.boothreceive.VM_BoothReceivePrimary;
import com.example.wms.viewmodels.lottery.VM_LotteryGiveScore;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.collectrequest.boothreceive.BoothReceivePrimary;
import com.google.android.gms.maps.SupportMapFragment;

import butterknife.ButterKnife;

public class LotteryGiveScore extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_LotteryGiveScore vm_lotteryGiveScore;


    public LotteryGiveScore() {//___________________________________________________________________ LotteryGiveScore
    }//_____________________________________________________________________________________________ LotteryGiveScore



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_lotteryGiveScore = new VM_LotteryGiveScore(getContext());
            FragmentLotteryGiveScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery_give_score, container, false
            );
            binding.setVMLotteryGiveScore(vm_lotteryGiveScore);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setGetMessageFromObservable(
                LotteryGiveScore.this,
                vm_lotteryGiveScore.getPublishSubject(),
                vm_lotteryGiveScore);
    }//_____________________________________________________________________________________________ End onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


}
