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

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentGameNew extends Fragment {

    private Context context;
    private View view;

    @BindView(R.id.TextViewNew)
    TextView TextViewNew;

    public FragmentGameNew() {//____________________________________________________________________ Start FragmentGameNew
    }//_____________________________________________________________________________________________ End FragmentGameNew


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        context = getActivity();
        FragmentGameNewBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_new, container, false
        );
        binding.setNewgame("null");
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        TextViewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameActivity.class);
                context.startActivity(intent);
            }
        });
    }//_____________________________________________________________________________________________ End onStart
}
