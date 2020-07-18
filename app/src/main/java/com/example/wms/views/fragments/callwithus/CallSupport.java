package com.example.wms.views.fragments.callwithus;

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
import com.example.wms.R;
import com.example.wms.databinding.FragmentSupportBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.callwithus.VM_Support;
import com.example.wms.views.dialogs.searchspinner.MLSpinnerDialog;
import com.example.wms.views.fragments.FragmentPrimary;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import butterknife.BindView;

public class CallSupport extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_Support vm_support;
    private String RequestType;
    private MLSpinnerDialog spinnerRequestType;
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
            SetOnClick();
            ClickType = false;
            vm_support.GetAllDepartments();
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

        DismissLoading();

        if (action.equals(StaticValues.ML_GetAllDepartments)) {
            SetItemRegion();
            return;
        }

//        if (action.equals(StaticValues.ML_Success))
//            getContext().onBackPressed();

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetOnClick() {//___________________________________________________________________ SetOnClick
        LayoutDepartments.setOnClickListener(v -> {
            ClickType = true;
            if (vm_support.getMd_spinnerItems() == null)
                vm_support.GetAllDepartments();
            else
                SetItemRegion();
        });


        RelativeLayoutSend.setOnClickListener(v -> {

            if (CheckEmpty()) {
                hideKeyboard();
                ShowLoading();
                vm_support.SubmitTicket(RequestType,
                        EditTextSubject.getText().toString(),
                        EditTextDescription.getText().toString());
            }
        });


    }//_____________________________________________________________________________________________ SetOnClick



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean subject ;
        boolean Description;
        boolean type;

        if (EditTextDescription.getText().length() < 1) {
            EditTextDescription.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextDescription.setError(getResources().getString(R.string.EmptyLastName));
            EditTextDescription.requestFocus();
            Description = false;
        } else
            Description = true;


        if (EditTextSubject.getText().length() < 1) {
            EditTextSubject.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditTextSubject.setError(getResources().getString(R.string.EmptyFirstName));
            EditTextSubject.requestFocus();
            subject = false;
        } else
            subject = true;


        if (RequestType.equalsIgnoreCase("-1")) {
            LayoutDepartmentsBack.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            type = false;
        } else
            type = true;


        return Description && subject && type;

    }//_____________________________________________________________________________________________ CheckEmpty




    private void SetItemRegion() {//________________________________________________________________ SetItemRegion

        TextViewDepartments.setText(getResources().getString(R.string.ChooseRequestType));
        RequestType = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerRequestType = new MLSpinnerDialog(
                getActivity(),
                vm_support.getMd_spinnerItems(),
                getResources().getString(R.string.Region_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerRequestType.setCancellable(true); // for cancellable
        spinnerRequestType.setShowKeyboard(false);// for open keyboard by default
        spinnerRequestType.bindOnSpinerListener((item, position) -> {
            TextViewDepartments.setText(item);
            RequestType = vm_support.getMd_spinnerItems().get(position).getId();
            LayoutDepartmentsBack.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        if (ClickType)
            spinnerRequestType.showSpinerDialog();

    }//_____________________________________________________________________________________________ SetItemRegion



    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        txtLoading.setText(getResources().getString(R.string.SendMessage));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading



}
