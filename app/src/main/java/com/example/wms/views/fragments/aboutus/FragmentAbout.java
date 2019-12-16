package com.example.wms.views.fragments.aboutus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentAboutBinding;
import com.example.wms.viewmodels.aboutus.FragmentAboutViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentAbout extends Fragment {

    private Context context;
    private FragmentAboutViewModel fragmentAboutViewModel;

    @BindView(R.id.FragmentAboutTab)
    SmartTabLayout FragmentAboutTab;

    @BindView(R.id.FragmentAboutView)
    ViewPager FragmentAboutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAboutBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_about, container, false
        );
        fragmentAboutViewModel = new FragmentAboutViewModel(context);
        binding.setAbout(fragmentAboutViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentAbout(Context context) {//_______________________________________________________ Start FragmentAbout
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentAbout



    public FragmentAbout() {//______________________________________________________________________ Start FragmentAbout
    }//_____________________________________________________________________________________________ End FragmentAbout


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs


        FragmentAboutPrimery fragmentAboutPrimery = new FragmentAboutPrimery(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.AboutUs, fragmentAboutPrimery.getClass())

                .create());

        FragmentAboutView.setAdapter(adapter);
        FragmentAboutTab.setViewPager(FragmentAboutView);

    }//_____________________________________________________________________________________________ End SetTabs



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetBackClickAndGoHome(true));
    }//_____________________________________________________________________________________________ End BackClick



}
