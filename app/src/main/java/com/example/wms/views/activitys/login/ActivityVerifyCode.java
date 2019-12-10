package com.example.wms.views.activitys.login;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.ActivityVerifyCodeBinding;
import com.example.wms.viewmodels.login.ActivityVerifyCodeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityVerifyCode extends AppCompatActivity {

    private ActivityVerifyCodeViewModel activityVerifyCodeViewModel;
    private boolean ReTryGetSMSClick = false;


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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVerifyCodeBinding binding = DataBindingUtil.setContentView(
                this,R.layout.activity_verify_code
        );
        activityVerifyCodeViewModel = new ActivityVerifyCodeViewModel(this);
        binding.setVerifycode(activityVerifyCodeViewModel);
        ButterKnife.bind(this);
        VerifyCode1.requestFocus();
        SetBackVerifyCode();
        SetTextChangeListener();
        ReTryGetSMS();
        SetClick();
    }//_____________________________________________________________________________________________ End onCreate


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ReTryGetSMSClick)
                    StartTimer(Integer.valueOf(VerifyCode1.getText().toString() + VerifyCode2.getText().toString()));
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private void StartTimer(int Elapse) {//___________________________________________________________________ Start StartTimer

        ReTryGetSMSClick = false;
        TimeElapsed.setVisibility(View.VISIBLE);
        message.setText(getResources().getString(R.string.ElapsedTimeGetSMS));

        Elapse = Elapse * 10;
        progressBar.setMax(Elapse * 2);
        progressBar.setProgress(Elapse);
        TimeElapsed.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                int mili = progressBar.getProgress() + 10;
                int seconds = (int) (mili / 10) % 60;
                int minutes = (int) ((mili / (10 * 60)) % 60);
                TimeElapsed.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    handler.postDelayed(this, 100);
                else
                    ReTryGetSMS();
            }
        }, 100);
    }//_____________________________________________________________________________________________ End StartTimer


    private void ReTryGetSMS() {//__________________________________________________________________ Start ReTryGetSMS
        TimeElapsed.setVisibility(View.GONE);
        ReTryGetSMSClick = true;
        message.setText(getResources().getString(R.string.ReTryGetSMS));
    }//_____________________________________________________________________________________________ End ReTryGetSMS


    private void SetTextChangeListener() {//________________________________________________________ Start SetTextChangeListener

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


    }//_____________________________________________________________________________________________ End SetTextChangeListener


    private void SetBackVerifyCode() {//____________________________________________________________ Start SetBackVerifyCode

        Boolean c1 = SetBackVerifyCodeView(VerifyCode1);
        Boolean c2 = SetBackVerifyCodeView(VerifyCode2);
        Boolean c3 = SetBackVerifyCodeView(VerifyCode3);
        Boolean c4 = SetBackVerifyCodeView(VerifyCode4);
        Boolean c5 = SetBackVerifyCodeView(VerifyCode5);
        Boolean c6 = SetBackVerifyCodeView(VerifyCode6);

        if (c1 && c2 && c3 && c4 && c5 && c6)
            Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();

    }//_____________________________________________________________________________________________ End SetBackVerifyCode


    private TextWatcher TextChange(EditText eNext) {//______________________________________________ Satart TextChange

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

    }//_____________________________________________________________________________________________ End TextChange


    private Boolean SetBackVerifyCodeView(EditText editText) {//____________________________________ Satart SetBackVerifyCodeView

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

    }//_____________________________________________________________________________________________ End SetBackVerifyCodeView


    private View.OnKeyListener SetKeyBackSpace(EditText view) {//____________________________________ Start SetKeyBackSpace
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                EditText edit = (EditText) v;
                if (keyCode == 67) {
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
    }//_____________________________________________________________________________________________ End SetKeyBackSpace


}
