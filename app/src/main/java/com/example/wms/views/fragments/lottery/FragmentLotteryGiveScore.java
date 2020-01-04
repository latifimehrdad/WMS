package com.example.wms.views.fragments.lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryGiveScoreBinding;
import com.example.wms.viewmodels.lottery.VM_FragmentLotteryGiveScore;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLotteryGiveScore extends Fragment {

    private Context context;
    private VM_FragmentLotteryGiveScore vm_fragmentLotteryGiveScore;
    private View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        FragmentLotteryGiveScoreBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery_give_score, container, false
        );
        vm_fragmentLotteryGiveScore = new VM_FragmentLotteryGiveScore(context);
        binding.setGivescore(vm_fragmentLotteryGiveScore);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLotteryGiveScore() {//___________________________________________________________ Start FragmentLotteryGiveScore
    }//_____________________________________________________________________________________________ End FragmentLotteryGiveScore


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



}
