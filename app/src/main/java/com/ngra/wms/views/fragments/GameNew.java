package com.ngra.wms.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentGameNewBinding;
import com.ngra.wms.game.controls.GameActivity;
import com.ngra.wms.views.fragments.FragmentPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameNew extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    @BindView(R.id.TextViewNew)
    TextView TextViewNew;

    //______________________________________________________________________________________________ GameNew
    public GameNew() {
    }
    //______________________________________________________________________________________________ GameNew


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentGameNewBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_game_new, container, false
            );
            binding.setNewGame("null");
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            TextViewNew.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), GameActivity.class);
                getContext().startActivity(intent);
            });
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
