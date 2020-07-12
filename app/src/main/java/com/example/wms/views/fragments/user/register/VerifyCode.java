package com.example.wms.views.fragments.user.register;

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

import com.example.wms.R;
import com.example.wms.databinding.FragmentVerifyCodeBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.register.VM_VerifyCode;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class VerifyCode extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_VerifyCode vm_verifyCode;
    private String PhoneNumber = "";
    private String Password = "";
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


    public VerifyCode() {//_________________________________________________________________________ VerifyCode
    }//_____________________________________________________________________________________________ VerifyCode


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentVerifyCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_verify_code, container, false);
            vm_verifyCode = new VM_VerifyCode(getContext());
            binding.setVerify(vm_verifyCode);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                VerifyCode.this,
                vm_verifyCode.getPublishSubject(),
                vm_verifyCode);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ Start init
        SetPhoneNumberPassword();
        VerifyCode1.requestFocus();
        SetBackVerifyCode();
        SetTextChangeListener();
        ReTryGetSMS();
        SetOnclick();
        StartTimer(59);
    }//_____________________________________________________________________________________________ End init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissProgress();

        if (action.equals(StaticValues.ML_GotoLogin)) {
            if (getContext() != null) {
                getContext().onBackPressed();
                getContext().onBackPressed();
            }
            return;
        }

        if (action.equals(StaticValues.ML_Success) ||
                action.equals(StaticValues.ML_ResponseFailure) ||
                action.equals(StaticValues.ML_ResponseError)) {
            VerifyCode1.setText("");
            VerifyCode2.setText("");
            VerifyCode3.setText("");
            VerifyCode4.setText("");
            VerifyCode5.setText("");
            VerifyCode6.setText("");
            VerifyCode1.requestFocus();
            SetBackVerifyCode();
            StartTimer(60);
        }


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetOnclick() {//___________________________________________________________________ SetOnclick

        message.setOnClickListener(v -> {
            if (ReTryGetSMSClick) {
                vm_verifyCode.setPassword(Password);
                vm_verifyCode.setPhoneNumber(PhoneNumber);
                vm_verifyCode.SendNumber();
            }
        });

    }//_____________________________________________________________________________________________ SetOnclick


    private void StartTimer(int Elapse) {//_________________________________________________________ StartTimer

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
                    ReTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }//_____________________________________________________________________________________________ StartTimer


    private void ReTryGetSMS() {//__________________________________________________________________ ReTryGetSMS
        TimeElapsed.setVisibility(View.GONE);
        ReTryGetSMSClick = true;
        message.setText(getResources().getString(R.string.ReTryGetSMS));
    }//_____________________________________________________________________________________________ ReTryGetSMS


    private void SetTextChangeListener() {//________________________________________________________ SetTextChangeListener

        VerifyCode1.addTextChangedListener(TextChange(VerifyCode2));
        VerifyCode2.addTextChangedListener(TextChange(VerifyCode3));
        VerifyCode3.addTextChangedListener(TextChange(VerifyCode4));
        VerifyCode4.addTextChangedListener(TextChange(VerifyCode5));
        VerifyCode5.addTextChangedListener(TextChange(VerifyCode6));
        VerifyCode6.addTextChangedListener(TextChange(VerifyCode6));

        VerifyCode1.setOnKeyListener(SetKeyBackSpace(VerifyCode1));
        VerifyCode2.setOnKeyListener(SetKeyBackSpace(VerifyCode1));
        VerifyCode3.setOnKeyListener(SetKeyBackSpace(VerifyCode2));
        VerifyCode4.setOnKeyListener(SetKeyBackSpace(VerifyCode3));
        VerifyCode5.setOnKeyListener(SetKeyBackSpace(VerifyCode4));
        VerifyCode6.setOnKeyListener(SetKeyBackSpace(VerifyCode5));
    }//_____________________________________________________________________________________________ SetTextChangeListener


    private TextWatcher TextChange(EditText eNext) {//______________________________________________ TextChange

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
                SetBackVerifyCode();
            }
        };

    }//_____________________________________________________________________________________________ TextChange


    private View.OnKeyListener SetKeyBackSpace(EditText view) {//___________________________________ SetKeyBackSpace
        return (v, keyCode, event) -> {

            EditText edit = (EditText) v;
            if (keyCode == 67) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (edit.getText().length() == 0) {
                    view.requestFocus();
                    SetBackVerifyCode();
                    return true;
                } else
                    return false;
            }
            return false;
        };
    }//_____________________________________________________________________________________________ SetKeyBackSpace


    private void SetBackVerifyCode() {//____________________________________________________________ SetBackVerifyCode

        Boolean c1 = SetBackVerifyCodeView(VerifyCode1);
        Boolean c2 = SetBackVerifyCodeView(VerifyCode2);
        Boolean c3 = SetBackVerifyCodeView(VerifyCode3);
        Boolean c4 = SetBackVerifyCodeView(VerifyCode4);
        Boolean c5 = SetBackVerifyCodeView(VerifyCode5);
        Boolean c6 = SetBackVerifyCodeView(VerifyCode6);

        if (c1 && c2 && c3 && c4 && c5 && c6) {
            String code = VerifyCode1.getText().toString() +
                    VerifyCode2.getText().toString() +
                    VerifyCode3.getText().toString() +
                    VerifyCode4.getText().toString() +
                    VerifyCode5.getText().toString() +
                    VerifyCode6.getText().toString();

            ShowProgressDialog();
            vm_verifyCode.setPhoneNumber(PhoneNumber);
            vm_verifyCode.setVerifyCode(code);
            vm_verifyCode.SendVerifyCode();
        }

    }//_____________________________________________________________________________________________ SetBackVerifyCode


    private Boolean SetBackVerifyCodeView(EditText editText) {//____________________________________ SetBackVerifyCodeView

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

    }//_____________________________________________________________________________________________ SetBackVerifyCodeView


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog

        if (getContext() != null && getFragmentManager() != null) {
            progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .ShowProgress(getContext(), null);
            progress.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }

    }//_____________________________________________________________________________________________ ShowProgressDialog


    private void SetPhoneNumberPassword() {//_______________________________________________________ SetPhoneNumberPassword
        Bundle bundle = getArguments();
        if (bundle != null && getContext() != null) {
            PhoneNumber = bundle.getString(getContext().getString(R.string.ML_PhoneNumber));
            Password = bundle.getString(getContext().getString(R.string.ML_Password));
        }
    }//_____________________________________________________________________________________________ SetPhoneNumberPassword


    private void DismissProgress() {//______________________________________________________________ DismissProgress
        if (progress != null)
            progress.dismiss();

        if (timer != null && runnable != null) {
            timer.removeCallbacks(runnable);
            timer = null;
            runnable = null;
        }
    }//_____________________________________________________________________________________________ DismissProgress


}
