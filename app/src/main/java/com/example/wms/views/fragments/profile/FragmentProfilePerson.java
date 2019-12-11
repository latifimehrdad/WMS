/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfilePersonBinding;
import com.example.wms.viewmodels.user.profile.FragmentProfilePersonViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentProfilePerson extends Fragment {

    private Context context;
    private FragmentProfilePersonViewModel fragmentProfilePersonViewModel;

    @BindView(R.id.MaterialSpinner1)
    MaterialSpinner MaterialSpinner1;

    @BindView(R.id.MaterialSpinner2)
    MaterialSpinner MaterialSpinner2;

    @BindView(R.id.MaterialSpinner3)
    MaterialSpinner MaterialSpinner3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfilePersonBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile_person, container, false);
        fragmentProfilePersonViewModel = new FragmentProfilePersonViewModel(context);
        binding.setPerson(fragmentProfilePersonViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentProfilePerson(Context context) {//______________________________________________ Start FragmentProfilePerson
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentProfilePerson


    public FragmentProfilePerson() {//_____________________________________________________________ Start FragmentProfilePerson
    }//_____________________________________________________________________________________________ End FragmentProfilePerson


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        MaterialSpinner1.setItems("انتخاب شهر", "تست 1", "تست 2");
        MaterialSpinner2.setItems("انتخاب منطقه", "تست 1", "تست2");
        MaterialSpinner3.setItems("انتخاب کاربر", "تست 1", "تست 2");
    }//_____________________________________________________________________________________________ End onStart

    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick

}
