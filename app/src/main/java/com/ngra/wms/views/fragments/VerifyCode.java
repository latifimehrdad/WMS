package com.ngra.wms.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentVerifyCodeBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_VerifyCode;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.dialogs.DialogProgress;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class VerifyCode extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_VerifyCode vm_verifyCode;
    private String PhoneNumber = "";
    private String VerifyType = "";
    /*    private String Password = "";*/
    private DialogProgress progress;
    private boolean ReTryGetSMSClick = false;
    private Handler timer;
    private Runnable runnable;

    @BindView(R.id.VerifyCode1)
    EditText VerifyCode1;

    @BindView(R.id.VerifyCode2)
    EditText VerifyCode2;

    @BindView(R.id.VerifyCode3)
    EditText VerifyCode3;

    @BindView(R.id.VerifyCode4)
    EditText VerifyCode4;

    @BindView(R.id.VerifyCode5)
    EditText VerifyCode5;

    @BindView(R.id.VerifyCode6)
    EditText VerifyCode6;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.TimeElapsed)
    TextView TimeElapsed;

    @BindView(R.id.message)
    TextView message;


    //______________________________________________________________________________________________ VerifyCode
    public VerifyCode() {
    }
    //______________________________________________________________________________________________ VerifyCode


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentVerifyCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_verify_code, container, false);
            vm_verifyCode = new VM_VerifyCode(getContext());
            binding.setVerify(vm_verifyCode);
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
                VerifyCode.this,
                vm_verifyCode.getPublishSubject(),
                vm_verifyCode);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        setPhoneNumberPassword();
        VerifyCode1.requestFocus();
        setBackVerifyCode();
        setTextChangeListener();
        reTryGetSMS();
        setOnclick();
        startTimer(120);
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {


        if (action.equals(StaticValues.ML_GotoLogin)) {
            dismissProgress();
            if (getContext() != null) {
                getContext().onBackPressed();
                getContext().onBackPressed();
            }
            return;
        }

        if (action.equals(StaticValues.ML_GoToHome)) {
            dismissProgress();
            if (getContext() != null) {
                if (VerifyType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_SingUp))) {
                    getContext().onBackPressed();
                    getContext().onBackPressed();
                    getContext().onBackPressed();
                } else {
                    getContext().onBackPressed();
                    getContext().onBackPressed();
                }
            }
            return;
        }

        if (action.equals(StaticValues.ML_Success)){
            if (progress != null)
                progress.dismiss();
            VerifyCode1.setText("");
            VerifyCode2.setText("");
            VerifyCode3.setText("");
            VerifyCode4.setText("");
            VerifyCode5.setText("");
            VerifyCode6.setText("");
            VerifyCode1.requestFocus();
            setBackVerifyCode();
            startTimer(120);
            return;
        }


        if (action.equals(StaticValues.ML_ResponseFailure) ||
                action.equals(StaticValues.ML_ResponseError)) {
            if (progress != null)
                progress.dismiss();
            VerifyCode1.setText("");
            VerifyCode2.setText("");
            VerifyCode3.setText("");
            VerifyCode4.setText("");
            VerifyCode5.setText("");
            VerifyCode6.setText("");
            VerifyCode1.requestFocus();
            setBackVerifyCode();
            return;
        }


    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {

        message.setOnClickListener(v -> {
            if (ReTryGetSMSClick) {
                /*                vm_verifyCode.setPassword(Password);*/
                if (VerifyType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_SingUp))) {
                    vm_verifyCode.setPhoneNumber(PhoneNumber);
                    vm_verifyCode.sendNumber();
                } else {
                    vm_verifyCode.getLoginCode(PhoneNumber);
                }
            }
        });

    }
    //______________________________________________________________________________________________ setOnclick


    //______________________________________________________________________________________________ startTimer
    private void startTimer(int Elapse) {

        ReTryGetSMSClick = false;
        TimeElapsed.setVisibility(View.VISIBLE);
        message.setText(getResources().getString(R.string.ElapsedTimeGetSMS));

        Elapse = Elapse * 10;
        progressBar.setMax(Elapse * 2);
        progressBar.setProgress(Elapse);
        TimeElapsed.setVisibility(View.VISIBLE);

        timer = new Handler();
        runnable = new Runnable() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                int mil = progressBar.getProgress() + 10;
                int seconds = (mil / 10) % 60;
                int minutes = (mil / (10 * 60)) % 60;
                TimeElapsed.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    timer.postDelayed(this, 100);
                else
                    reTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }
    //______________________________________________________________________________________________ startTimer


    //______________________________________________________________________________________________ reTryGetSMS
    private void reTryGetSMS() {
        TimeElapsed.setVisibility(View.GONE);
        ReTryGetSMSClick = true;
        message.setText(getResources().getString(R.string.ReTryGetSMS));
    }
    //______________________________________________________________________________________________ reTryGetSMS


    //______________________________________________________________________________________________ setTextChangeListener
    private void setTextChangeListener() {

        VerifyCode1.addTextChangedListener(textChange(VerifyCode2));
        VerifyCode2.addTextChangedListener(textChange(VerifyCode3));
        VerifyCode3.addTextChangedListener(textChange(VerifyCode4));
        VerifyCode4.addTextChangedListener(textChange(VerifyCode5));
        VerifyCode5.addTextChangedListener(textChange(VerifyCode6));
        VerifyCode6.addTextChangedListener(textChange(VerifyCode6));

        VerifyCode1.setOnKeyListener(setKeyBackSpace(VerifyCode1));
        VerifyCode2.setOnKeyListener(setKeyBackSpace(VerifyCode1));
        VerifyCode3.setOnKeyListener(setKeyBackSpace(VerifyCode2));
        VerifyCode4.setOnKeyListener(setKeyBackSpace(VerifyCode3));
        VerifyCode5.setOnKeyListener(setKeyBackSpace(VerifyCode4));
        VerifyCode6.setOnKeyListener(setKeyBackSpace(VerifyCode5));
    }
    //______________________________________________________________________________________________ setTextChangeListener


    //______________________________________________________________________________________________ textChange
    private TextWatcher textChange(EditText eNext) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    eNext.requestFocus();
                setBackVerifyCode();
            }
        };

    }
    //______________________________________________________________________________________________ textChange


    //______________________________________________________________________________________________ setKeyBackSpace
    private View.OnKeyListener setKeyBackSpace(EditText view) {
        return (v, keyCode, event) -> {

            EditText edit = (EditText) v;
            if (keyCode == 67) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (edit.getText().length() == 0) {
                    view.requestFocus();
                    setBackVerifyCode();
                    return true;
                } else
                    return false;
            }
            return false;
        };
    }
    //______________________________________________________________________________________________ setKeyBackSpace


    //______________________________________________________________________________________________ setBackVerifyCode
    private void setBackVerifyCode() {

        Boolean c1 = setBackVerifyCodeView(VerifyCode1);
        Boolean c2 = setBackVerifyCodeView(VerifyCode2);
        Boolean c3 = setBackVerifyCodeView(VerifyCode3);
        Boolean c4 = setBackVerifyCodeView(VerifyCode4);
        Boolean c5 = setBackVerifyCodeView(VerifyCode5);
        Boolean c6 = setBackVerifyCodeView(VerifyCode6);

        if (c1 && c2 && c3 && c4 && c5 && c6) {
            String code = VerifyCode1.getText().toString() +
                    VerifyCode2.getText().toString() +
                    VerifyCode3.getText().toString() +
                    VerifyCode4.getText().toString() +
                    VerifyCode5.getText().toString() +
                    VerifyCode6.getText().toString();

            showProgressDialog();
            if (VerifyType.equalsIgnoreCase(getContext().getResources().getString(R.string.ML_SingUp))) {
                vm_verifyCode.setPhoneNumber(PhoneNumber);
                vm_verifyCode.setVerifyCode(code);
                vm_verifyCode.sendVerifyCode();
            } else {
                vm_verifyCode.getLoginVerify(PhoneNumber, code);
            }
        }

    }
    //______________________________________________________________________________________________ setBackVerifyCode


    //______________________________________________________________________________________________ setBackVerifyCodeView
    private Boolean setBackVerifyCodeView(EditText editText) {

        boolean ret = false;
        if (editText.getText().length() == 0)
            if (editText.isFocused())
                editText.setBackground(getResources().getDrawable(R.drawable.edit_verify_code_index));
            else
                editText.setBackground(getResources().getDrawable(R.drawable.edit_verify_code_empty));
        else {
            editText.setBackground(getResources().getDrawable(R.drawable.edit_code_verify_full));
            ret = true;
        }
        return ret;

    }
    //______________________________________________________________________________________________ setBackVerifyCodeView


    //______________________________________________________________________________________________ showProgressDialog
    private void showProgressDialog() {

        if (getContext() != null && getFragmentManager() != null) {
            progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .showProgress(getContext(), null);
            progress.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }

    }
    //______________________________________________________________________________________________ showProgressDialog


    //______________________________________________________________________________________________ setPhoneNumberPassword
    private void setPhoneNumberPassword() {
        Bundle bundle = getArguments();
        if (bundle != null && getContext() != null) {
            PhoneNumber = bundle.getString(getContext().getString(R.string.ML_PhoneNumber));
            VerifyType = bundle.getString(getContext().getResources().getString(R.string.ML_Type));
        }
    }
    //______________________________________________________________________________________________ setPhoneNumberPassword


    //______________________________________________________________________________________________ dismissProgress
    private void dismissProgress() {
        if (progress != null)
            progress.dismiss();

        if (timer != null && runnable != null) {
            timer.removeCallbacks(runnable);
            timer = null;
            runnable = null;
        }
    }
    //______________________________________________________________________________________________ dismissProgress


}
