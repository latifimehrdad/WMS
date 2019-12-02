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

import com.example.wms.R;
import com.example.wms.databinding.FragmentRegisterBankBinding;
import com.example.wms.viewmodels.register.FragmentRegisterBankViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRegisterBank extends Fragment {

    private Context context;
    private FragmentRegisterBankViewModel fragmentRegisterBankViewModel;

    @BindView(R.id.MaterialSpinner1)
    MaterialSpinner MaterialSpinner1;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBankBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_register_bank,container, false
        );
        fragmentRegisterBankViewModel = new FragmentRegisterBankViewModel(context);
        binding.setBank(fragmentRegisterBankViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this,view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentRegisterBank(Context context) {//________________________________________________ Start FragmentRegisterBank
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRegisterBank


    public FragmentRegisterBank() {//_______________________________________________________________ Start FragmentRegisterBank
    }//_____________________________________________________________________________________________ End FragmentRegisterBank


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        MaterialSpinner1.setItems("انتخاب بانک", "ملی", "ملت");
    }//_____________________________________________________________________________________________ End onStart


}
