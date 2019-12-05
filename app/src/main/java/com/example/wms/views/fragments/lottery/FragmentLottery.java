package com.example.wms.views.fragments.lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryBinding;
import com.example.wms.viewmodels.lottery.FragmentLotteryViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.fragments.register.FragmentRegisterBank;
import com.example.wms.views.fragments.register.FragmentRegisterCode;
import com.example.wms.views.fragments.register.FragmentRegisterPerson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentLottery extends Fragment {

    private Context context;
    private FragmentLotteryViewModel fragmentLotteryViewModel;

    @BindView(R.id.FragmentLotteryTab)
    SmartTabLayout FragmentLotteryTab;

    @BindView(R.id.FragmentLotteryView)
    ViewPager FragmentLotteryView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLotteryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery, container, false
        );
        fragmentLotteryViewModel = new FragmentLotteryViewModel(context);
        binding.setLattery(fragmentLotteryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLottery(Context context) {//_____________________________________________________ Start FragmentLottery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentLottery


    public FragmentLottery() {//____________________________________________________________________ Start FragmentLottery
    }//_____________________________________________________________________________________________ End FragmentLottery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentLotteryGiveScore FragmentLotteryGiveScore = new FragmentLotteryGiveScore(context);
        FragmentLotteryResult lotteryResult = new FragmentLotteryResult(context);
        FragmentLotteryPrimery lotteryPrimery = new FragmentLotteryPrimery(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.HowGiveScore, FragmentLotteryGiveScore.getClass())
                .add(R.string.LotteryResult, lotteryResult.getClass())
                .add(R.string.Lottery, lotteryPrimery.getClass())
                .create());

        FragmentLotteryView.setAdapter(adapter);
        FragmentLotteryTab.setViewPager(FragmentLotteryView);

    }//_____________________________________________________________________________________________ End SetTabs



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
