package com.example.wms.views.fragments.user.register;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import com.example.wms.R;
import com.example.wms.databinding.FragmentVerifyCodeBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.register.VM_FragmentVerifyCode;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerifyCode extends Fragment {

    private Context context;
    private NavController navController;
    private View view;
    private DisposableObserver<Byte> observer;
    private VM_FragmentVerifyCode vm_fragmentVerifyCode;
    private String PhoneNumber = "";
    private String Password = "";
    private boolean ReTryGetSMSClick = false;
    private DialogProgress progress;
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

    public FragmentVerifyCode() {//_________________________________________________________________ FragmentVerifyCode
        // Required empty public constructor
    }//_____________________________________________________________________________________________ FragmentVerifyCode


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onStart
        if (view == null) {
            this.context = getActivity();
            vm_fragmentVerifyCode = new VM_FragmentVerifyCode(context);
            FragmentVerifyCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_verify_code, container, false
            );
            binding.setVerifycode(vm_fragmentVerifyCode);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            init();
        }
        return view;
    }//_____________________________________________________________________________________________ onStart


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        if (observer != null)
            observer.dispose();
        observer = null;
        ObserverObservables();
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init
        SetPhoneNumberPassword();
        VerifyCode1.requestFocus();
        SetBackVerifyCode();
        SetTextChangeListener();
        ReTryGetSMS();
        SetClick();
        StartTimer(60);
    }//_____________________________________________________________________________________________ init


    private void SetClick() {//_____________________________________________________________________ SetClick

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ReTryGetSMSClick)
                    StartTimer(Integer.valueOf(VerifyCode1.getText().toString() + VerifyCode2.getText().toString()));
            }
        });

    }//_____________________________________________________________________________________________ SetClick


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
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                int mil = progressBar.getProgress() + 10;
                int seconds = (int) (mil / 10) % 60;
                int minutes = (int) ((mil / (10 * 60)) % 60);
                TimeElapsed.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    timer.postDelayed(this, 100);
                else
                    ReTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }//_____________________________________________________________________________________________ StartTimer


    private void DismissProgress() {//______________________________________________________________ DismissProgress
        if (progress != null)
            progress.dismiss();

        if (timer != null && runnable != null) {
            timer.removeCallbacks(runnable);
            timer = null;
            runnable = null;
        }
    }//_____________________________________________________________________________________________ DismissProgress


    private void ReTryGetSMS() {//__________________________________________________________________ ReTryGetSMS
        TimeElapsed.setVisibility(View.GONE);
        ReTryGetSMSClick = true;
        message.setText(getResources().getString(R.string.ReTryGetSMS));
    }//_____________________________________________________________________________________________ ReTryGetSMS


    private void SetPhoneNumberPassword() {//_______________________________________________________ SetPhoneNumberPassword
        Bundle bundle = getArguments();
        PhoneNumber = bundle.getString(context.getString(R.string.ML_PhoneNumber));
        Password = bundle.getString(context.getString(R.string.ML_Password));
    }//_____________________________________________________________________________________________ SetPhoneNumberPassword


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


        vm_fragmentVerifyCode
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ ObserverObservables


    private void HandleAction(Byte action) {//______________________________________________________ HandleAction
        if (action == StaticValues.ML_GotoLogin) {
            ShowMessage(getResources().getString(R.string.RegisterOK),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check));
            getActivity().onBackPressed();
            getActivity().onBackPressed();
//            navController.navigate(R.id.action_fragmentVerifyCode_to_fragmentLogin);
        } else if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(
                    getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
            VerifyCode1.setText("");
            VerifyCode2.setText("");
            VerifyCode3.setText("");
            VerifyCode4.setText("");
            VerifyCode5.setText("");
            VerifyCode6.setText("");
            VerifyCode1.requestFocus();
            SetBackVerifyCode();
        } else if (action == StaticValues.ML_ResponseError) {
            ShowMessage(
                    vm_fragmentVerifyCode.getMessageResponse(),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
            VerifyCode1.setText("");
            VerifyCode2.setText("");
            VerifyCode3.setText("");
            VerifyCode4.setText("");
            VerifyCode5.setText("");
            VerifyCode6.setText("");
            VerifyCode1.requestFocus();
            SetBackVerifyCode();
        }
    }//_____________________________________________________________________________________________ HandleAction


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getFragmentManager(),0);

    }//_____________________________________________________________________________________________ ShowMessage


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
            vm_fragmentVerifyCode.SendVerifyCode(PhoneNumber, code);

        }

    }//_____________________________________________________________________________________________ SetBackVerifyCode


    private Boolean SetBackVerifyCodeView(EditText editText) {//____________________________________ SetBackVerifyCodeView

        Boolean ret = false;
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
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

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
            }
        };
    }//_____________________________________________________________________________________________ SetKeyBackSpace


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(context, null);
        progress.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);

    }//_____________________________________________________________________________________________ ShowProgressDialog


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        if (observer != null)
            observer.dispose();
        observer = null;
        DismissProgress();
    }//_____________________________________________________________________________________________ onDestroy


    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ onStop

}
