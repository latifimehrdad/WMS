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
import com.example.wms.databinding.FragmentProfileCodeBinding;
import com.example.wms.viewmodels.user.profile.FragmentProfileCodeViewModel;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentProfileCode extends Fragment {

    private Context context;
    private FragmentProfileCodeViewModel fragmentProfileCodeViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileCodeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_code,container,false
        );
        fragmentProfileCodeViewModel = new FragmentProfileCodeViewModel(context);
        binding.setCode(fragmentProfileCodeViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentProfileCode() {//_______________________________________________________________ Start FragmentProfileCode
    }//_____________________________________________________________________________________________ End FragmentProfileCode


    public FragmentProfileCode(Context context) {//________________________________________________ Start FragmentProfileCode
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentProfileCode


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick



}
