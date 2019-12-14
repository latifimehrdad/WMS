/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBinding;
import com.example.wms.viewmodels.user.profile.FragmentProfileViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentProfile extends Fragment {

    private Context context;
    private FragmentProfileViewModel fragmentProfileViewModel;

    @BindView(R.id.FragmentRegisteryTab)
    SmartTabLayout FragmentRegisteryTab;

    @BindView(R.id.FragmentRegisteryView)
    ViewPager FragmentRegisteryView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile, container, false
        );
        fragmentProfileViewModel = new FragmentProfileViewModel(context);
        binding.setProfile(fragmentProfileViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentProfile(Context context) {//____________________________________________________ Start FragmentProfile
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentProfile


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentProfileCode fragmentProfileCode = new FragmentProfileCode(context);
        FragmentProfileBank fragmentProfileBank = new FragmentProfileBank(context);
        FragmentProfilePerson fragmentProfilePerson = new FragmentProfilePerson(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.RegisterPersonCodeNew, fragmentProfileCode.getClass())
                .add(R.string.RegisterPersonBank, fragmentProfileBank.getClass())
                .add(R.string.RegisterPerson, fragmentProfilePerson.getClass())
                .create());

        FragmentRegisteryView.setAdapter(adapter);
        FragmentRegisteryTab.setViewPager(FragmentRegisteryView);
        FragmentRegisteryView.setCurrentItem(2);

    }//_____________________________________________________________________________________________ End SetTabs



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick


}
