package com.example.wms.views.fragments.register;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentVerifyCodeBinding;
import com.example.wms.viewmodels.register.FragmentVerifyCodeViewModel;
import com.example.wms.views.activitys.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentVerifyCode extends Fragment {

    private Context context;
    private FragmentVerifyCodeViewModel fragmentVerifyCodeViewModel;

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

    @BindView(R.id.test2)
    LinearLayout test2;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentVerifyCodeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_verify_code, container, false
        );
        fragmentVerifyCodeViewModel = new FragmentVerifyCodeViewModel(context);
        binding.setVerifycode(fragmentVerifyCodeViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentVerifyCode(Context context) {//__________________________________________________ Start FragmentVerifyCode
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentVerifyCode


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        VerifyCode1.requestFocus();
        SetBackVerifyCode();
        SetTextChangeListener();
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();
            }
        });

    }//_____________________________________________________________________________________________ End onStart



    private void set()
    {
        progressBar.setMax(360);
        progressBar.setProgress(180);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                if(progressBar.getProgress() > 0)
                    handler.postDelayed(this, 90);
            }
        },90);
        RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotatemenu);
        ranim.setFillAfter(true); //For the textview to remain at the same place after the rotation
        test2.setAnimation(ranim);
        test2.setVisibility(View.VISIBLE);
    }


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
            Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();

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
