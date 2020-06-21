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
import com.example.wms.viewmodels.learn.VM_FragmentLearnSeparation;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentLearnSeparation extends Fragment {

    private Context context;
    private VM_FragmentLearnSeparation vm_fragmentLearnSeparation;
    private View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentLearnSeparationBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learn_separation, container, false
        );
        vm_fragmentLearnSeparation = new VM_FragmentLearnSeparation(context);
        binding.setLearnseparation(vm_fragmentLearnSeparation);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentLearnSeparation() {//____________________________________________________________ Start FragmentLearnSeparation
    }//_____________________________________________________________________________________________ End FragmentLearnSeparation


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart



}
