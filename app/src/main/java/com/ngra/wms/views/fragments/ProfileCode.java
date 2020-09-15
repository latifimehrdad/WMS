package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentProfileCodeBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_ProfileCode;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class ProfileCode extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_ProfileCode vm_profileCode;

    @BindView(R.id.editBuildingRenovationCode)
    EditText editBuildingRenovationCode;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentProfileCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_code, container, false);
            vm_profileCode = new VM_ProfileCode(getContext());
            binding.setVmCode(vm_profileCode);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                ProfileCode.this,
                vm_profileCode.getPublishSubject(),
                vm_profileCode);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        setTextWatcher();
        setClick();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissLoading();

        if (action.equals(StaticValues.ML_EditProfile)) {
            return;
        }


        if (action.equals(StaticValues.ML_GetAccountNumberNull)) {
            editBuildingRenovationCode.requestFocus();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable




    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest



    //______________________________________________________________________________________________ setClick
    private void setClick() {


        RelativeLayoutSend.setOnClickListener(v -> {

            if (checkEmpty()) {
                hideKeyboard();
                showLoading();
                vm_profileCode.sendCode();
            }
        });

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        if (vm_profileCode.getBuildingRenovationCode().length() < 1) {
            editBuildingRenovationCode.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editBuildingRenovationCode.setError(getResources().getString(R.string.EmptyAccountNumber));
            editBuildingRenovationCode.requestFocus();
            return false;
        } else
            return true;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        editBuildingRenovationCode.setBackgroundResource(R.drawable.dw_edit_back);
        editBuildingRenovationCode.addTextChangedListener(textChangeForChangeBack(editBuildingRenovationCode));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        txtLoading.setText(getResources().getString(R.string.SaveInfo));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


}
