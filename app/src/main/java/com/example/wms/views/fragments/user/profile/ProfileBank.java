package com.example.wms.views.fragments.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBankBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.profile.VM_ProfileBank;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.dialogs.searchspinner.MLSpinnerDialog;
import com.example.wms.views.dialogs.searchspinner.OnSpinnerItemClick;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.user.register.VerifyCode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

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

    @BindView(R.id.btnSendAccountNumber)
    Button btnSendAccountNumber;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_bank, container, false);
            vm_profileBank = new VM_ProfileBank(getContext());
            binding.setVmBank(vm_profileBank);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
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
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        setAccessClick(true);

        if (progress != null)
            progress.dismiss();

        if (action == StaticValues.ML_EditProfile) {
            return;
        }

        if (action == StaticValues.ML_GetAccountNumbers) {
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

        if (action == StaticValues.ML_GetAccountNumberNull) {
            editAccountNumber.requestFocus();
            return;
        }

        if (action == StaticValues.ML_GetBanks) {
            SetItemBanks();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetItemBanks() {//_________________________________________________________________ SetItemBanks
        TextBank.setText(getResources().getString(R.string.ChooseBank));
        BankId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerBanks = new MLSpinnerDialog(
                getActivity(),
                vm_profileBank.getBanks(),
                getResources().getString(R.string.Bank_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerBanks.setCancellable(true); // for cancellable
        spinnerBanks.setShowKeyboard(false);// for open keyboard by default
        spinnerBanks.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextBank.setText(item);
                BankId = vm_profileBank.getBanks().get(position).getId();
                LayoutBank.setBackgroundColor(getResources().getColor(R.color.mlEdit));
            }
        });

        spinnerBanks.showSpinerDialog();
    }//_____________________________________________________________________________________________ SetItemBanks


    private void SetClick() {//_____________________________________________________________________ SetClick

        LayoutBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((vm_profileBank.getBanks() == null) || (vm_profileBank.getBanks().size() == 0))
                    GetBanks();
                else
                    spinnerBanks.showSpinerDialog();
            }
        });

        btnSendAccountNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAccessClick())
                    return;

                if (CheckEmpty()) {
                    setAccessClick(false);
                    hideKeyboard();
                    ShowProgressDialog(null);
                    vm_profileBank.setAccountNumber(editAccountNumber.getText().toString());
                    vm_profileBank.setBankId(BankId);
                    vm_profileBank.SendAccountNumber();
                }
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void GetBanks() {//_____________________________________________________________________ GetBanks
        ShowProgressDialog(getResources().getString(R.string.GetBanks));
        vm_profileBank.GetBanks();
    }//_____________________________________________________________________________________________ GetBanks


    private void GeuUserAccountNumber() {//_________________________________________________________ GeuUserAccountNumber
        ShowProgressDialog(getResources().getString(R.string.GetUserAccountNumber));
        vm_profileBank.GetUserAccountNumber();
    }//_____________________________________________________________________________________________ GeuUserAccountNumber


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editAccountNumber.setBackgroundResource(R.drawable.edit_normal_background);
        editAccountNumber.addTextChangedListener(TextChangeForChangeBack(editAccountNumber));
        LayoutBank.setBackgroundColor(getResources().getColor(R.color.mlEdit));
    }//_____________________________________________________________________________________________ SetTextWatcher


    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean accountnumbert = false;
        boolean bank = false;

        if (editAccountNumber.getText().length() < 1) {
            editAccountNumber.setBackgroundResource(R.drawable.edit_empty_background);
            editAccountNumber.setError(getResources().getString(R.string.EmptyAccountNumber));
            editAccountNumber.requestFocus();
            accountnumbert = false;
        } else
            accountnumbert = true;

        if (BankId.equalsIgnoreCase("-1")) {
            LayoutBank.setBackground(getResources().getDrawable(R.drawable.edit_empty_background));
            bank = false;
        } else
            bank = true;

        if (accountnumbert && bank)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void ShowProgressDialog(String title) {//_______________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(getContext(), title);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog


}
