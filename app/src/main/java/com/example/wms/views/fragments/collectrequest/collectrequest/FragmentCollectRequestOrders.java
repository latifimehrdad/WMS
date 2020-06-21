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
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_FragmentCollectRequestOrders;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentCollectRequestOrders extends Fragment {

    private Context context;
    private VM_FragmentCollectRequestOrders vm_fragmentCollectRequestOrders;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentCollectRequestOrdersBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_orders,container,false
        );
        vm_fragmentCollectRequestOrders = new VM_FragmentCollectRequestOrders(context);
        binding.setCollectorders(vm_fragmentCollectRequestOrders);
        View view = binding.getRoot();
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCollectRequestOrders() {//_______________________________________________________ Start FragmentCollectRequestOrders
    }//_____________________________________________________________________________________________ End FragmentCollectRequestOrders


    @Override
    public void onStart() {
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
