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
import com.example.wms.databinding.FragmentRegisterCodeBinding;
import com.example.wms.viewmodels.register.FragmentRegisterCodeViewModel;

import butterknife.ButterKnife;

public class FragmentRegisterCode extends Fragment {

    private Context context;
    private FragmentRegisterCodeViewModel fragmentRegisterCodeViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterCodeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_register_code,container,false
        );
        fragmentRegisterCodeViewModel = new FragmentRegisterCodeViewModel(context);
        binding.setCode(fragmentRegisterCodeViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentRegisterCode() {//_______________________________________________________________ Start FragmentRegisterCode
    }//_____________________________________________________________________________________________ End FragmentRegisterCode


    public FragmentRegisterCode(Context context) {//________________________________________________ Start FragmentRegisterCode
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRegisterCode


}
