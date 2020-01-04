package com.example.wms.views.fragments.lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryResultBinding;
import com.example.wms.viewmodels.lottery.VM_FragmentLotteryResult;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLotteryResult extends Fragment {

    private Context context;
    private VM_FragmentLotteryResult vm_fragmentLotteryResult;
    private View view;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentLotteryResultBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery_result, container, false
        );
        vm_fragmentLotteryResult = new VM_FragmentLotteryResult(context);
        binding.setLotteryresult(vm_fragmentLotteryResult);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentLotteryResult() {//______________________________________________________________ Start FragmentLotteryResult
    }//_____________________________________________________________________________________________ End FragmentLotteryResult


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



}
