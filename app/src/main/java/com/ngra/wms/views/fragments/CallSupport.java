package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentSupportBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Support;
import com.ngra.wms.views.dialogs.searchspinner.MLSpinnerDialog;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class CallSupport extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_Support vm_support;
    private String DepartmentId;
    private String CategoryId;
    private boolean ClickType;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.EditTextSubject)
    EditText EditTextSubject;

    @BindView(R.id.EditTextDescription)
    EditText EditTextDescription;

    @BindView(R.id.TextViewDepartments)
    TextView TextViewDepartments;

    @BindView(R.id.LayoutDepartmentsBack)
    LinearLayout LayoutDepartmentsBack;

    @BindView(R.id.LayoutDepartments)
    LinearLayout LayoutDepartments;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;


    //______________________________________________________________________________________________ CallSupport
    public CallSupport() {
    }
    //______________________________________________________________________________________________ CallSupport


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_support = new VM_Support(getContext());
            FragmentSupportBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_support, container, false
            );
            binding.setVMSupport(vm_support);
            setView(binding.getRoot());
            setOnClick();
            ClickType = false;
            vm_support.getAllDepartments();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                CallSupport.this,
                vm_support.getPublishSubject(),
                vm_support);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissLoading();

        if (action.equals(StaticValues.ML_GetAllDepartments)) {
            setItemCategory();
            return;
        }

        if (action.equals(StaticValues.ML_Success))
            if (getContext() != null)
                getContext().onBackPressed();

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ SetOnClick
    private void setOnClick() {
        LayoutDepartments.setOnClickListener(v -> {
            ClickType = true;
            if (vm_support.getMd_spinnerItems() == null)
                vm_support.getAllDepartments();
            else
                setItemCategory();
        });

        RelativeLayoutSend.setOnClickListener(v -> {
            if (checkEmpty()) {
                hideKeyboard();
                showLoading();
                vm_support.submitTicket(
                        DepartmentId,
                        EditTextSubject.getText().toString(),
                        EditTextDescription.getText().toString(),
                        CategoryId);
            }
        });

    }
    //______________________________________________________________________________________________ SetOnClick


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean subject;
        boolean Description;
        boolean type;

        if (EditTextDescription.getText().length() < 1) {
            EditTextDescription.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextDescription.setError(getResources().getString(R.string.EmptyDescription));
            EditTextDescription.requestFocus();
            Description = false;
        } else
            Description = true;


        if (EditTextSubject.getText().length() < 1) {
            EditTextSubject.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextSubject.setError(getResources().getString(R.string.EmptySubject));
            EditTextSubject.requestFocus();
            subject = false;
        } else
            subject = true;


        if (CategoryId.equalsIgnoreCase("-1")) {
            LayoutDepartmentsBack.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            type = false;
        } else
            type = true;


        return Description && subject && type;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ setItemCategory
    private void setItemCategory() {

        TextViewDepartments.setText(getResources().getString(R.string.ChooseRequestType));
        CategoryId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        MLSpinnerDialog spinnerRequestType = new MLSpinnerDialog(
                getActivity(),
                vm_support.getMd_spinnerItems(),
                getResources().getString(R.string.Region_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignore));// With 	Animation
        spinnerRequestType.setCancellable(true); // for cancellable
        spinnerRequestType.setShowKeyboard(false);// for open keyboard by default
        spinnerRequestType.bindOnSpinerListener((item, position) -> {
            TextViewDepartments.setText(item);
            CategoryId = vm_support.getMd_spinnerItems().get(position).getId();
            DepartmentId = vm_support.getMd_spinnerItems().get(position).getData();
            LayoutDepartmentsBack.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        if (ClickType)
            spinnerRequestType.showSpinerDialog();

    }
    //______________________________________________________________________________________________ setItemCategory


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        txtLoading.setText(getResources().getString(R.string.SendMessage));
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
