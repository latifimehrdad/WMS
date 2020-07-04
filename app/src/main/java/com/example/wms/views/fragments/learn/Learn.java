package com.example.wms.views.fragments.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnBinding;
import com.example.wms.viewmodels.learn.VM_Learn;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.packrequest.PackageRequest;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Learn extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_Learn vm_learn;


    @BindView(R.id.FragmentLearnTab)
    SmartTabLayout FragmentLearnTab;

    @BindView(R.id.FragmentLearnView)
    ViewPager FragmentLearnView;



    public Learn() {//______________________________________________________________________________ Learn
    }//_____________________________________________________________________________________________ Learn


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_learn = new VM_Learn(getContext());
            FragmentLearnBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn, container, false);
            binding.setVmLearn(vm_learn);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetTabs();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________ Start
        super.onStart();
        setGetMessageFromObservable(
                Learn.this,
                vm_learn.getPublishSubject(),
                vm_learn);

    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetTabs() {//______________________________________________________________________ Start SetTabs

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentLearnItems, LearnItems.class)
                .add(R.string.FragmentLearnSeparation, LearnSeparation.class)
                .create());

        FragmentLearnView.setAdapter(adapter);
        FragmentLearnTab.setViewPager(FragmentLearnView);
        FragmentLearnView.setCurrentItem(1);

    }//_____________________________________________________________________________________________ End SetTabs


}
