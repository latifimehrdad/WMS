package com.ngra.wms.views.fragments.packrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentPackRequestBinding;
import com.ngra.wms.viewmodels.packrequest.VM_PackageRequest;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class PackageRequest extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_PackageRequest vm_packageRequest;

    @BindView(R.id.FragmentPackRequestTab)
    SmartTabLayout FragmentPackRequestTab;

    @BindView(R.id.FragmentPackRequestView)
    ViewPager FragmentPackRequestView;

    public PackageRequest() {//_____________________________________________________________________ PackageRequest

    }//_____________________________________________________________________________________________ PackageRequest


    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_packageRequest = new VM_PackageRequest(getContext());
            FragmentPackRequestBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request,container,false);
            binding.setVmPackage(vm_packageRequest);
            setView(binding.getRoot());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setPublishSubjectFromObservable(
                PackageRequest.this,
                vm_packageRequest.getPublishSubject(),
                vm_packageRequest);
        SetTabs();
    }//_____________________________________________________________________________________________ onStart



    private void SetTabs() {//______________________________________________________________________ SetTabs;

        FragmentPagerItemAdapter adapterLocation = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentPackRequestAddress, PackageRequestAddress.class)
                .create());

//        FragmentPagerItemAdapter adapterRequest = new FragmentPagerItemAdapter(
//                getChildFragmentManager(), FragmentPagerItems.with(getContext())
//                .add(R.string.FragmentPackRequestPrimary, PackageRequestPrimary.class)
//                .create());


        FragmentPackRequestView.setAdapter(adapterLocation);
        FragmentPackRequestTab.setViewPager(FragmentPackRequestView);
//        FragmentPackRequestView.setCurrentItem(0);


    }//_____________________________________________________________________________________________ SetTabs



    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable



}
