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
import com.example.wms.viewmodels.aboutus.FragmentAboutPrimeryViewModel;

import butterknife.ButterKnife;

public class FragmentAboutPrimery extends Fragment {

    private Context context;
    private FragmentAboutPrimeryViewModel fragmentAboutPrimeryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAboutPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_about_primery, container, false
        );
        fragmentAboutPrimeryViewModel = new FragmentAboutPrimeryViewModel(context);
        binding.setAboutprimery(fragmentAboutPrimeryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentAboutPrimery(Context context) {//________________________________________________ Start FragmentAboutPrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentAboutPrimery


    public FragmentAboutPrimery() {//_______________________________________________________________ Start FragmentAboutPrimery
    }//_____________________________________________________________________________________________ End FragmentAboutPrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart
}
