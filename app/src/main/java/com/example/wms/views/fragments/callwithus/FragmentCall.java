package com.example.wms.views.fragments.callwithus;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.wms.R;
import com.example.wms.databinding.FragmentCallBinding;
import com.example.wms.viewmodels.callwithus.FragmentCallWithUsViewModel;
import com.example.wms.views.activitys.MainActivity;

import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentCall extends Fragment {

    private Context context;
    private FragmentCallWithUsViewModel fragmentCallWithUsViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCallBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_call, container, false
        );
        fragmentCallWithUsViewModel = new FragmentCallWithUsViewModel(context);
        binding.setCall(fragmentCallWithUsViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView

    public FragmentCall(Context context) {//________________________________________________________ Start FragmentCall
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentCall


    public FragmentCall() {//_______________________________________________________________________ Start FragmentCall
    }//_____________________________________________________________________________________________ End FragmentCall


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart




    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick



}
