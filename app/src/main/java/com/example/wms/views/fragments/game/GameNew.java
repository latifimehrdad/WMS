package com.example.wms.views.fragments.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentGameNewBinding;
import com.example.wms.game.controls.GameActivity;
import com.example.wms.views.fragments.FragmentPrimary;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameNew extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    @BindView(R.id.TextViewNew)
    TextView TextViewNew;

    public GameNew() {//____________________________________________________________________________ FragmentGameNew
    }//_____________________________________________________________________________________________ FragmentGameNew


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentGameNewBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_game_new, container, false
            );
            binding.setNewgame("null");
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            TextViewNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GameActivity.class);
                    getContext().startActivity(intent);
                }
            });
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();

    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable
}
