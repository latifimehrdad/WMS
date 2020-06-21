package com.example.wms.views.fragments.collectrequest.recyclingcar;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentRecyclingCarPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.recyclingcar.VM_FragmentRecyclingCarPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRecyclingCarPrimery extends Fragment {

    private Context context;
    private VM_FragmentRecyclingCarPrimary vm_fragmentRecyclingCarPrimary;
    private View view;

    @BindView(R.id.FRCPSpinnerHours)
    MaterialSpinner FRCPSpinnerHours;

    @BindView(R.id.FRCPSpinnerDay)
    MaterialSpinner FRCPSpinnerDay;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textTime)
    TextView textTime;



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getActivity();
        FragmentRecyclingCarPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_recycling_car_primery, container, false
        );
        vm_fragmentRecyclingCarPrimary = new VM_FragmentRecyclingCarPrimary(context);
        binding.setRecyclingcarprimery(vm_fragmentRecyclingCarPrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


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

        FRCPSpinnerDay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position != 0)
                    textDate.setText("1398/01/01");
                else
                    textDate.setText("");
            }
        });


        FRCPSpinnerHours.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position == 1)
                    textTime.setText("8 - 10");
                else if (position == 2)
                    textTime.setText("11 - 13");
                else
                    textTime.setText("");
            }
        });
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems



    private View.OnKeyListener SetKey(View view){//_________________________________________________ Start SetBackClickAndGoHome
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode != 4) {
                    return false;
                }
                //MainActivity.FragmentMessage.onNext("CollectRequest");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetBackClickAndGoHome



}
