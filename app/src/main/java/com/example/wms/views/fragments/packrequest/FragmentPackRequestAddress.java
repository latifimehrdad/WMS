package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestAddressBinding;
import com.example.wms.viewmodels.packrequest.FragmentPackRequestAddressViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPackRequestAddress extends Fragment {

    private Context context;
    private FragmentPackRequestAddressViewModel fragmentPackRequestAddressViewModel;

    @BindView(R.id.FPRAMaterialSpinnerType)
    MaterialSpinner FPRAMaterialSpinnerType;

    @BindView(R.id.FPRAMaterialSpinnerTypeCount)
    MaterialSpinner FPRAMaterialSpinnerTypeCount;

    @BindView(R.id.FPRAMaterialSpinnerUser)
    MaterialSpinner FPRAMaterialSpinnerUser;

    @BindView(R.id.FPRAMaterialSpinnerCount)
    MaterialSpinner FPRAMaterialSpinnerCount;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPackRequestAddressBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack_request_address, container, false
        );
        fragmentPackRequestAddressViewModel = new FragmentPackRequestAddressViewModel(context);
        binding.setRequstaddress(fragmentPackRequestAddressViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentPackRequestAddress(Context context) {//__________________________________________ Start FragmentPackRequestAddress
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentPackRequestAddress


    public FragmentPackRequestAddress() {//_________________________________________________________ Start FragmentPackRequestAddress
    }//_____________________________________________________________________________________________ End FragmentPackRequestAddress


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onStart


    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FPRAMaterialSpinnerType.setItems("نوع واحد", "آپارتمان", "ویلایی");
        FPRAMaterialSpinnerTypeCount.setItems("تعداد واحد","1","2");
        FPRAMaterialSpinnerUser.setItems("کاربری ساختمان", "تجاری", "مسکونی");
        FPRAMaterialSpinnerCount.setItems("تعداد نفرات","10","12");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems


}
