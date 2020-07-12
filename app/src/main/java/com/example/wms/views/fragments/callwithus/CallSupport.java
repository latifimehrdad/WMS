package com.example.wms.views.fragments.callwithus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.FragmentSupportBinding;
import com.example.wms.viewmodels.callwithus.VM_Support;
import com.example.wms.views.fragments.FragmentPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CallSupport extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_Support vm_support;

    @BindView(R.id.MaterialSpinner1)
    MaterialSpinner MaterialSpinner1;

    @BindView(R.id.fsEditSubject)
    EditText fsEditSubject;

    @BindView(R.id.fsEditDescription)
    EditText fsEditDescription;

    public CallSupport() {//________________________________________________________________________ CallSupport
    }//_____________________________________________________________________________________________ CallSupport



    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_support = new VM_Support(getContext());
            FragmentSupportBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_support,container, false
            );
            binding.setVMSupport(vm_support);
            setView(binding.getRoot());
            MaterialSpinner1.setItems("نوع درخواست", "درخواست 1", "درخواست 2");
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                CallSupport.this,
                vm_support.getPublishSubject(),
                vm_support);
    }//_____________________________________________________________________________________________ onStart



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
