package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentGameScoreBinding;
import com.ngra.wms.views.fragments.FragmentPrimary;

import butterknife.ButterKnife;

public class GameScore extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

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
            FragmentGameScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_game_score, container, false
            );
            binding.setScore("null");
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
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ getActionFromObservable

}