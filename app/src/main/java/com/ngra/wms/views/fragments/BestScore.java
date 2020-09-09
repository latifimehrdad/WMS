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
import com.ngra.wms.databinding.FrBestScoreBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_BestScore;
import com.ngra.wms.views.adaptors.lottery.AP_BestScore;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class BestScore extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_BestScore vm_bestScore;

    @BindView(R.id.RecyclerViewGiveScore)
    RecyclerView RecyclerViewGiveScore;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;


    //______________________________________________________________________________________________ BestScore
    public BestScore() {
    }
    //______________________________________________________________________________________________ BestScore


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_bestScore = new VM_BestScore(getContext());
            FrBestScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_best_score, container, false
            );
            binding.setBest(vm_bestScore);
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
                BestScore.this,
                vm_bestScore.getPublishSubject(),
                vm_bestScore);
        vm_bestScore.getBestScore("waste");
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(StaticValues.ML_GetBestScore)) {
            setAdapterBestScore();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setAdapterBestScore
    private void setAdapterBestScore() {
        AP_BestScore ap_bestScore = new AP_BestScore(vm_bestScore.getMd_bestScores());
        RecyclerViewGiveScore.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewGiveScore.setAdapter(ap_bestScore);
        String title = getContext().getResources().getString(R.string.TopThree) + " ";
        if (vm_bestScore.getMd_bestScores().size() > 0)
            title = title + vm_bestScore.getMd_bestScores().get(0).getMonthName();
        TextViewTitle.setText(title);
    }
    //______________________________________________________________________________________________ setAdapterBestScore


}