package com.example.wms.views.fragments.lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLotteryPrimeryBinding;
import com.example.wms.viewmodels.lottery.VM_FragmentLotteryPrimary;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLotteryPrimery extends Fragment {

    private Context context;
    private VM_FragmentLotteryPrimary vm_fragmentLotteryPrimary;
    private View view;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentLotteryPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lottery_primery, container, false
        );
        vm_fragmentLotteryPrimary = new VM_FragmentLotteryPrimary(context);
        binding.setLotteryprimery(vm_fragmentLotteryPrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLotteryPrimery() {//_____________________________________________________________ Start FragmentLotteryPrimery
    }//_____________________________________________________________________________________________ End FragmentLotteryPrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
