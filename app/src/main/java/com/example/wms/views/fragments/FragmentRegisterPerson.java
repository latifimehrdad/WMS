/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentRegisterPersonBinding;
import com.example.wms.viewmodels.FragmentRegisterPersonViewModel;

import butterknife.ButterKnife;

public class FragmentRegisterPerson extends Fragment {

    private Context context;
    private FragmentRegisterPersonViewModel fragmentRegisterPersonViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterPersonBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_register_person, container, false);
        fragmentRegisterPersonViewModel = new FragmentRegisterPersonViewModel(context);
        binding.setPerson(fragmentRegisterPersonViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentRegisterPerson(Context context) {//______________________________________________ Start FragmentRegisterPerson
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRegisterPerson


    public FragmentRegisterPerson() {//_____________________________________________________________ Start FragmentRegisterPerson
    }//_____________________________________________________________________________________________ End FragmentRegisterPerson


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
