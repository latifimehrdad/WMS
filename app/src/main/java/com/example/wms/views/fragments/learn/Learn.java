package com.example.wms.views.fragments.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnBinding;
import com.example.wms.viewmodels.learn.VM_Learn;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.user.login.Login;

import butterknife.ButterKnife;

public class Learn extends FragmentPrimary {

    private NavController navController;
    private VM_Learn vm_learn;


    public Learn() {//______________________________________________________________________________ Learn
    }//_____________________________________________________________________________________________ Learn


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_learn = new VM_Learn(getContext());
            FragmentLearnBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn, container, false);
            binding.setVmLearn(vm_learn);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________ Start
        super.onStart();
        navController = Navigation.findNavController(getView());
        SetTabs();
    }//_____________________________________________________________________________________________ End onCreateView



}
