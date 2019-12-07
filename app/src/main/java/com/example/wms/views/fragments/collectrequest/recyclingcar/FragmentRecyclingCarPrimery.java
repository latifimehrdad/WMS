package com.example.wms.views.fragments.collectrequest.recyclingcar;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentRecyclingCarPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.recyclingcar.FragmentRecyclingCarPrimeryViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRecyclingCarPrimery extends Fragment {

    private Context context;
    private FragmentRecyclingCarPrimeryViewModel fragmentRecyclingCarPrimeryViewModel;

    @BindView(R.id.FRCPSpinnerHours)
    MaterialSpinner FRCPSpinnerHours;

    @BindView(R.id.FRCPSpinnerDay)
    MaterialSpinner FRCPSpinnerDay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRecyclingCarPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_recycling_car_primery, container, false
        );
        fragmentRecyclingCarPrimeryViewModel = new FragmentRecyclingCarPrimeryViewModel(context);
        binding.setRecyclingcarprimery(fragmentRecyclingCarPrimeryViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentRecyclingCarPrimery(Context context) {//_________________________________________ Start FragmentRecyclingCarPrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentRecyclingCarPrimery


    public FragmentRecyclingCarPrimery() {//________________________________________________________ Start FragmentRecyclingCarPrimery
    }//_____________________________________________________________________________________________ End FragmentRecyclingCarPrimery


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onCreateView


    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FRCPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
        FRCPSpinnerDay.setItems("روز تحویل","شنبه","سه شنبه");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
        FRCPSpinnerHours.setOnKeyListener(SetKey(FRCPSpinnerHours));
        FRCPSpinnerDay.setOnKeyListener(SetKey(FRCPSpinnerDay));


    }//_____________________________________________________________________________________________ End BackClick




    private View.OnKeyListener SetKey(View view){//_________________________________________________ Start SetKey
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                keyCode = 0;
                event = null;
                MainActivity.FragmentMessage.onNext("CollectRequest");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetKey



}
