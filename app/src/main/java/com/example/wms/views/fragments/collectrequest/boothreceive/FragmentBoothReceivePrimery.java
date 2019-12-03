package com.example.wms.views.fragments.collectrequest.boothreceive;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentBoothReceivePrimeryBinding;
import com.example.wms.viewmodels.collectrequest.boothreceive.FragmentBoothReceivePrimeryViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentBoothReceivePrimery extends Fragment {

    private Context context;
    private FragmentBoothReceivePrimeryViewModel fragmentBoothReceivePrimeryViewModel;

    @BindView(R.id.FBRPSpinnerHours)
    MaterialSpinner FBRPSpinnerHours;

    @BindView(R.id.FBRPSpinnerDay)
    MaterialSpinner FBRPSpinnerDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBoothReceivePrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_booth_receive_primery,container,false
        );
        fragmentBoothReceivePrimeryViewModel = new FragmentBoothReceivePrimeryViewModel(context);
        binding.setBoothreceiveprimery(fragmentBoothReceivePrimeryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentBoothReceivePrimery(Context context) {//_________________________________________ Start FragmentBoothReceivePrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentBoothReceivePrimery


    public FragmentBoothReceivePrimery() {//________________________________________________________ Start FragmentBoothReceivePrimery
    }//_____________________________________________________________________________________________ End FragmentBoothReceivePrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onStart


    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FBRPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
        FBRPSpinnerDay.setItems("روز تحویل","شنبه","سه شنبه");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems
}
