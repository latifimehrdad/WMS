package com.example.wms.views.fragments.aboutus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentAboutPrimeryBinding;
import com.example.wms.viewmodels.aboutus.VM_FragmentAboutPrimary;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentAboutPrimery extends Fragment {

    private Context context;
    private VM_FragmentAboutPrimary vm_fragmentAboutPrimary;
    private View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentAboutPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_about_primery, container, false
        );
        vm_fragmentAboutPrimary = new VM_FragmentAboutPrimary(context);
        binding.setAboutprimery(vm_fragmentAboutPrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentAboutPrimery() {//_______________________________________________________________ Start FragmentAboutPrimery
    }//_____________________________________________________________________________________________ End FragmentAboutPrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
