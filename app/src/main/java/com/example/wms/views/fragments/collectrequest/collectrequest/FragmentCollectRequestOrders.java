package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestOrdersBinding;
import com.example.wms.viewmodels.collectrequest.collectrequest.FragmentCollectRequestOrdersViewModel;

public class FragmentCollectRequestOrders extends Fragment {

    private Context context;
    private FragmentCollectRequestOrdersViewModel fragmentCollectRequestOrdersViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCollectRequestOrdersBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_orders,container,false
        );
        fragmentCollectRequestOrdersViewModel = new FragmentCollectRequestOrdersViewModel(context);
        binding.setCollectorders(fragmentCollectRequestOrdersViewModel);
        View view = binding.getRoot();
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCollectRequestOrders(Context context) {//________________________________________ Start FragmentCollectRequestOrders
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentCollectRequestOrders


    public FragmentCollectRequestOrders() {//_______________________________________________________ Start FragmentCollectRequestOrders
    }//_____________________________________________________________________________________________ End FragmentCollectRequestOrders


    @Override
    public void onStart() {
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
