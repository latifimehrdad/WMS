package com.example.wms.views.fragments.learn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnBinding;
import com.example.wms.viewmodels.learn.FragmentLearrnViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentLearn extends Fragment {

    private Context context;
    private FragmentLearrnViewModel fragmentLearrnViewModel;

    @BindView(R.id.FragmentLearnTab)
    SmartTabLayout FragmentLearnTab;

    @BindView(R.id.FragmentLearnView)
    ViewPager FragmentLearnView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLearnBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learn, container, false
        );
        fragmentLearrnViewModel = new FragmentLearrnViewModel(context);
        binding.setLearn(fragmentLearrnViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLearn(Context context) {//_______________________________________________________ Start
        this.context = context;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLearn() {//_______________________________________________________ Start
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________ Start
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onCreateView



    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentLearnSeparation learnSeparation = new FragmentLearnSeparation(context);
        FragmentLearnItems fragmentLearnItems = new FragmentLearnItems(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.FragmentLearnItems, fragmentLearnItems.getClass())
                .add(R.string.FragmentLearnSeparation, learnSeparation.getClass())
                .create());

        FragmentLearnView.setAdapter(adapter);
        FragmentLearnTab.setViewPager(FragmentLearnView);
        FragmentLearnView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick







}
