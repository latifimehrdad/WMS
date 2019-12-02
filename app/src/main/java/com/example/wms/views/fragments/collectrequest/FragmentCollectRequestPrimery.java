package com.example.wms.views.fragments.collectrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.FragmentCollectRequestPrimeryViewModel;

import butterknife.ButterKnife;

public class FragmentCollectRequestPrimery extends Fragment {

    private Context context;
    private FragmentCollectRequestPrimeryViewModel fragmentCollectRequestPrimeryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_primery,container,false
        );
        fragmentCollectRequestPrimeryViewModel = new FragmentCollectRequestPrimeryViewModel(context);
        binding.setCollectprimery(fragmentCollectRequestPrimeryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    public FragmentCollectRequestPrimery() {//______________________________________________________ Start FragmentCollectRequestPrimery
    }//_____________________________________________________________________________________________ End FragmentCollectRequestPrimery


    public FragmentCollectRequestPrimery(Context context) {//_______________________________________ Start FragmentCollectRequestPrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentCollectRequestPrimery

    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
