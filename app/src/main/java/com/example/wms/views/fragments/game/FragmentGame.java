package com.example.wms.views.fragments.game;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentGameBinding;
import com.example.wms.databinding.FragmentPackRequestBinding;
import com.example.wms.viewmodels.game.VM_GamePrimary;
import com.example.wms.viewmodels.packrequest.VM_FragmentPackRequest;
import com.example.wms.views.fragments.packrequest.FragmentPackRequestAddress;
import com.example.wms.views.fragments.packrequest.FragmentPackRequestPrimery;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentGame extends Fragment {

    private Context context;
    private VM_GamePrimary vm_gamePrimary;
    private View view;

    @BindView(R.id.FragmentGameTab)
    SmartTabLayout FragmentGameTab;

    @BindView(R.id.FragmentGameView)
    ViewPager FragmentGameView;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentGameBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        );
        vm_gamePrimary = new VM_GamePrimary(context);
        binding.setGame(vm_gamePrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentGame() {//_______________________________________________________________________ Start FragmentGame

    }//_____________________________________________________________________________________________ End FragmentGame


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.OnlineResponse, FragmentGameScore.class)
                .add(R.string.StartGame, FragmentGameNew.class)
                .create());

        FragmentGameView.setAdapter(adapter);
        FragmentGameTab.setViewPager(FragmentGameView);
        FragmentGameView.setCurrentItem(1);


    }//_____________________________________________________________________________________________ End SetTabs

}