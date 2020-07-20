package com.ngra.wms.views.fragments.user.profile;

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
import com.ngra.wms.viewmodels.user.profile.VM_ProfileBank;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.dialogs.DialogProgress;
import com.ngra.wms.views.dialogs.searchspinner.MLSpinnerDialog;
import com.ngra.wms.views.fragments.FragmentPrimary;


import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

import static com.ngra.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ProfileBank extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_ProfileBank vm_profileBank;
    private String BankId = "-1";
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

    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_bank, container, false);
            vm_profileBank = new VM_ProfileBank(getContext());
            binding.setVmBank(vm_profileBank);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                ProfileBank.this,
                vm_profileBank.getPublishSubject(),
                vm_profileBank);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ Start init
        SetTextWatcher();
        SetClick();
    }//_____________________________________________________________________________________________ End init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (progress != null)
            progress.dismiss();

        DismissLoading();

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

            BankId = vm_profileBank
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
            SetItemBanks();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetItemBanks() {//_________________________________________________________________ SetItemBanks
        TextBank.setText(getResources().getString(R.string.ChooseBank));
        BankId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerBanks = new MLSpinnerDialog(getActivity(), vm_profileBank.getBanks(), getResources().getString(R.string.Bank_Search), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.Ignor));// With 	Animation
        spinnerBanks.setCancellable(true); // for cancellable
        spinnerBanks.setShowKeyboard(false);// for open keyboard by default
        spinnerBanks.bindOnSpinerListener((item, position) -> {
            TextBank.setText(item);
            BankId = vm_profileBank.getBanks().get(position).getId();
            LayoutBank.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

        spinnerBanks.showSpinerDialog();
    }//_____________________________________________________________________________________________ SetItemBanks


    private void SetClick() {//_____________________________________________________________________ SetClick

        LayoutBank.setOnClickListener(v -> {
            if ((vm_profileBank.getBanks() == null) || (vm_profileBank.getBanks().size() == 0))
                GetBanks();
            else
                spinnerBanks.showSpinerDialog();
        });

        RelativeLayoutSend.setOnClickListener(v -> {

            if (CheckEmpty()) {
                hideKeyboard();
                ShowLoading();
                vm_profileBank.setAccountNumber(editAccountNumber.getText().toString());
                vm_profileBank.setBankId(BankId);
                vm_profileBank.SendAccountNumber();
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void GetBanks() {//_____________________________________________________________________ GetBanks
        ShowProgressDialog(getResources().getString(R.string.GetBanks));
        vm_profileBank.GetBanks();
    }//_____________________________________________________________________________________________ GetBanks


//    private void GeuUserAccountNumber() {//_________________________________________________________ GeuUserAccountNumber
//        ShowProgressDialog(getResources().getString(R.string.GetUserAccountNumber));
//        vm_profileBank.GetUserAccountNumber();
//    }//_____________________________________________________________________________________________ GeuUserAccountNumber


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editAccountNumber.setBackgroundResource(R.drawable.dw_edit_back);
        editAccountNumber.addTextChangedListener(TextChangeForChangeBack(editAccountNumber));
        LayoutBank.setBackgroundResource(R.drawable.dw_edit_back);
    }//_____________________________________________________________________________________________ SetTextWatcher


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean accountnumbert;
        boolean bank;

        if (editAccountNumber.getText().length() != 26) {
            editAccountNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editAccountNumber.setError(getResources().getString(R.string.EmptyAccountNumber));
            editAccountNumber.requestFocus();
            accountnumbert = false;
        } else
            accountnumbert = true;


        String IR = editAccountNumber.getText().toString();
        IR = IR.substring(0,2);

        if (!IR.equalsIgnoreCase("IR") || !IR.equalsIgnoreCase("ir")) {
            editAccountNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editAccountNumber.setError(getResources().getString(R.string.EmptyAccountNumber));
            editAccountNumber.requestFocus();
            accountnumbert = false;
        } else
            accountnumbert = true;


        if (BankId.equalsIgnoreCase("-1")) {
            LayoutBank.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            bank = false;
        } else
            bank = true;

        return accountnumbert && bank;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void ShowProgressDialog(String title) {//_______________________________________________ ShowProgressDialog

        if (getContext() != null) {
            progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .ShowProgress(getContext(), title);
            progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }
    }//_____________________________________________________________________________________________ ShowProgressDialog


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        txtLoading.setText(getResources().getString(R.string.SaveInfo));
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
