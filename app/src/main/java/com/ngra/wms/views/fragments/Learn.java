package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLearnBinding;
import com.ngra.wms.viewmodels.VM_Learn;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Learn extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_Learn vm_learn;


    @BindView(R.id.FragmentLearnTab)
    SmartTabLayout FragmentLearnTab;

    @BindView(R.id.FragmentLearnView)
    ViewPager FragmentLearnView;


    //______________________________________________________________________________________________ Learn
    public Learn() {
    }
    //______________________________________________________________________________________________ Learn


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_learn = new VM_Learn(getContext());
            FragmentLearnBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn, container, false);
            binding.setVmLearn(vm_learn);
            setView(binding.getRoot());
            setTabs();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView



    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Learn.this,
                vm_learn.getPublishSubject(),
                vm_learn);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setTabs
    private void setTabs() {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentLearnItems, LearnItems.class)
                .add(R.string.FragmentLearnSeparation, LearnSeparation.class)
                .create());

        FragmentLearnView.setAdapter(adapter);
        FragmentLearnTab.setViewPager(FragmentLearnView);
        FragmentLearnView.setCurrentItem(1);

    }
    //______________________________________________________________________________________________ setTabs

}
