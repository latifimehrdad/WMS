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
import com.example.wms.databinding.FragmentLotteryPrimeryBinding;
import com.example.wms.viewmodels.lottery.FragmentLotteryPrimeryViewModel;
import com.example.wms.views.activitys.MainActivity;

import butterknife.ButterKnife;

public class FragmentLotteryPrimery extends Fragment {

    private Context context;
    private FragmentLotteryPrimeryViewModel fragmentLotteryPrimeryViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLotteryPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery_primery, container, false
        );
        fragmentLotteryPrimeryViewModel = new FragmentLotteryPrimeryViewModel(context);
        binding.setLotteryprimery(fragmentLotteryPrimeryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentLotteryPrimery(Context context) {//______________________________________________ Start FragmentLotteryPrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentLotteryPrimery


    public FragmentLotteryPrimery() {//_____________________________________________________________ Start FragmentLotteryPrimery
    }//_____________________________________________________________________________________________ End FragmentLotteryPrimery


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
