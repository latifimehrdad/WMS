package com.example.wms.views.fragments.collectrequest.collectrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.collectrequest.VM_FragmentCollectRequestPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentCollectRequestPrimery extends Fragment {

    private Context context;
    private VM_FragmentCollectRequestPrimary vm_fragmentCollectRequestPrimary;
    private View view;
    private NavController navController;

    @BindView(R.id.fcrpRecyclingCar)
    LinearLayout fcrpRecyclingCar;

    @BindView(R.id.fcrpBoothReceive)
    LinearLayout fcrpBoothReceive;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_primery, container, false
        );
        vm_fragmentCollectRequestPrimary = new VM_FragmentCollectRequestPrimary(context);
        binding.setCollectprimery(vm_fragmentCollectRequestPrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentCollectRequestPrimery() {//______________________________________________________ Start FragmentCollectRequestPrimery
    }//_____________________________________________________________________________________________ End FragmentCollectRequestPrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        SetClicks();
    }//_____________________________________________________________________________________________ End onStart


    private void SetClicks() {//____________________________________________________________________ Start

        fcrpRecyclingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentCollectRequest_to_fragmentRecyclingCar);
            }
        });

        fcrpBoothReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragmentCollectRequest_to_fragmentBoothReceive);
            }
        });

    }//_____________________________________________________________________________________________ End


}
