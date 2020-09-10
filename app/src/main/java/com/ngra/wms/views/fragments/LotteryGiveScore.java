package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLotteryGiveScoreBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_LotteryGiveScore;
import com.ngra.wms.views.adaptors.lottery.AP_GiveScore;
import com.ngra.wms.views.adaptors.lottery.AP_ScoreListConfig;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;


public class LotteryGiveScore extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_LotteryGiveScore vm_lotteryGiveScore;

    @BindView(R.id.RecyclerViewGiveScoreNormal)
    RecyclerView RecyclerViewGiveScoreNormal;

    @BindView(R.id.RecyclerViewUserScore)
    RecyclerView RecyclerViewUserScore;

    @BindView(R.id.RecyclerViewGiveScoreConfigs)
    RecyclerView RecyclerViewGiveScoreConfigs;

    @BindView(R.id.gifLoading)
    GifView gifLoading;


    //______________________________________________________________________________________________ LotteryGiveScore
    public LotteryGiveScore() {
    }
    //______________________________________________________________________________________________ LotteryGiveScore


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_lotteryGiveScore = new VM_LotteryGiveScore(getContext());
            FragmentLotteryGiveScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_lottery_give_score, container, false
            );
            binding.setVMLotteryGiveScore(vm_lotteryGiveScore);
            setView(binding.getRoot());
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                LotteryGiveScore.this,
                vm_lotteryGiveScore.getPublishSubject(),
                vm_lotteryGiveScore);
        gifLoading.setVisibility(View.VISIBLE);
        vm_lotteryGiveScore.getGiveScoreList();

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_GetGiveScore)) {
            setAdapterGiveScoreListNormal();
            setAdapterGiveScoreListConfig();
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ setAdapterGiveScoreListNormal
    private void setAdapterGiveScoreListNormal() {

        AP_GiveScore ap_giveScore = new AP_GiveScore(vm_lotteryGiveScore.getScoreItemsNormal());
        RecyclerViewGiveScoreNormal.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewGiveScoreNormal.setAdapter(ap_giveScore);
    }
    //______________________________________________________________________________________________ setAdapterGiveScoreListNormal


    //______________________________________________________________________________________________ setAdapterGiveScoreListConfig
    private void setAdapterGiveScoreListConfig() {

        AP_ScoreListConfig ap_scoreListConfig = new AP_ScoreListConfig(vm_lotteryGiveScore.getScoreListConfigs());
        RecyclerViewGiveScoreConfigs.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewGiveScoreConfigs.setAdapter(ap_scoreListConfig);
    }
    //______________________________________________________________________________________________ setAdapterGiveScoreListConfig


}
