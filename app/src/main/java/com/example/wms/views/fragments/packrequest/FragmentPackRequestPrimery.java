package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestPrimeryBinding;
import com.example.wms.viewmodels.packrequest.FragmentPackRequestViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPackRequestPrimery extends Fragment {

    private Context context;
    private FragmentPackRequestViewModel fragmentPackRequestViewModel;

    @BindView(R.id.FPRPSpinnerHours)
    MaterialSpinner FPRPSpinnerHours;

    @BindView(R.id.FPRPSpinnerDay)
    MaterialSpinner FPRPSpinnerDay;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPackRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack_request_primery,container,false
        );
        fragmentPackRequestViewModel = new FragmentPackRequestViewModel(context);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentPackRequestPrimery(Context context) {//__________________________________________ Start FragmentPackRequestPrimery
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentPackRequestPrimery


    public FragmentPackRequestPrimery() {//_________________________________________________________ Start FragmentPackRequestPrimery

    }//_____________________________________________________________________________________________ End FragmentPackRequestPrimery



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetMaterialSpinnersItems();
    }//_____________________________________________________________________________________________ End onStart




    private void SetMaterialSpinnersItems() {//_____________________________________________________ Start SetMaterialSpinnersItems
        FPRPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
        FPRPSpinnerDay.setItems("روز تحویل","شنبه","سه شنبه");
    }//_____________________________________________________________________________________________ End SetMaterialSpinnersItems



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
        FPRPSpinnerHours.setOnKeyListener(SetKey(FPRPSpinnerHours));
        FPRPSpinnerDay.setOnKeyListener(SetKey(FPRPSpinnerDay));


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
                MainActivity.FragmentMessage.onNext("Main");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetKey



}
