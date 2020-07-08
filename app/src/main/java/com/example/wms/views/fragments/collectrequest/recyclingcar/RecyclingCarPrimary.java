package com.example.wms.views.fragments.collectrequest.recyclingcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentRecyclingCarPrimeryBinding;
import com.example.wms.viewmodels.collectrequest.recyclingcar.VM_RecyclingCarPrimary;
import com.example.wms.views.fragments.FragmentPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclingCarPrimary extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_RecyclingCarPrimary vm_recyclingCarPrimary;

    @BindView(R.id.FRCPSpinnerHours)
    MaterialSpinner FRCPSpinnerHours;

    @BindView(R.id.FRCPSpinnerDay)
    MaterialSpinner FRCPSpinnerDay;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textTime)
    TextView textTime;

    public RecyclingCarPrimary() {//________________________________________________________________ RecyclingCarPrimary
    }//_____________________________________________________________________________________________ RecyclingCarPrimary


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (getView() == null) {
            vm_recyclingCarPrimary = new VM_RecyclingCarPrimary(getContext());
            FragmentRecyclingCarPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_recycling_car_primery, container, false
            );
            binding.setVMRecyclingPrimary(vm_recyclingCarPrimary);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            SetMaterialSpinnersItems();
        }
        return getView();
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        setGetMessageFromObservable(
                RecyclingCarPrimary.this,
                vm_recyclingCarPrimary.getPublishSubject(),
                vm_recyclingCarPrimary);
    }//_____________________________________________________________________________________________ End onCreateView


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable


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


}
