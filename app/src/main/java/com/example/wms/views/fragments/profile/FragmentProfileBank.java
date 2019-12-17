/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
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
import com.example.wms.viewmodels.user.profile.FragmentProfileBankViewModel;
import com.example.wms.views.activitys.MainActivity;
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

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;
import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class FragmentProfileBank extends Fragment {

    private Context context;
    private FragmentProfileBankViewModel fragmentProfileBankViewModel;
    private MLSpinnerDialog spinnerBanks;
    private View view;
    private DialogProgress progress;
    private boolean ClickBank = false;
    private ArrayList<ModelSpinnerItem> BanksList;
    private String BankId = "-1";

    @BindView(R.id.LayoutBank)
    LinearLayout LayoutBank;

    @BindView(R.id.TextBank)
    TextView TextBank;

    @BindView(R.id.editAccountNumber)
    EditText editAccountNumber;

    @BindView(R.id.btnSendAccountNumber)
    Button btnSendAccountNumber;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getContext();
        FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_bank, container, false
        );
        fragmentProfileBankViewModel = new FragmentProfileBankViewModel(context);
        binding.setBank(fragmentProfileBankViewModel);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        ObserverObservables();
        SetTextWatcher();
        SetClick();

    }//_____________________________________________________________________________________________ End onStart


    private void SetClick() {//_____________________________________________________________________ Start SetClick

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
                    fragmentProfileBankViewModel.setAccountNumber(editAccountNumber.getText().toString());
                    fragmentProfileBankViewModel.setBankId(BankId);
                    fragmentProfileBankViewModel.SendAccountNumber();
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

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


    }//_____________________________________________________________________________________________ End CheckEmpty


    private void GetBanks() {//_____________________________________________________________________ Start GetBanks
        ShowProgressDialog(getResources().getString(R.string.GetBanks));
        fragmentProfileBankViewModel.GetBanks();
    }//_____________________________________________________________________________________________ End GetBanks


    private void GeuUserAccountNumber() {//_________________________________________________________ Start GeuUserAccountNumber
        ShowProgressDialog(getResources().getString(R.string.GetUserAccountNumber));
        fragmentProfileBankViewModel.GetUserAccountNumber();
    }//_____________________________________________________________________________________________ End GeuUserAccountNumber


    private void BackClick(boolean execute) {//_____________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetBackClickAndGoHome(execute));
    }//_____________________________________________________________________________________________ End BackClick


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        editAccountNumber.setBackgroundResource(R.drawable.edit_normal_background);
        editAccountNumber.addTextChangedListener(TextChangeForChangeBack(editAccountNumber));

        LayoutBank.setBackgroundColor(getResources().getColor(R.color.mlEdit));

    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        fragmentProfileBankViewModel
                .Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progress != null)
                                    progress.dismiss();
                                switch (s) {
                                    case "SuccessfulNull":
                                        editAccountNumber.requestFocus();
                                        break;
                                    case "SuccessfulBank":
                                        BanksList = fragmentProfileBankViewModel.getBanks();
                                        SetItemBanks();
                                        break;
                                    case "SuccessfulEdit":
                                        ShowMessage(fragmentProfileBankViewModel.getMessageResponcse()
                                                , getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_check));
                                        break;
                                    case "SuccessfulGetAccount":
                                        editAccountNumber.setText(fragmentProfileBankViewModel
                                                .getAccountNumbers()
                                                .getAccountNumber());

                                        TextBank.setText(fragmentProfileBankViewModel
                                                .getAccountNumbers()
                                                .getBank()
                                                .getTitle());

                                        BankId = fragmentProfileBankViewModel
                                                .getAccountNumbers()
                                                .getBank()
                                                .getId();
                                        break;
                                    case "Failure":
                                        ShowMessage(getResources().getString(R.string.NetworkError),
                                                getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


    private void ShowProgressDialog(String title) {//_______________________________________________ Start ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(context, title);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog


    private void SetItemBanks() {//_________________________________________________________________ Start SetItemBanks
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
    }//_____________________________________________________________________________________________ End SetItemBanks


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getChildFragmentManager());

    }//_____________________________________________________________________________________________ End ShowMessage


    @Override
    public void setMenuVisibility(boolean menuVisible) {//___________________________________________ Start setMenuVisibility
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BackClick(MainActivity.complateprofile);
                    GeuUserAccountNumber();
                }
            },100);

        }
    }//_____________________________________________________________________________________________ End setMenuVisibility

}
