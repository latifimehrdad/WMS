package com.ngra.wms.views.fragments.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentGameBinding;
import com.ngra.wms.viewmodels.game.VM_GamePrimary;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Game extends Fragment {

    private Activity context;
    private View view;

    @BindView(R.id.FragmentGameTab)
    SmartTabLayout FragmentGameTab;

    @BindView(R.id.FragmentGameView)
    ViewPager FragmentGameView;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (view == null) {
            this.context = getActivity();
            FragmentGameBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_game, container, false
            );
            VM_GamePrimary vm_gamePrimary = new VM_GamePrimary(context);
            binding.setGame(vm_gamePrimary);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public Game() {//_______________________________________________________________________ Start FragmentGame

    }//_____________________________________________________________________________________________ End FragmentGame


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.OnlineResponse, GameScore.class)
                .add(R.string.StartGame, GameNew.class)
                .create());

        FragmentGameView.setAdapter(adapter);
        FragmentGameTab.setViewPager(FragmentGameView);
        FragmentGameView.setCurrentItem(1);


    }//_____________________________________________________________________________________________ End SetTabs

}
