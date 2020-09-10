package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentGameScoreBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_BestScore;
import com.ngra.wms.views.adaptors.lottery.AP_BestScore;
import com.ngra.wms.views.fragments.FragmentPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameScore extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_BestScore vm_bestScore;

    @BindView(R.id.RecyclerViewGiveScore)
    RecyclerView RecyclerViewGiveScore;

    @BindView(R.id.TextViewTitle)
    TextView TextViewTitle;


    //______________________________________________________________________________________________ FragmentGameNew
    public GameScore() {
    }
    //______________________________________________________________________________________________ FragmentGameNew


    //______________________________________________________________________________________________ onCreateV
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_bestScore = new VM_BestScore(getContext());
            FragmentGameScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_game_score, container, false
            );
            binding.setScore(vm_bestScore);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }

        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                GameScore.this,
                vm_bestScore.getPublishSubject(),
                vm_bestScore);
        vm_bestScore.getBestScore("game");
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




    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


}