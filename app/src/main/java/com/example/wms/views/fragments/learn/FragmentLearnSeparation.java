package com.example.wms.views.fragments.learn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnSeparationBinding;
import com.example.wms.viewmodels.learn.FragmentLearnSeparationViewmodel;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLearnSeparation extends Fragment {

    private Context context;
    private FragmentLearnSeparationViewmodel fragmentLearnSeparationViewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLearnSeparationBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learn_separation, container, false
        );
        fragmentLearnSeparationViewmodel = new FragmentLearnSeparationViewmodel(context);
        binding.setLearnseparation(fragmentLearnSeparationViewmodel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentLearnSeparation(Context context) {//_____________________________________________ Start FragmentLearnSeparation
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentLearnSeparation


    public FragmentLearnSeparation() {//____________________________________________________________ Start FragmentLearnSeparation
    }//_____________________________________________________________________________________________ End FragmentLearnSeparation


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetBackClickAndGoHome(true));
    }//_____________________________________________________________________________________________ End BackClick



}
