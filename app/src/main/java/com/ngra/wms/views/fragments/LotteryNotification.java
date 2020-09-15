package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FrLotteryNotificationBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_LotteryNotification;
import com.ngra.wms.views.adaptors.lottery.AP_LotteryNotification;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class LotteryNotification extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_LotteryNotification vm_lotteryNotification;

    @BindView(R.id.recyclerViewNotification)
    RecyclerView recyclerViewNotification;

    @BindView(R.id.textViewNoNotification)
    TextView textViewNoNotification;


    //______________________________________________________________________________________________ LotteryNotification
    public LotteryNotification() {
    }
    //______________________________________________________________________________________________ LotteryNotification


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_lotteryNotification = new VM_LotteryNotification(getContext());
            FrLotteryNotificationBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_lottery_notification, container, false
            );
            binding.setLotteryNotification(vm_lotteryNotification);
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
                LotteryNotification.this,
                vm_lotteryNotification.getPublishSubject(),
                vm_lotteryNotification);

        recyclerViewNotification.setVisibility(View.GONE);
        textViewNoNotification.setVisibility(View.GONE);
        vm_lotteryNotification.getGiveScoreList();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(StaticValues.ML_LotteryNotification)) {
            if (vm_lotteryNotification.getMd_lotteryNotifications().size() > 0)
                setAdapter();
            else
                textViewNoNotification.setVisibility(View.VISIBLE);
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {

    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ actionWhenFailureRequest
    private void setAdapter() {
        recyclerViewNotification.setVisibility(View.VISIBLE);
        textViewNoNotification.setVisibility(View.GONE);
        AP_LotteryNotification lotteryNotification = new AP_LotteryNotification(vm_lotteryNotification.getMd_lotteryNotifications());
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewNotification.setAdapter(lotteryNotification);
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest

}
