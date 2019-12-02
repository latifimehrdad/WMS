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
import com.example.wms.databinding.FragmentRegisterPersonBinding;
import com.example.wms.viewmodels.register.FragmentRegisterPersonViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRegisterPerson extends Fragment {

    private Context context;
    private FragmentRegisterPersonViewModel fragmentRegisterPersonViewModel;

    @BindView(R.id.MaterialSpinner1)
    MaterialSpinner MaterialSpinner1;

    @BindView(R.id.MaterialSpinner2)
    MaterialSpinner MaterialSpinner2;

    @BindView(R.id.MaterialSpinner3)
    MaterialSpinner MaterialSpinner3;

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
        MaterialSpinner1.setItems("انتخاب شهر", "تست 1", "تست 2");
        MaterialSpinner2.setItems("انتخاب منطقه", "تست 1", "تست2");
        MaterialSpinner3.setItems("انتخاب کاربر", "تست 1", "تست 2");
    }//_____________________________________________________________________________________________ End onStart


}
