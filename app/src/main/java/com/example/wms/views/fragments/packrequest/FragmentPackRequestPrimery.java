package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestPrimeryBinding;
import com.example.wms.viewmodels.packrequest.VM_FragmentPackRequest;
import com.example.wms.viewmodels.packrequest.VM_FragmentPackRequestPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentPackRequestPrimery extends Fragment {

    private Context context;
    private VM_FragmentPackRequestPrimary vm_fragmentPackRequestPrimary;
    private View view;

    @BindView(R.id.FPRPSpinnerHours)
    MaterialSpinner FPRPSpinnerHours;

    @BindView(R.id.FPRPSpinnerDay)
    MaterialSpinner FPRPSpinnerDay;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textTime)
    TextView textTime;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getContext();
        FragmentPackRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack_request_primery,container,false
        );
        vm_fragmentPackRequestPrimary = new VM_FragmentPackRequestPrimary(context);
        binding.setRequestprimery(vm_fragmentPackRequestPrimary);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onStart




    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FPRPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
        FPRPSpinnerDay.setItems("روز تحویل","شنبه","سه شنبه");

        FPRPSpinnerDay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position != 0)
                    textDate.setText("1398/01/01");
                else
                    textDate.setText("");
            }
        });


        FPRPSpinnerHours.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
