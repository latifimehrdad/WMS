package com.ngra.wms.views.fragments.lottery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLotteryGiveScoreBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.lottery.VM_LotteryGiveScore;
import com.ngra.wms.views.adaptors.lottery.AP_GiveScore;
import com.ngra.wms.views.adaptors.lottery.AP_UserScore;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;


public class LotteryGiveScore extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_LotteryGiveScore vm_lotteryGiveScore;

    @BindView(R.id.RecyclerViewGiveScore)
    RecyclerView RecyclerViewGiveScore;

    @BindView(R.id.RecyclerViewUserScore)
    RecyclerView RecyclerViewUserScore;


    public LotteryGiveScore() {//___________________________________________________________________ LotteryGiveScore
    }//_____________________________________________________________________________________________ LotteryGiveScore



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView

        if (getView() == null) {
            vm_lotteryGiveScore = new VM_LotteryGiveScore(getContext());
            FragmentLotteryGiveScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery_give_score, container, false
            );
            binding.setVMLotteryGiveScore(vm_lotteryGiveScore);
            setView(binding.getRoot());
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
        vm_lotteryGiveScore.GetUserScoreList();

    }//_____________________________________________________________________________________________ End onStart




    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable


        if (action.equals(StaticValues.ML_GetUserScore)) {
            SetAdapterUserScore();
            return;
        }

        if (action.equals(StaticValues.ML_GetGiveScore)) {
            SetAdapterGiveScoreList();
        }
    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetAdapterGiveScoreList() {//______________________________________________________ SetAdapterGiveScoreList

        AP_GiveScore ap_giveScore = new AP_GiveScore(vm_lotteryGiveScore.getMd_giveScoreItemList());
        RecyclerViewGiveScore.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewGiveScore.setAdapter(ap_giveScore);
    }//_____________________________________________________________________________________________ SetAdapterGiveScoreList


    private void SetAdapterUserScore() {//__________________________________________________________ SetAdapterUserScore
        AP_UserScore ap_userScore = new AP_UserScore(vm_lotteryGiveScore.getMd_userScoreItemList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setStackFromEnd(true);
        RecyclerViewUserScore.setLayoutManager(linearLayoutManager);
        RecyclerViewUserScore.setAdapter(ap_userScore);
        vm_lotteryGiveScore.GetGiveScoreList();
        RecyclerViewUserScore.scrollToPosition(vm_lotteryGiveScore.getMd_userScoreItemList().size() - 1);
    }//_____________________________________________________________________________________________ SetAdapterUserScore



}
