/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBankBinding;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.profile.VM_FragmentProfileBank;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.dialogs.searchspinner.MLSpinnerDialog;
import com.example.wms.views.dialogs.searchspinner.OnSpinnerItemClick;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class FragmentProfileBank extends Fragment {

    private Context context;
    private VM_FragmentProfileBank vm_fragmentProfileBank;
    private MLSpinnerDialog spinnerBanks;
    private View view;
    private DialogProgress progress;
    private boolean ClickBank = false;
    private ArrayList<ModelSpinnerItem> BanksList;
    private String BankId = "-1";
    private DisposableObserver<Byte> observer;

    @BindView(R.id.LayoutBank)
    LinearLayout LayoutBank;

    @BindView(R.id.TextBank)
    TextView TextBank;

    @BindView(R.id.editAccountNumber)
    EditText editAccountNumber;

    @BindView(R.id.btnSendAccountNumber)
    Button btnSendAccountNumber;


    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (view == null) {
            this.context = getContext();
            FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_bank, container, false
            );
            vm_fragmentProfileBank = new VM_FragmentProfileBank(context);
            binding.setBank(vm_fragmentProfileBank);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            SetTextWatcher();
            SetClick();
        }
        return view;
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservables();
    }//_____________________________________________________________________________________________ onStart


    private void SetClick() {//_____________________________________________________________________ SetClick

        LayoutBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBank = true;
                if ((BanksList == null) || (BanksList.size() == 0))
                    GetBanks();
                else
                    spinnerBanks.showSpinerDialog();
            }
        });

        btnSendAccountNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEmpty()) {
                    ShowProgressDialog(null);
                    vm_fragmentProfileBank.setAccountNumber(editAccountNumber.getText().toString());
                    vm_fragmentProfileBank.setBankId(BankId);
                    vm_fragmentProfileBank.SendAccountNumber();
                }
            }
        });

    }//_____________________________________________________________________________________________ SetClick


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


    private void GetBanks() {//_____________________________________________________________________ GetBanks
        ShowProgressDialog(getResources().getString(R.string.GetBanks));
        vm_fragmentProfileBank.GetBanks();
    }//_____________________________________________________________________________________________ GetBanks


    private void GeuUserAccountNumber() {//_________________________________________________________ GeuUserAccountNumber
        ShowProgressDialog(getResources().getString(R.string.GetUserAccountNumber));
        vm_fragmentProfileBank.GetUserAccountNumber();
    }//_____________________________________________________________________________________________ GeuUserAccountNumber


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editAccountNumber.setBackgroundResource(R.drawable.edit_normal_background);
        editAccountNumber.addTextChangedListener(TextChangeForChangeBack(editAccountNumber));
        LayoutBank.setBackgroundColor(getResources().getColor(R.color.mlEdit));
    }//_____________________________________________________________________________________________ SetTextWatcher


    private void ObserverObservables() {//__________________________________________________________ ObserverObservables

        observer = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progress != null)
                            progress.dismiss();
                        HandleAction(s);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        vm_fragmentProfileBank
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ ObserverObservables



    private void HandleAction(Byte action) {//______________________________________________________ HandleAction
        if (action == StaticValues.ML_EditProfile) {
            ShowMessage(vm_fragmentProfileBank.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check));
        } else if (action == StaticValues.ML_GetAccountNumbers) {
            editAccountNumber.setText(vm_fragmentProfileBank
                    .getAccountNumbers()
                    .getAccountNumber());

            TextBank.setText(vm_fragmentProfileBank
                    .getAccountNumbers()
                    .getBank()
                    .getTitle());

            BankId = vm_fragmentProfileBank
                    .getAccountNumbers()
                    .getBank()
                    .getId();
        } else if (action == StaticValues.ML_GetAccountNumberNull) {
            editAccountNumber.requestFocus();
        } else if (action == StaticValues.ML_GetBanks) {
            BanksList = vm_fragmentProfileBank.getBanks();
            SetItemBanks();
        } else if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        } else if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_fragmentProfileBank.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        }
    }//_____________________________________________________________________________________________ HandleAction



    private void ShowProgressDialog(String title) {//_______________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(context, title);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog


    private void SetItemBanks() {//_________________________________________________________________ SetItemBanks
        TextBank.setText(getResources().getString(R.string.ChooseBank));
        BankId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerBanks = new MLSpinnerDialog(
                getActivity(),
                BanksList,
                getResources().getString(R.string.Bank_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerBanks.setCancellable(true); // for cancellable
        spinnerBanks.setShowKeyboard(false);// for open keyboard by default
        spinnerBanks.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextBank.setText(item);
                BankId = BanksList.get(position).getId();
                LayoutBank.setBackgroundColor(getResources().getColor(R.color.mlEdit));
            }
        });

        if (ClickBank)
            spinnerBanks.showSpinerDialog();
    }//_____________________________________________________________________________________________ SetItemBanks


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getChildFragmentManager());

    }//_____________________________________________________________________________________________ ShowMessage


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        if(observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ onDestroy

}
