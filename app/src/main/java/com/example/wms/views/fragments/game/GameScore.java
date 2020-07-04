package com.example.wms.views.fragments.game;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentGameNewBinding;
import com.example.wms.databinding.FragmentGameScoreBinding;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.ButterKnife;

public class GameScore extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    public GameScore() {//____________________________________________________________________ Start FragmentGameNew
    }//_____________________________________________________________________________________________ End FragmentGameNew


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            FragmentGameScoreBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_game_score, container, false
            );
            binding.setScore("null");
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }

        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


    @Override
    public void GetMessageFromObservable(Byte action) {

    }
}