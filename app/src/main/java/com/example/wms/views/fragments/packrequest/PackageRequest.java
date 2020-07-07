package com.example.wms.views.fragments.packrequest;

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
import com.example.wms.databinding.FragmentPackRequestBinding;
import com.example.wms.viewmodels.packrequest.VM_PackageRequest;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.user.login.Login;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageRequest extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_PackageRequest vm_packageRequest;
    private NavController navController;

    @BindView(R.id.FragmentPackRequestTab)
    SmartTabLayout FragmentPackRequestTab;

    @BindView(R.id.FragmentPackRequestView)
    ViewPager FragmentPackRequestView;

    public PackageRequest() {//_____________________________________________________________________ PackageRequest

    }//_____________________________________________________________________________________________ PackageRequest


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_packageRequest = new VM_PackageRequest(getContext());
            FragmentPackRequestBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request,container,false);
            binding.setVmPackage(vm_packageRequest);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                PackageRequest.this,
                vm_packageRequest.getPublishSubject(),
                vm_packageRequest);
        navController = Navigation.findNavController(getView());
        SetTabs();
    }//_____________________________________________________________________________________________ onStart



    private void SetTabs() {//______________________________________________________________________ SetTabs;

        FragmentPagerItemAdapter adapterLocation = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentPackRequestAddress, FragmentPackRequestAddress.class)
                .create());

        FragmentPagerItemAdapter adapterRequest = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.FragmentPackRequestPrimary, FragmentPackRequestPrimary.class)
                .create());


        FragmentPackRequestView.setAdapter(adapterLocation);
        FragmentPackRequestTab.setViewPager(FragmentPackRequestView);
//        FragmentPackRequestView.setCurrentItem(0);


    }//_____________________________________________________________________________________________ SetTabs



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable



}
