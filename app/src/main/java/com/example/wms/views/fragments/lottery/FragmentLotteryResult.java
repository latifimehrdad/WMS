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
import com.example.wms.databinding.FragmentLotteryResultBinding;
import com.example.wms.viewmodels.lottery.FragmentLotteryResultViewModel;
import com.example.wms.views.activitys.MainActivity;

import butterknife.ButterKnife;

public class FragmentLotteryResult extends Fragment {

    private Context context;
    private FragmentLotteryResultViewModel fragmentLotteryResultViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLotteryResultBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery_result, container, false
        );
        fragmentLotteryResultViewModel = new FragmentLotteryResultViewModel(context);
        binding.setLotteryresult(fragmentLotteryResultViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLotteryResult(Context context) {//_______________________________________________ Start FragmentLotteryResult
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentLotteryResult


    public FragmentLotteryResult() {//______________________________________________________________ Start FragmentLotteryResult
    }//_____________________________________________________________________________________________ End FragmentLotteryResult


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick




    private View.OnKeyListener SetKey(View view){//_________________________________________________ Start SetKey
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                keyCode = 0;
                event = null;
                MainActivity.FragmentMessage.onNext("Main");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetKey



}
