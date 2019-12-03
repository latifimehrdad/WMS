/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentRegisterBinding;
import com.example.wms.viewmodels.register.FragmentRegisterViewModel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRegister extends Fragment {

    private Context context;
    private FragmentRegisterViewModel fragmentRegisterViewModel;

    @BindView(R.id.FragmentRegisteryTab)
    SmartTabLayout FragmentRegisteryTab;

    @BindView(R.id.FragmentRegisteryView)
    ViewPager FragmentRegisteryView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_register, container, false
        );
        fragmentRegisterViewModel = new FragmentRegisterViewModel(context);
        binding.setRegister(fragmentRegisterViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentRegister(Context context) {//____________________________________________________ Start FragmentRegister
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRegister


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetTabs();
    }//_____________________________________________________________________________________________ End onStart


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentRegisterCode fragmentRegisterCode = new FragmentRegisterCode(context);
        FragmentRegisterBank fragmentRegisterBank = new FragmentRegisterBank(context);
        FragmentRegisterPerson fragmentRegisterPerson = new FragmentRegisterPerson(context);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(context)
                .add(R.string.RegisterPerson, fragmentRegisterPerson.getClass())
                .add(R.string.RegisterPersonBank, fragmentRegisterBank.getClass())
                .add(R.string.RegisterPersonCodeNew, fragmentRegisterCode.getClass())
                .create());

        FragmentRegisteryView.setAdapter(adapter);
        FragmentRegisteryTab.setViewPager(FragmentRegisteryView);

    }//_____________________________________________________________________________________________ End SetTabs


}
