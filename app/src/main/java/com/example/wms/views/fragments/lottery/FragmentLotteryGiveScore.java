package com.example.wms.views.fragments.lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryGiveScoreBinding;
import com.example.wms.viewmodels.lottery.FragmentLotteryGiveScoreViewModel;
import com.example.wms.views.activitys.MainActivity;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentLotteryGiveScore extends Fragment {

    private Context context;
    private FragmentLotteryGiveScoreViewModel fragmentLotteryGiveScoreViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLotteryGiveScoreBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery_give_score, container, false
        );
        fragmentLotteryGiveScoreViewModel = new FragmentLotteryGiveScoreViewModel(context);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentLotteryGiveScore(Context context) {//____________________________________________ Start FragmentLotteryGiveScore
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentLotteryGiveScore


    public FragmentLotteryGiveScore() {//___________________________________________________________ Start FragmentLotteryGiveScore
    }//_____________________________________________________________________________________________ End FragmentLotteryGiveScore


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick





}
