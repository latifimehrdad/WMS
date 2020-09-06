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

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentProfileBankBinding;

import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_ProfileBank;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.dialogs.DialogProgress;
import com.ngra.wms.views.dialogs.searchspinner.MLSpinnerDialog;


import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

import static com.ngra.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ProfileBank extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_ProfileBank vm_profileBank;
    private String bankId = "-1";
    private DialogProgress progress;
    private MLSpinnerDialog spinnerBanks;


    @BindView(R.id.LayoutBank)
    LinearLayout LayoutBank;

    @BindView(R.id.TextBank)
    TextView TextBank;

    @BindView(R.id.editAccountNumber)
    EditText editAccountNumber;

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
            FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_bank, container, false);
            vm_profileBank = new VM_ProfileBank(getContext());
            binding.setVmBank(vm_profileBank);
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
                ProfileBank.this,
                vm_profileBank.getPublishSubject(),
                vm_profileBank);
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

        if (progress != null)
            progress.dismiss();

        dismissLoading();

        if (action.equals(StaticValues.ML_EditProfile)) {
            return;
        }

        if (action.equals(StaticValues.ML_GetAccountNumbers)) {
            editAccountNumber.setText(vm_profileBank
                    .getAccountNumbers()
                    .getAccountNumber());

            TextBank.setText(vm_profileBank
                    .getAccountNumbers()
                    .getBank()
                    .getTitle());

            bankId = vm_profileBank
                    .getAccountNumbers()
                    .getBank()
                    .getId();

            return;
        }

        if (action.equals(StaticValues.ML_GetAccountNumberNull)) {
            editAccountNumber.requestFocus();
            return;
        }

        if (action.equals(StaticValues.ML_GetBanks)) {
            setItemBanks();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setItemBanks
    private void setItemBanks() {
        TextBank.setText(getResources().getString(R.string.ChooseBank));
        bankId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerBanks = new MLSpinnerDialog(getActivity(), vm_profileBank.getBanks(), getResources().getString(R.string.Bank_Search), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.Ignore));// With 	Animation
        spinnerBanks.setCancellable(true); // for cancellable
        spinnerBanks.setShowKeyboard(false);// for open keyboard by default
        spinnerBanks.bindOnSpinerListener((item, position) -> {
            TextBank.setText(item);
            bankId = vm_profileBank.getBanks().get(position).getId();
            LayoutBank.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        spinnerBanks.showSpinerDialog();
    }
    //______________________________________________________________________________________________ setItemBanks


    //______________________________________________________________________________________________ setClick
    private void setClick() {

        LayoutBank.setOnClickListener(v -> {
            if ((vm_profileBank.getBanks() == null) || (vm_profileBank.getBanks().size() == 0))
                getBanks();
            else
                spinnerBanks.showSpinerDialog();
        });

        RelativeLayoutSend.setOnClickListener(v -> {

            if (checkEmpty()) {
                hideKeyboard();
                showLoading();
                vm_profileBank.setAccountNumber(editAccountNumber.getText().toString());
                vm_profileBank.setBankId(bankId);
                vm_profileBank.sendAccountNumber();
            }
        });

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ getBanks
    private void getBanks() {
        showProgressDialog(getResources().getString(R.string.GetBanks));
        vm_profileBank.getBankList();
    }
    //______________________________________________________________________________________________ getBanks


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        editAccountNumber.setBackgroundResource(R.drawable.dw_edit_back);
        editAccountNumber.addTextChangedListener(TextChangeForChangeBack(editAccountNumber));
        LayoutBank.setBackgroundResource(R.drawable.dw_edit_back);
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean accountnumbert;
        boolean bank;

        if (editAccountNumber.getText().length() != 26) {
            editAccountNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editAccountNumber.setError(getResources().getString(R.string.EmptyAccountNumber));
            editAccountNumber.requestFocus();
            accountnumbert = false;
        } else
            accountnumbert = true;


        if (editAccountNumber.getText().length() > 3) {
            String IR = editAccountNumber.getText().toString();
            IR = IR.substring(0, 2);

            if (!IR.equalsIgnoreCase("IR") || !IR.equalsIgnoreCase("ir")) {
                editAccountNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
                editAccountNumber.setError(getResources().getString(R.string.EmptyAccountNumber));
                editAccountNumber.requestFocus();
                accountnumbert = false;
            } else
                accountnumbert = true;
        }


        if (bankId.equalsIgnoreCase("-1")) {
            LayoutBank.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            bank = false;
        } else
            bank = true;

        return accountnumbert && bank;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ showProgressDialog
    private void showProgressDialog(String title) {

        if (getContext() != null) {
            progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .showProgress(getContext(), title);
            progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }
    }
    //______________________________________________________________________________________________ showProgressDialog


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
