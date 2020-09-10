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
import com.ngra.wms.databinding.FrScoreUserBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_UserScore;
import com.ngra.wms.views.adaptors.lottery.AP_UserScore;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class UserScore extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_UserScore vm_userScore;

    @BindView(R.id.RecyclerViewGiveScore)
    RecyclerView RecyclerViewGiveScore;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;


    //______________________________________________________________________________________________ UserScore
    public UserScore() {
    }
    //______________________________________________________________________________________________ UserScore



    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_userScore = new VM_UserScore(getContext());
            FrScoreUserBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_score_user, container, false
            );
            binding.setScore(vm_userScore);
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
                UserScore.this,
                vm_userScore.getPublishSubject(),
                vm_userScore);
        vm_userScore.getUserScoreList();

    }
    //______________________________________________________________________________________________ onStart



    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(StaticValues.ML_GetUserScore)) {
            setAdapterUserScore();
            return;
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest



    //______________________________________________________________________________________________ setAdapterUserScore
    private void setAdapterUserScore() {
        AP_UserScore ap_userScore = new AP_UserScore(vm_userScore.getMd_userScoreInfoList().getItems());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerViewGiveScore.setLayoutManager(linearLayoutManager);
        RecyclerViewGiveScore.setAdapter(ap_userScore);

        String title = getContext().getResources().getString(R.string.SumScoreOfMonth);
        if (vm_userScore.getMd_userScoreInfoList() != null)
            title = title +  " " + vm_userScore.getMd_userScoreInfoList().getMonthName();

        TextViewTitle.setText(title);

    }
    //______________________________________________________________________________________________ setAdapterUserScore


}
