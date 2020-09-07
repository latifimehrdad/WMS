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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrWalletBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_NewWallet;
import com.ngra.wms.views.application.ApplicationWMS;
import com.yangp.ypwaveview.YPWaveView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class NewWallet extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_NewWallet vm_newWallet;
    private Integer totalPrice;
    private Integer theLowestHarvest;
    private Integer theHighestAmountThatCanBeWithdrawn;
    private NavController navController;

    @BindView(R.id.relativeLayoutWalletProgress)
    RelativeLayout relativeLayoutWalletProgress;

    @BindView(R.id.linearLayoutWithdrawalAmount)
    LinearLayout linearLayoutWithdrawalAmount;

    @BindView(R.id.textViewTotalPrice)
    TextView textViewTotalPrice;

    @BindView(R.id.textViewUserWalletBalance)
    TextView textViewUserWalletBalance;

    @BindView(R.id.textViewTheLowestHarvest)
    TextView textViewTheLowestHarvest;

    @BindView(R.id.textViewMaxAmount)
    TextView textViewMaxAmount;

    @BindView(R.id.editTextAmount)
    EditText editTextAmount;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.yPWaveView)
    YPWaveView yPWaveView;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;

    @BindView(R.id.imageViewLoading)
    ImageView imageViewLoading;

    @BindView(R.id.gifViewSend)
    GifView gifViewSend;

    @BindView(R.id.linearLayoutUserAmount)
    LinearLayout linearLayoutUserAmount;

    @BindView(R.id.LinearLayoutTransactions)
    LinearLayout LinearLayoutTransactions;


    //______________________________________________________________________________________________ NewWallet
    public NewWallet() {
    }
    //______________________________________________________________________________________________ NewWallet


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_newWallet = new VM_NewWallet(getContext());
            FrWalletBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_wallet, container, false
            );
            binding.setWallet(vm_newWallet);
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
                NewWallet.this,
                vm_newWallet.getPublishSubject(),
                vm_newWallet);

        if (getView() != null)
            navController = Navigation.findNavController(getView());
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);
        imageViewLoading.setVisibility(View.VISIBLE);
        gifViewSend.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_GetUserScorePriceReport)) {
            setUserScorePrice();
            return;
        }

        if (action.equals(StaticValues.ML_Success)) {
            relativeLayoutWalletProgress.setVisibility(View.GONE);
            linearLayoutWithdrawalAmount.setVisibility(View.GONE);
            gifLoading.setVisibility(View.VISIBLE);
            vm_newWallet.getUserFundInfo();
            return;
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ init
    private void init() {

        relativeLayoutWalletProgress.setVisibility(View.GONE);
        linearLayoutWithdrawalAmount.setVisibility(View.GONE);
        setOnClick();

        gifLoading.setVisibility(View.VISIBLE);
        vm_newWallet.getUserFundInfo();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setUserScorePrice
    private void setUserScorePrice() {

        if (getContext() == null)
            return;
        totalPrice = (int) vm_newWallet.getMd_userFundInfo().getInfo().getCredit();
//        totalPrice = (int) vm_newWallet.getMd_userFundInfo().getWalletInfo().getMinWithdrawal();
        String string = getContext().getResources().getString(R.string.WalletMoney) + " " + getApplicationUtility().splitNumberOfAmount(totalPrice);
        textViewTotalPrice.setText(string);


        theLowestHarvest = (int) vm_newWallet.getMd_userFundInfo().getWalletInfo().getMinWithdrawal();
        theHighestAmountThatCanBeWithdrawn = (int) vm_newWallet.getMd_userFundInfo().getWalletInfo().getMaxWithdrawal();

        if (theLowestHarvest > totalPrice) {
            relativeLayoutWalletProgress.setVisibility(View.VISIBLE);
            textViewTheLowestHarvest.setText(getApplicationUtility().splitNumberOfAmount(theLowestHarvest));
            textViewUserWalletBalance.setText(getApplicationUtility().splitNumberOfAmount(totalPrice));
            yPWaveView.setMax(theLowestHarvest);
            yPWaveView.setProgress(totalPrice);

            int percent = (totalPrice * 100) / theLowestHarvest;
            percent = 100 - percent;

            int measuredHeightWave = yPWaveView.getLayoutParams().height;
            measuredHeightWave = measuredHeightWave - (measuredHeightWave / 3);

            int margin = (measuredHeightWave * percent) / 100;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, margin, 0, 0);
            params.addRule(RelativeLayout.LEFT_OF, R.id.yPWaveView);
            linearLayoutUserAmount.setLayoutParams(params);


        } else {
            linearLayoutWithdrawalAmount.setVisibility(View.VISIBLE);
            textViewMaxAmount.setText(getApplicationUtility().splitNumberOfAmount(theHighestAmountThatCanBeWithdrawn));
        }


    }
    //______________________________________________________________________________________________ setUserScorePrice


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        RelativeLayoutSend.setOnClickListener(v -> {
            hideKeyboard();
            if (checkValidation()) {
                imageViewLoading.setVisibility(View.GONE);
                gifViewSend.setVisibility(View.VISIBLE);
                vm_newWallet.settlementDemand(Integer.valueOf(editTextAmount.getText().toString().replaceAll(",","")));
            }
        });


        LinearLayoutTransactions.setOnClickListener(v -> navController.navigate(R.id.action_newWallet_to_accountFundRequest));

        editTextAmount.addTextChangedListener(
                ApplicationWMS.getApplicationWMS(getContext())
                        .getUtilityComponent()
                        .getApplicationUtility()
                        .setTextWatcherSplitting(editTextAmount));

    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ checkValidation
    private boolean checkValidation() {

        if (editTextAmount.getText().toString().length() == 0)
            return false;
        else {
            Integer amount = Integer.valueOf(editTextAmount.getText().toString().replaceAll(",", ""));
            if (amount < theLowestHarvest) {
                showMessageDialog(getContext().getResources().getString(R.string.validationAmountIsLowest),
                        getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlCollectRight1));
                return false;
            } else if (amount > theHighestAmountThatCanBeWithdrawn) {
                showMessageDialog(getContext().getResources().getString(R.string.validationAmountIsHighest),
                        getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlCollectRight1));
                return false;
            } else
                return true;
        }

    }
    //______________________________________________________________________________________________ checkValidation

}
