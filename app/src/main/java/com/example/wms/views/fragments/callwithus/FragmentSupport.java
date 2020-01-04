package com.example.wms.views.fragments.callwithus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentSupportBinding;
import com.example.wms.viewmodels.callwithus.VM_FragmentSupport;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;

public class FragmentSupport extends Fragment {

    private Context context;
    private VM_FragmentSupport vm_fragmentSupport;
    private View view;

    @BindView(R.id.MaterialSpinner1)
    MaterialSpinner MaterialSpinner1;

    @BindView(R.id.fsEditSubject)
    EditText fsEditSubject;

    @BindView(R.id.fsEditDescription)
    EditText fsEditDescription;


    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentSupportBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_support,container, false
        );
        vm_fragmentSupport = new VM_FragmentSupport(context);
        binding.setSupport(vm_fragmentSupport);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentSupport() {//____________________________________________________________________ Start FragmentSupport
    }//_____________________________________________________________________________________________ End FragmentProfilePerson


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        MaterialSpinner1.setItems("نوع درخواست", "درخواست 1", "درخواست 2");
    }//_____________________________________________________________________________________________ End onStart



}
